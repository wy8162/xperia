package y.w.concurrency.jcp
// Producer / Consumer based on Blocking Queue
//
import java.util.concurrent.*

class BlockingQueueTest {
    class FileCrawler implements Runnable {
        private final BlockingQueue<File> fileQueue;
        private final FileFilter fileFilter;
        private final File root;

        FileCrawler(BlockingQueue<File> fileQueue, final FileFilter fileFilter, root) {
            this.fileQueue = fileQueue;
            this.root = root;
            this.fileFilter = new FileFilter() {
                boolean accept(File f) {
                    return f.isDirectory() || fileFilter.accept(f);
                }
            };
        }

        private boolean alreadyIndexed(File f) { return false; }

        void run() {
            try {
                crawl(root);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void crawl(File root) throws InterruptedException {
            File[] entries = root.listFiles(fileFilter);
            entries.each { entry ->
                if (entry.isDirectory()) crawl(entry);
                else if (!alreadyIndexed(entry)) {
                    println "(${Thread.currentThread().getName()})queue...file ${entry}";
                    fileQueue.put(entry);
                }
            }
        }
    }

    class Indexer implements Runnable {
        private final BlockingQueue<File> queue;

        Indexer(BlockingQueue<File> queue) { this.queue = queue; }

        void run() {
            try {
                while (true) {
                    indexFile(queue.take());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        void indexFile(File file) {
            println "(${Thread.currentThread().getName()})Index...file ${file}, size=${file.size()}";
        }
    }
}

final int BOUND = 10;
final int N_CONSUMERS = Runtime.getRuntime().availableProcessors();

BlockingQueue<File> queue = new LinkedBlockingQueue<File>(BOUND);
FileFilter filter = new FileFilter() {
    boolean accept(File file) { return true; }
};

new File("/Users/yangwang/Prog/Groovy/e2esuite").eachFile { root ->
    new Thread(new BlockingQueueTest.FileCrawler(queue, filter, root)).start();
}

(1..N_CONSUMERS).each {
    new Thread(new y.w.concurrency.gpar.Indexer(queue)).start();
}

// NOTE: need to figure out how to inform indexers to end
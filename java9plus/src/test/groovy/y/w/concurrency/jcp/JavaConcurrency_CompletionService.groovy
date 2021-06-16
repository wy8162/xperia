package y.w.concurrency.jcp

import java.util.concurrent.*;

class CompletionServiceTest {
    class FileInfo {
        String name;
        int lines;
    }

    class LineCounter implements Callable<FileInfo> {
        private final File file;

        LineCounter(File file) { this.file = file; }

        public FileInfo call() {
            //Thread.sleep(200L);
            int count = 0;
            file.eachLine { count++; }
            return new FileInfo(name: Thread.currentThread().getName() + ' ' + file.getName(),
                    lines: count);
        }
    }
}

ExecutorService executor = Executors.newFixedThreadPool(3);
CompletionService<CompletionServiceTest.FileInfo> completionService = new ExecutorCompletionService<CompletionServiceTest.FileInfo>(executor);
def files = []
new File("/Users/yangwang/Downloads/net/jcip/examples").eachFile {
    if (!it.isDirectory()) files << it;
}

// Sub the tasks to ExecutorCompletionService for execution
files.each { file ->
    completionService.submit( new CompletionServiceTest.LineCounter( file ) );
}

/*
while (true) {
    Future<FileInfo> f = completionService.poll();
    if (f) {
        FileInfo fileInfo = f.get();
        println "${fileInfo.name} lines=${fileInfo.lines}"
    } else break;
}
*/

try {
    for (int i = 0; i < files.size(); i++) {
        Future<CompletionServiceTest.FileInfo> f = completionService.take();
        CompletionServiceTest.FileInfo fileInfo = f.get();
        println "${fileInfo.name} lines=${fileInfo.lines}"
    }
} catch (InterruptedException e) {
    Thread.currentThread().interrupt();
} catch (ExecutionException e) {
    println e;
}



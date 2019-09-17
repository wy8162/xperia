package y.w.concurrency.jcp

import java.util.concurrent.*;

class FileList implements Callable<List<String>> {
    private final String dir;
    FileList(String dir) { this.dir = dir; }
    
    List<String> call() {
        List<String> list = [];
        new File(dir).eachFile { file -> list << file.getName(); }
        return list;
    }
}

ExecutorService executor = Executors.newCachedThreadPool();

Future<List<String>> flist1 = executor.submit( new FileList( "/Users/yangwang/Downloads/prod" ) );
Future<List<String>> flist2 = executor.submit( new FileList( "/Users/yangwang/Downloads/net/jcip/examples" ) );

try {
    println "Files under /Users/yangwang/Downloads/prod:";
    flist1.get().each { println it }
    println "Files under /Users/yangwang/Downloads/net/jcip/examples:";
    flist2.get().each { println it }
} catch (InterruptedException e) {
    // Re-assert the thread's interrupted status
    Thread.currentThread().interrupt();
    // We don't need the result, so cancel the task too
    flist1.cancel(true);
    flist1.cancel(true);
} catch (ExecutionException e) {
    println e;
}
package y.w.concurrency.jcp
// Producer / Consumer based on Blocking Queue
//
import java.util.concurrent.*

class LogAnalysis implements Runnable {
    private Thread currentThread;
    private final BlockingQueue<String> message;
    
    LogAnalysis(BlockingQueue<String> message)
    {
        this.message = message
    }
    
    void run() {
        String msg;
        while ( (msg = message.take()) != "exit" )
            println "Received message: $msg";
    }
    
    Thread start() {
        currentThread = new Thread(this);
        currentThread.start();
        return currentThread;
    }
}

BlockingQueue<String> qa = new LinkedBlockingQueue<String>(10);
BlockingQueue<String> qb = new LinkedBlockingQueue<String>(10);

def loga = new LogAnalysis(qa).start();
def logb = new LogAnalysis(qb).start();

println loga.getState();
println logb.getState();

qa.put("Hello, from Yang Wang");
qb.put("Hello, from Jack Wang");

println loga.getState();
println logb.getState();

qa.put("Hello, from Rush Limbaugh");
qb.put("Hello, from Rick Santorum");

//Thread.currentThread().sleep(500);

println loga.getState();
println logb.getState();
qa.put("exit");
qb.put("exit");

Thread.currentThread().sleep(1000);
println loga.getState();
println logb.getState();

package y.w.concurrency.jcp

//From JCP

class ClosableThreadGate {
    class ThreadGate {
        // CONDITION-PREDICATE: opened-since(n) (isOpen || generation>n)
        private boolean isOpen;
        private int generation;

        synchronized void close() {
            isOpen = false;
        }

        synchronized void open() {
            ++generation;
            isOpen = true;
            notifyAll();
        }

        // BLOCKS-UNTIL: opened-since(generation on entry)
        synchronized void await() throws InterruptedException {
            int arrivalGeneration = generation;
            while (!isOpen && arrivalGeneration == generation)
                wait();
        }
    }

    class Task implements Runnable {
        ThreadGate gate;

        Task(ThreadGate g) { gate = g; }

        void run() {
            for (int i = 0; i < 3; i++) {
                println "Task [${Thread.currentThread().getName()}] - (${i}) is waiting for notification"
                gate.await();
                println "Task [${Thread.currentThread().getName()}] - (${i}) got it. Bye now."
            }
        }
    }
}


def gate = new ClosableThreadGate.ThreadGate();
gate.close(); // Close the door now.

def t1 = new Thread( new ClosableThreadGate.Task(gate) );
def t2 = new Thread( new ClosableThreadGate.Task(gate) );
def t3 = new Thread( new ClosableThreadGate.Task(gate) );
[t1,t2,t3]*.start();

Thread.sleep(3000);
println "Main [${Thread.currentThread().getName()}] - open door now -1"
gate.open();
println "Main [${Thread.currentThread().getName()}] - close door now -2"
gate.close();
Thread.sleep(3000);

println "Main [${Thread.currentThread().getName()}] - open door now -2"
gate.open();
println "Main [${Thread.currentThread().getName()}] - close door now -3"
gate.close();
Thread.sleep(3000);

println "Main [${Thread.currentThread().getName()}] - open door now -3"
gate.open();
println "Main [${Thread.currentThread().getName()}] - close door now -4"
gate.close();
Thread.sleep(3000);
gate.open();

[t1,t2,t3]*.join();

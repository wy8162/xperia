package y.w.concurrency.jcp

import java.util.concurrent.ExecutorService

class ExecutorTest {
    class ATask implements Runnable {
        private int sleepTime
        private String name
        private static Random generator = new Random()

        ATask(String nme) {
            name = nme
            sleepTime = generator.nextInt(5000)
        }

        void run() {
            try {
                println "$name is going to sleep $sleepTime ms"
                Thread.sleep(sleepTime)
            } catch (InterruptedException e) {
                e.printStackTrace()
            }
            println "$name woke up now and finishes."
        }
    }
}

def task1 = new ExecutorTest.ATask("task 1")
def task2 = new ExecutorTest.ATask("task 2")
def task3 = new ExecutorTest.ATask("task 3")
def task4 = new ExecutorTest.ATask("task 4")

println "Staring threads now..."
ExecutorService executor = Executors.newFixedThreadPool( 3 ) // Only allocate 3 threads
executor.execute( task1 )
executor.execute( task2 )
executor.execute( task3 )
executor.execute( task4 ) // This one has to wait until one thread finishes because the pool only has 3 threads

executor.shutdown()       // Shutdown worker threads in the pool
println "Main Thread stops now. Threads created by executor will continue until terminated."
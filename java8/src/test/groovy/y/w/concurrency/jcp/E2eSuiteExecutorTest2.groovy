package y.w.concurrency.jcp

import java.util.concurrent.*;

class E2eSuiteExecutorTest2Impl {
    interface E2eTask {
        void run();
    }

    class LogSyncJob implements Runnable {
        private static int runCount = 0;
        private CountDownLatch finishCountDown;
        private CountDownLatch startCountDown;
        private BlockingQueue<String> fileQueue = null;

        LogSyncJob(BlockingQueue<String> queue) {
            this.fileQueue = queue;
            this.startCountDown = startCountDown;
            this.finishCountDown = finishCountDown;
        }

        void run() {
            runCount++;
            println "\t----->LogSyncJob started: " + new Date();
            fileQueue.put("File_" + runCount);
        }
    }


    class LogDispatcher implements Runnable {
        private static int runCount = 0;
        private CountDownLatch finishCountDown;
        private CountDownLatch startCountDown;
        private final BlockingQueue<String> fileQueue;
        private final BlockingQueue<String>[] processorQueues;

        LogDispatcher(BlockingQueue<String> queue, List<BlockingQueue<String>> processorQueues,
                             CountDownLatch startCountDown) {
            this.fileQueue = queue;
            this.processorQueues = processorQueues;
            this.startCountDown = startCountDown;
        }

        void run() {
            startCountDown.await();
            try {
                while (true) {
                    println ">>>>LogDispatcher is waiting for file...";
                    String file = fileQueue.take();
                    println ">>>>LogDispatcher got file: " + file;
                    runCount++;

                    processorQueues[runCount % 2].put(file);
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

    class Worker implements Runnable {
        private CountDownLatch startCountDown;
        private final BlockingQueue<String> fileQueue;
        private E2eTask task;

        Worker(E2eTask task, BlockingQueue<String> queue, CountDownLatch startCountDown) {
            this.task = task;
            this.fileQueue = queue;
            this.startCountDown = startCountDown;
        }

        void run() {
            this.startCountDown.await();
            try {
                while (true) {
                    //task.run();
                    println Thread.currentThread().getName() + "Worker is waiting for file...";
                    String file = fileQueue.take();
                    println Thread.currentThread().getName() + "Worker got file: " + file + " at " + new Date();
                }
            } catch (InterruptedException ignored) {
            }
        }
    }

    class E2eSuiteExecutor {
        private final CountDownLatch startCountDown;
        private ExecutorService executor;
        private ScheduledExecutorService scheduler;
        private BlockingQueue<String> fileQueue;
        private List<BlockingQueue<String>> dispatchQueues = [];

        E2eSuiteExecutor() {
            startCountDown = new CountDownLatch(1);
        }

        void serve() {
            println "\n\nDemonstrating usage of Executor and ScheduledThreadPool...";

            Runtime.getRuntime().addShutdownHook(new Thread() {
                void run() {
                    try {
                        println "Application is being shutdown...";
                        E2eSuiteExecutor.this.stop();
                    } catch (InterruptedException ignored) {
                    }
                }
            });

            fileQueue = new ArrayBlockingQueue<String>(10);
            dispatchQueues << new ArrayBlockingQueue<String>(10);
            dispatchQueues << new ArrayBlockingQueue<String>(10);

            LogSyncJob logSyncJob = new LogSyncJob(fileQueue);
            LogDispatcher logDispatcher = new LogDispatcher(fileQueue, dispatchQueues,
                    startCountDown);
            Worker ratings = new Worker(null, dispatchQueues[0], startCountDown);
            Worker cmp = new Worker(null, dispatchQueues[1], startCountDown);

            executor = Executors.newCachedThreadPool();
            scheduler = Executors.newScheduledThreadPool(1);

            ScheduledFuture logSyncLogS = scheduler.scheduleAtFixedRate(logSyncJob, 5, 2, TimeUnit.SECONDS);

            Future logDispatcherF = executor.submit(logDispatcher);
            Future ratingsF = executor.submit(ratings);
            Future cmpF = executor.submit(cmp);

            println "Now, fire them up...";
            startCountDown.countDown();

            executor.awaitTermination(12000, TimeUnit.MILLISECONDS);

            logSyncLogS.cancel(true);
            logDispatcherF.cancel(true);
            ratingsF.cancel(true);
            cmpF.cancel(true);

            this.stop();

            println "Done. ExecutorService isShutdown=" + executor.isShutdown() + " isTerminated=" + executor.isTerminated() + "\n";
        }

        void stop() {
            executor.shutdown();
            scheduler.shutdown();
        }
    }
}

def e2e = new E2eSuiteExecutorTest2Impl.E2eSuiteExecutor();
e2e.serve();
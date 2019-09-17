package y.w.concurrency.jcp

import java.util.concurrent.*;

class E2eSuiteExecutorTestImpl {
    interface E2eTask {
        void run();
    }

    class LogSyncJob implements Runnable {
        private CountDownLatch finishCountDown;
        private CountDownLatch startCountDown;
        private ArrayBlockingQueue<String> fileQueue = null;

        LogSyncJob(ArrayBlockingQueue<String> queue, CountDownLatch startCountDown, CountDownLatch finishCountDown) {
            this.fileQueue = queue;
            this.startCountDown = startCountDown;
            this.finishCountDown = finishCountDown;
        }

        void run() {
            startCountDown.await();
            println "LogSyncJob started";
            fileQueue.put("File_One");
            fileQueue.put("File_Two");
            fileQueue.put("File_Three");
            fileQueue.put("File_Four");
            finishCountDown.countDown();
        }
    }


    class LogDispatcher implements Runnable {
        private CountDownLatch finishCountDown;
        private CountDownLatch startCountDown;
        private final ArrayBlockingQueue<String> fileQueue;
        private final ArrayBlockingQueue<String>[] processorQueues

        LogDispatcher(ArrayBlockingQueue<String> queue, List<ArrayBlockingQueue<String>> processorQueues,
                             CountDownLatch startCountDown, CountDownLatch finishCountDown) {
            this.fileQueue = queue;
            this.processorQueues = processorQueues;
            this.startCountDown = startCountDown;
            this.finishCountDown = finishCountDown;
        }

        void run() {
            startCountDown.await();
            for (int i = 0; i < 4; i++) {
                println "LogDispatcher is waiting for file..."
                String file = fileQueue.take();
                println "LogDispatcher got file: " + file;

                switch (file) {
                    case "File_One":
                        processorQueues[0].put(file);
                        break;
                    case "File_Two":
                        processorQueues[1].put(file);
                        break;
                    case "File_Three":
                        processorQueues[0].put(file);
                        break;
                    case "File_Four":
                        processorQueues[1].put(file);
                        break;
                    default:
                        println "Bad file!!!";
                        break;
                }
            }
            finishCountDown.countDown();
        }
    }

    class Worker implements Runnable {
        private CountDownLatch finishCountDown;
        private CountDownLatch startCountDown;
        private final ArrayBlockingQueue<String> fileQueue;
        private E2eTask task;

        Worker(E2eTask task, ArrayBlockingQueue<String> queue,
                      CountDownLatch startCountDown, CountDownLatch finishCountDown) {
            this.task = task;
            this.fileQueue = queue;
            this.startCountDown = startCountDown;
            this.finishCountDown = finishCountDown;
        }

        void run() {
            this.startCountDown.await();
            try {
                //task.run();
                println Thread.currentThread().getName() + "Worker Got file: " + fileQueue.take();
                println Thread.currentThread().getName() + "Worker Got file: " + fileQueue.take();
            } catch (InterruptedException ignored) {
            }
            finishCountDown.countDown();
        }
    }

    class E2eSuiteExecutor {
        private final CountDownLatch finishCountDown;
        private final CountDownLatch startCountDown;
        private Executor executor;
        private int threadPoolSize = 5;
        private ArrayBlockingQueue<String> fileQueue;
        private List<ArrayBlockingQueue<String>> dispatchQueues = [];

        E2eSuiteExecutor() {
            finishCountDown = new CountDownLatch(4);
            startCountDown = new CountDownLatch(1);
        }

        void serve() {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                void run() {
                    try {
                        println "Interrupted in shutdown hook";
                        E2eSuiteExecutor.this.stop();
                    } catch (InterruptedException ignored) {
                    }
                }
            });

            fileQueue = new ArrayBlockingQueue<String>(10);
            dispatchQueues << new ArrayBlockingQueue<String>(10);
            dispatchQueues << new ArrayBlockingQueue<String>(10);

            LogSyncJob logSyncJob = new LogSyncJob(fileQueue, startCountDown, finishCountDown);
            LogDispatcher logDispatcher = new LogDispatcher(fileQueue, dispatchQueues,
                    startCountDown, finishCountDown);
            Worker ratings = new Worker(null, dispatchQueues[0], startCountDown, finishCountDown);
            Worker cmp = new Worker(null, dispatchQueues[1], startCountDown, finishCountDown);

            executor = Executors.newFixedThreadPool(threadPoolSize);

            executor.execute(logSyncJob);
            executor.execute(logDispatcher);
            executor.execute(ratings);
            executor.execute(cmp);

            println "Now, fire them up...";
            startCountDown.countDown();

            println "Now, waiting for the tasks to complete..";
            finishCountDown.await();
            this.stop();
            println "Done.";
        }

        void stop() {
            executor.shutdown();
        }
    }
}

def e2e = new E2eSuiteExecutorTestImpl.E2eSuiteExecutor();
e2e.serve();
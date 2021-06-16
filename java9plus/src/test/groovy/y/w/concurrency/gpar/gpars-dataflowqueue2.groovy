package y.w.concurrency.gpar

import groovyx.gpars.actor.DynamicDispatchActor

class DataflowQueue2Test {
    class MyWorker extends DynamicDispatchActor {
        def currThread
        def threadName
        def actor
        def name

        void setActor(act, nm, tn) { actor = act; name = nm; threadName = tn }

        void onMessage(String message) {
            println "$name - Worker: $message from ACTOR at " + new Date()
            currThread = Thread.currentThread()
            currThread.setName(threadName)
            (0..25).each {
                (0..10).each {
                    Thread.sleep(40L)
                    println "$name - Worker " + it + " ****" + currThread.getName()
                }
                println "$name - Worker: woke up at " + new Date()
            }
            println "$name - Worker: woke up at " + new Date()
        }

        void onMessage(boolean message) {
            println 'Worker Done. Bye.'
            stop()
        }
    }

    final class MyActor extends DynamicDispatchActor {
        def currThread
        def threadName
        def actor
        def name

        void setActor(act, nm, tn) { actor = act; name = nm; threadName = tn }

        void onMessage(String message) {
            println "$name - Actor: working now at " + new Date()
            currThread = Thread.currentThread()
            currThread.setName(threadName)
            actor "Hey, worker"
            (0..25).each {
                (0..10).each {
                    Thread.sleep(50L)
                    println "$name - Actor " + it + " ****" + currThread.getName()
                }
                println "$name - Actor: woke up at " + new Date()
            }
            actor false
        }

        void onMessage(boolean message) {
            println 'Actor Done. Bye.'
            stop()
        }
    }
}

final def actor = new DataflowQueue2Test.MyActor()
final def worker = new DataflowQueue2Test.MyWorker()
actor.setActor(worker, "..........", "Jack")
worker.setActor(actor, "\t----------", "Jane")
actor.start()
worker.start()
actor "Get started"

final def actor1 = new DataflowQueue2Test.MyActor()
final def worker1 = new DataflowQueue2Test.MyWorker()
actor1.setActor(worker1, "\t\t++++++++++", "Yang")
worker1.setActor(actor1, "\t\t\t==========", "Daisy")
actor1.start()
worker1.start()
actor1 "Get started"

actor1 false
actor false

[actor, worker, actor1, worker1]*.join()

println "Total Heap size: " + Runtime.getRuntime().totalMemory()
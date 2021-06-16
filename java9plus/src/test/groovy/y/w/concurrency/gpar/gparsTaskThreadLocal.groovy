package y.w.concurrency.gpar

import groovyx.gpars.dataflow.DataflowQueue
import static groovyx.gpars.dataflow.Dataflow.task
import groovyx.gpars.group.DefaultPGroup

class Message {
    def message    // End Of Message if message = null
}

class MessageQueue {
    DataflowQueue inQ = null // Incoming Queue
    DataflowQueue outQ= null // Outgoing Queue
}

abstract class TaskTemplate {
    protected static final ThreadLocal queue = new ThreadLocal() {
        protected Object initialValue() {
            println "${Thread.currentThread().getName()}-created one queue"
            return new MessageQueue()
        }
    }

    TaskTemplate() {
    }

    abstract void doWork(Message message)
    
    def run() {
        while (true) {
            def message = queue.get().inQ.val
            doWork(message)
            if (message.message == null) {
                println "${Thread.currentThread().getName()}->>>>>>>>${getClass().name}: Bye!"
                break
            }
        }
        return true
    }
    
    void sendMessage(msg) { queue.get().inQ << msg }
    
    DataflowQueue getIncomingQueue() { return queue.get().inQ }
    
    void setOutgoingQueue(DataflowQueue q) { queue.get().outQ = q }
    void setIncomingQueue(DataflowQueue q) { queue.get().inQ = q }
    
    boolean isTherePendingWork() { return queue.get().inQ.length() > 0 }
}

final class ParserTask extends TaskTemplate {
    void doWork(Message message) {
        if (message.message) {
            println "\t${Thread.currentThread().getName()}-Parser: ${message.message}"
            queue.get().outQ << new Message(message : "From Parser: " + message.message)
            queue.get().outQ << new Message(message : "And this is from me, the Parser")
            (1..2).each {
                //Thread.sleep(100L)
                queue.get().outQ << new Message(message : "I'm " + "still" * it + " parsing")
            }
        } else {
            queue.get().outQ << message
        }
    }
}

final class ProcessorTask extends TaskTemplate {
    void doWork(Message message) {
        println "\t\t${Thread.currentThread().getName()}-Processor: ${message.message}"
    }
}

// Queues
def parserInQ = new DataflowQueue()
def procInQ = new DataflowQueue()

// Create task group
def group = new DefaultPGroup()

group.with {
    def parserTask = task {
        def parser = new ParserTask()
        parser.setIncomingQueue(parserInQ)
        parser.setOutgoingQueue(procInQ)
        parser.run()
    }
    
    def processorTask = task {
        def processor = new ProcessorTask()
        processor.setIncomingQueue(procInQ)
        processor.run()
    }
    
    def main = task {
        //Thread.sleep(300L)
        (1..3).each { n ->
            println "${Thread.currentThread().getName()}-Yeah, I'm coming..."
            parserInQ << new Message( message : "From Controller: Hey, my number = " + n )
            //Thread.sleep(300L)
        }
        parserInQ << new Message( message : null )
        
       // Let's wait for these guys to finish their work
       //Thread.sleep(1000L)
    }.join()
}

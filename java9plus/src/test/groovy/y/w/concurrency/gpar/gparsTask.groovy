package y.w.concurrency.gpar

import groovyx.gpars.dataflow.DataflowQueue
import static groovyx.gpars.dataflow.Dataflow.task
import groovyx.gpars.group.DefaultPGroup

class TaskTest {
	class Message {
		def message    // End Of Message if message = null
	}

	abstract class TaskTemplate {
		DataflowQueue inQ        // Incoming Queue
		DataflowQueue outQ    // Outgoing Queue

		TaskTemplate() {
			inQ = new DataflowQueue()
			outQ = null
		}

		abstract void doWork(Message message)

		def run() {
			while (true) {
				def message = inQ.val
				doWork(message)
				if (message.message == null) {
					println ">>>>>>>>${getClass().name}: Bye!"
					break
				}
			}
			return true
		}

		void sendMessage(msg) { inQ << msg }

		DataflowQueue getIncomingQueue() { return inQ }

		void setOutgoingQueue(DataflowQueue q) { outQ = q }

		boolean isTherePendingWork() { return inQ.length() > 0 }
	}

	class ParserTask extends TaskTemplate {
		ParserTask(TaskTest enclosing) {
			super(enclosing)
		}

		void doWork(Message message) {
			if (message.message) {
				println "\tParser: ${message.message}"
				outQ << new Message(message: "From Parser: " + message.message)
				outQ << new Message(message: "And this is from me, the Parser")
				(0..3).each {
					Thread.sleep(10L)
					outQ << new Message(message: "I'm " + "still" * it + " parsing")
				}
			} else {
				outQ << message
			}
		}
	}

	class ProcessorTask extends TaskTemplate {
		ProcessorTask(TaskTest enclosing) {
			super(enclosing)
		}

		void doWork(Message message) {
			println "\t\tProcessor: ${message.message}"
		}
	}
}

// Create tasks
def parser = new TaskTest.ParserTask()
def processor = new TaskTest.ProcessorTask()

// Link them toggether
parser.setOutgoingQueue( processor.getIncomingQueue() )

// Create task group
def group = new DefaultPGroup()

group.with {
	def parserTask = task {
		parser.run()
	}
	
	def processorTask = task {
		processor.run()
	}
	
	def main = task {
		(1..100000).each { n ->
			println "Yeah, I'm coming..."
			parser.sendMessage( new Message( message : "From Controller: Hey, my number = " + n ) )
			//Thread.sleep(300L)
		}
		parser.sendMessage(  new Message( message : null ) )
		
		// Let's wait for these guys to finish their work
		while (parser.isTherePendingWork() || processor.isTherePendingWork()) {
			Thread.sleep(100L)
		}
	}.join()
}


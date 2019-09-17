package y.w.concurrency.gpar

import groovyx.gpars.actor.DynamicDispatchActor

class ActorTaskTest {
	class Message {
		def message    // End Of Message if message = null
	}

	abstract class ActorTemplate extends DynamicDispatchActor {
		DynamicDispatchActor outQ    // Outgoing Queue

		ActorTemplate() {
			outQ = null
		}

		abstract void doWork(Message message)

		void onMessage(Message message) {
			doWork(message)
			if (message.message == null) {
				println "${getClass().name} got a null message"
				stop()
				return
			}
		}

		DynamicDispatchActor getIncomingQueue() { return this }

		void setOutgoingQueue(DynamicDispatchActor q) { outQ = q }

		boolean isRunning() { return this.isActive() }
	}

	class ParserTask extends ActorTemplate {
		private static Random generator = new Random()

		ParserTask(ActorTaskTest enclosing) {
			super(enclosing)
		}

		void doWork(Message message) {
			if (message.message) {
				println "\tParser: ${message.message}"
				outQ << new Message(message: "From Parser: " + message.message)
				outQ << new Message(message: "And this is from me, the Parser")
				(0..3).each {
					Thread.sleep(generator.nextInt(300))
					outQ << new Message(message: "I'm " + "still" * it + " parsing")
				}
			} else {
				outQ << message
			}
		}
	}

	class ProcessorTask extends ActorTemplate {
		ProcessorTask(ActorTaskTest enclosing) {
			super(enclosing)
		}

		void doWork(Message message) {
			println "\t\tProcessor: ${message.message}"
		}
	}

	class DriverTask extends ActorTemplate {
		DriverTask(ActorTaskTest enclosing) {
			super(enclosing)
		}
		private static Random generator = new Random()

		void doWork(Message message) {
			(1..1000).each { n ->
				println "Yeah, I'm coming..."
				println outQ.getClass().name
				outQ << new Message(message: "From Controller: Hey, my number = " + n)
				Thread.sleep(generator.nextInt(300))
			}
			println "Driver finished it's job"
			outQ << new Message(message: null)
		}
	}
}

def parser    = new ActorTaskTest.ParserTask()
def processor = new ActorTaskTest.ProcessorTask()
def driver    = new ActorTaskTest.DriverTask()

parser.setOutgoingQueue( processor )
driver.setOutgoingQueue(    parser )

[driver, parser, processor]*.start()

driver new Message(message : null )     // Fire it up and then, done.

[driver, parser, processor]*.join()
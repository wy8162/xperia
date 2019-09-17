package y.w.concurrency.gpar

import groovyx.gpars.actor.DynamicDispatchActor

class DynamicActorTest {
	class MyActor extends DynamicDispatchActor {
		def MyActor() {}
		def counter = 0

		def processString(String str) {
			counter++
			def sb = new StringBuilder(8 * 1024)

			def head = ~/^\d{4}-\d{2}-/
			def pat = ~/Executed in (\d+) msec/
			def callId = ~/(MS|WS)-\d+-\d+/

			if (head.matcher(str)) {
				sb.append(str[0..23] + " ")
				if (str.contains("jdbc.sqltiming")) sb.append(" SQL ")
				def m = callId.matcher(str)
				if (m) sb.append(m[0][1])
				return sb.toString()
			}
			return null
		}

		void onMessage(String message) {
			processString(message)
		}

		void onMessage(boolean message) {
			println 'Done. Bye.'
			stop()
		}
	}
}

def startTime = new Date()
final def actor1 = new DynamicActorTest.MyActor().start()
final def actor2 = new DynamicActorTest.MyActor().start()

println "Total Heap size: " + Runtime.getRuntime().totalMemory()
println "Total Free size: " + Runtime.getRuntime().freeMemory()

def f = new File("/Users/yangwang/Downloads/temp/core-dsuat_v1_nysddss15-2011_10_31_15_43_02.log.1")
f.each { line ->
	actor1 line
	actor2 line
}

actor1 false
actor2 false

[actor1, actor2]*.join()

println "Total Heap size: " + Runtime.getRuntime().totalMemory()
println "Total Free size: " + Runtime.getRuntime().freeMemory()
def endTime = new Date()
println "Elapsed time: " + (endTime.time - startTime.time)
println "Total lines (actor 1): " + actor1.counter
println "Total lines (actor 2): " + actor1.counter

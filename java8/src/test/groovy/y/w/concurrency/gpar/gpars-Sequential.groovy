package y.w.concurrency.gpar

import groovyx.gpars.actor.DynamicDispatchActor

def doProcess(String str) {
	def sb = new StringBuilder(8*1024)

	def head= ~/^\d{4}-\d{2}-/
	def pat = ~/Executed in (\d+) msec/
	def callId= ~/(MS|WS)-\d+-\d+/
	
	if (head.matcher(str)) {
		sb.append(str[0..23] + " ")
		if (str.contains("jdbc.sqltiming")) sb.append(" SQL ")
		def m = callId.matcher(str)
		if (m) sb.append(m[0][1])
		return sb.toString()
	}
	return null
}

def startTime = new Date()

println "Total Heap size: " + Runtime.getRuntime().totalMemory()
println "Total Free size: " + Runtime.getRuntime().freeMemory()

def f = new File("/Users/yangwang/Downloads/temp/core-dsuat_v1_nysddss15-2011_10_31_15_43_02.log.1")
f.each { line ->
	doProcess( line )
	doProcess( line )
}

println "Total Heap size: " + Runtime.getRuntime().totalMemory()
println "Total Free size: " + Runtime.getRuntime().freeMemory()
def endTime = new Date()
println "Elapsed time: " + (endTime.time - startTime.time)


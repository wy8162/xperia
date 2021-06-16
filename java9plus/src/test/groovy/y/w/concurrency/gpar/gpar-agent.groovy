package y.w.concurrency.gpar

import groovyx.gpars.agent.Agent

class Cat {
    StringBuilder name =  new StringBuilder(512)
    void append(s) { name << s}
    def getName() { return name }
}

def cat = new Cat()

class Conference extends Agent<Cat> {
    def Conference(d) { super(d) }
    private def register(attendee) { data.append(attendee) }
    void getData() { println data.getName() }
}

final Agent conference = new Conference(cat)  //new Conference created

/**
 * Three external parties will try to register/unregister concurrently
 */

final Thread t1 = Thread.start {
    Thread.sleep(1000L)
    conference << {register("Jack")}               //send a command to register 10 attendees
}

final Thread t2 = Thread.start {
    Thread.sleep(2000L)
    conference << {register("Jane")}                //send a command to register 5 attendees
}

final Thread t3 = Thread.start {
    Thread.sleep(2000L)
    conference << {register("Yang")}              //send a command to unregister 3 attendees
}

final Thread t4 = Thread.start {
    def l = 5000L
    while (true) {
        if (l <= 0) break
        l -= 500
        Thread.sleep(500L)
        conference.getData()
    }
}

println "Active Thread: " + Thread.activeCount()
[t1, t2, t3, t4]*.join()
println "Finally "
conference.getData()
println "Active Thread: " + Thread.activeCount()

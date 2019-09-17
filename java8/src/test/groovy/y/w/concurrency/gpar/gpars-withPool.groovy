package y.w.concurrency.gpar

import groovyx.gpars.actor.Actor
import groovyx.gpars.actor.DefaultActor
import groovyx.gpars.GParsPool
import java.util.concurrent.atomic.AtomicInteger


GParsPool.withPool {
    println "Checking /Users/yangwang/Downloads/Example13"
    def depth = 0
    GParsPool.runForkJoin(new File("/Users/yangwang/Downloads/Example13")) { file ->
        depth++
        println '--'*(depth-1) + file.name
        file.eachFile {
            if (it.isDirectory()) {
                //println "===>Forking a child task for $it"
                forkOffChild(it)
            } else println '  '*depth + it.name
        }
        depth--
        return 0
    }
}

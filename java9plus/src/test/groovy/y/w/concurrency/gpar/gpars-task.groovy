package y.w.concurrency.gpar


import groovyx.gpars.dataflow.DataflowQueue
import static groovyx.gpars.dataflow.Dataflow.task

class Indexer {
    final DataflowQueue queue
    def task
    
    Indexer() {
        queue = new DataflowQueue()
        task  = task { return run() }
    }
    
    def run() {
        while (true) {
            def var = queue.val
            if (var == "DONE") break
            println "I am going to index this data: " + var
        }
        return true
    }
}

class Worker {
    final DataflowQueue queue
    def indexer
    def task
    
    Worker(idx) {
        queue = new DataflowQueue()
        indexer = idx
        task  = task { return run() }
    }
    
    def run() {
        while (true) {
            def var = queue.val
            if (var == "DONE") break
            println "I got this: " + var
        }
        return true
    }
}

def indexer = new Indexer()

def t = task {
    def list = [:]
    ['a','b','c'].each {
        list[it] = new y.w.concurrency.jcp.Worker(indexer)
    }
    (1..3).each { n ->
        ['a','b','c'].each { t ->
            Thread.sleep(100L)
            list[t].queue << t + " " + n
        }
    }
}

t.join()
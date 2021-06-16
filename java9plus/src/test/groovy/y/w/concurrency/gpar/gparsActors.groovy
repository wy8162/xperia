package y.w.concurrency.gpar

import groovyx.gpars.actor.DynamicDispatchActor

final class MyIndexer extends DynamicDispatchActor {
    def MyIndexer() {}
    def processString(String str) {
        println "Indexing...$str"
        return null
    }

    void onMessage(String message) {
        processString(message)
    }

    void onMessage(boolean message) {
        println 'Index Done. Bye.'
        stop()
    }
}

final class MyActor extends DynamicDispatchActor {
    def indexer
    def name
    def MyActor(idx, name) { indexer = idx; this.name = name }
    def counter = 0

    def processString(String str) {
        indexer str + " $name"
        return null
    }

    void onMessage(String message) {
        processString(message)
    }

    void onMessage(boolean message) {
        println 'Actor $name Done. Bye.'
        stop()
    }
}

final def indexer= new MyIndexer().start()
final def actor1 = new MyActor(indexer, "aaa").start()
final def actor2 = new MyActor(indexer, "bbb").start()
final def actor3 = new MyActor(indexer, "ccc").start()

["111", "222", "333", "444"].each { line ->
    actor1 line
    actor2 line
    actor3 line
}

actor1 false
actor2 false
actor3 false
sleep(1000L)
indexer false

[actor1, actor2, actor3, indexer]*.join()
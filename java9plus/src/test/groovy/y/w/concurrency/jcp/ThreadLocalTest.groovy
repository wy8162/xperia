package y.w.concurrency.jcp

class MyInfo {
    static int count = 1
    String name = "Yes"
    Date   date = new Date() + count++
}

class Task implements Runnable {
    private static final ThreadLocal myInfo = new ThreadLocal() {
        protected Object initialValue() {
            println "created one queue"
            return new MyInfo(name : "Goose")
        }
    }
    
    Task(String nme) {
        myInfo.set(new MyInfo(name : nme))
    }
    
    void setName(String name) {
        myInfo.get().name = name
    }
    
    void run() {
        setName("Random No " + (Math.random()))
        (0..2).each {
            println "${Thread.currentThread().getName()} - My name is '${myInfo.get().name}', date=${myInfo.get().date}"
            Thread.sleep(500L)
        }
    }
}

Task task1 = new Task("Yang")
Task task2 = new Task("Yang")
Task task3 = new Task("Yang")
//task1.setName("Yang") // This won't work - it actually set's the main thread's ThreadLocal variable
//task2.setName("Jack") // This won't work - it actually set's the main thread's ThreadLocal variable
//task3.setName("Jane") // This won't work - it actually set's the main thread's ThreadLocal variable
Thread th1 = new Thread(task1)
Thread th2 = new Thread(task2)
Thread th3 = new Thread(task3)

[th1, th2, th3]*.start()
[th1, th2, th3]*.join()

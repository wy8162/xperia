package y.w.concurrency.jcp

class C {
    int i;
    
    synchronized public void needIt() { wait(); }
    synchronized public void getIt() { notifyAll(); }
}

def c = new C();

def th1 = new Thread(new Runnable() {
    public void run() {
        println "${Thread.currentThread().getName()} - I'm gonna wait..."
        c.needIt()
    }
    });
    

def th2 = new Thread(new Runnable() {
    public void run() {
        println "${Thread.currentThread().getName()} - I'm gonna wait..."
        c.needIt()
    }
    });
    
[th1, th2]*.start();

println "Main - ${Thread.currentThread().getName()} - I'm gonna wait..."
Thread.sleep(5000);
c.getIt();

[th1, th2]*.join();

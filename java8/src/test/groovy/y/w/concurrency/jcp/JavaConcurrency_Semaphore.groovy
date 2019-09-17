package y.w.concurrency.jcp
/**
    A counting semaphore. Conceptually, a semaphore maintains a set of permits.
    Each acquire() blocks if necessary until a permit is available, and then takes it.
    Each release() adds a permit, potentially releasing a blocking acquirer. However,
    no actual permit objects are used; the Semaphore just keeps a count of the number 
    available and acts accordingly
 */

import java.util.concurrent.*;

/**
 * BoundedHashSet
 * <p/>
 * Using Semaphore to bound a collection
 *
 * @author Brian Goetz and Tim Peierls
 */
public class BoundedHashSet <T> {
    private final Set<T> set;
    private final Semaphore sem;

    public BoundedHashSet(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        sem = new Semaphore(bound) //, true); // Fairness set to true
    }

    public boolean add(T o) throws InterruptedException {
        println "Before add - Avaliable permits: ${sem.availablePermits()}"
        sem.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            if (!wasAdded)
                sem.release();
        }
    }

    public boolean remove(Object o) {
        boolean wasRemoved = set.remove(o);
        if (wasRemoved)
            sem.release();
        println "After remove - Avaliable permits: ${sem.availablePermits()}"
        return wasRemoved;
    }
}

class AddElement implements Runnable {
    BoundedHashSet<String> bhs = null
    List<String> list = null
    AddElement(BoundedHashSet<String> set, List<String> list) {
        this.bhs = set;
        this.list = list;
   }
    void run() {
        list.each {
            bhs?.add(it);
            println "${Thread.currentThread().getName()}-${System.currentTimeMillis()}-Added: $it";
        }
    }
}

class RemoveElement implements Runnable {
    BoundedHashSet<String> bhs = null
    List<String> list = null
    RemoveElement(BoundedHashSet<String> set, List<String> list) {
        this.bhs = set;
        this.list = list;
   }
    void run() {
        list.each {
            Thread.sleep(500L); // Give sometime for AddEle to add and be blocked
            bhs.remove(it);
            println "${Thread.currentThread().getName()}-${System.currentTimeMillis()}-Removed: $it";
        }
    }
}

List<String> list = ['Jack', 'Yang', 'Jane', 'Wang', 'Daisy', 'Sunflow', 'Butterfly']
def set  = new BoundedHashSet<String>(3);

def th1 = new Thread(new AddElement(set, list));
def th2 = new Thread(new RemoveElement(set, list));

[th1, th2]*.start()
[th1, th2]*.join()


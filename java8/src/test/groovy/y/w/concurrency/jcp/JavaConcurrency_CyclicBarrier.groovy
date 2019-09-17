package y.w.concurrency.jcp
/**
    Using CyclicBarrier to calculate a matrix: total value of all the cells.
 */

import java.util.concurrent.*;

class CalculateMatrix {
    private final List<List<Integer>> matrix;
    private final CyclicBarrier barrier;
    private final Worker[] workers;
    private volatile List<Integer> values;
    
    CalculateMatrix(theMatrix) {
        this.matrix = theMatrix;
        int count = this.matrix.size(); // number of rows
        this.values = new Integer[ count ] as List<Integer>;
        this.barrier = new CyclicBarrier( count,
            new Runnable() {
                public void run() {
                    def total = 0;
                    values.each { total += it; }
                    println "Total value: $total"
                }});
        this.workers = new Worker[count];
        (0..count-1).each { workers[it] = new Worker(it, values, matrix[it]); }
    }
    
    private class Worker implements Runnable {
        private final List<Integer> list;
        private final int idx;
        private final List<Integer> values;
        
        Worker(int idx, List<Integer> values, List<Integer> list) {
            this.list = list;
            this.idx = idx;
            this.values = values;
        }
        
        public void run() {
            def total = 0;
            list.each { total += it; }
            values[idx] = total;
            println "Done with row $idx, total=$total";
            try { barrier.await(); // Wait untill all parties called this barrier.
                  println "Thread ${Thread.currentThread().getName()} finished."; }
            catch (InterruptedException ex) { return; }
            catch (BrokenBarrierException ex) { return; }
        }
    }
    
    public void start() {
        workers.each {
            new Thread( it ).start();
        }
    }
}

List<List<Integer>> matrix = [
    [1,2,3,4,5,6,7,8,9,10],
    [11,12,13,14,15,16,17],
    [18,19,20,21,22,23,24],
    [25,26,27,28,29,30,31]
];

def calc = new CalculateMatrix(matrix);
calc.start();
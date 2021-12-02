package y.w.j8.multithreading.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class CustomRecursiveAction extends RecursiveAction {
    private final int[] arr;
    private final int start, end;
    private final int SIZE = 2;
    public CustomRecursiveAction(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start + 1 > SIZE) {
            ForkJoinTask.invokeAll(divideTasks());
        } else {
            process();
        }
    }

    private List<CustomRecursiveAction> divideTasks() {
        int mid = (start + end) / 2;
        List<CustomRecursiveAction> tasks = new ArrayList<>();

        tasks.add(new CustomRecursiveAction(arr, start, mid));
        tasks.add(new CustomRecursiveAction(arr, mid + 1, end));

        return tasks;
    }

    private void process() {
        System.out.println(String.format("%s : Processing: %d - %d", Thread.currentThread().getName(), start, end));
    }
}

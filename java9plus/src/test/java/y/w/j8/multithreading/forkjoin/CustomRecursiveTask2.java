package y.w.j8.multithreading.forkjoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CustomRecursiveTask2 extends RecursiveTask<Integer> {
    private final int[] arr;
    private final int SIZE = 2;
    public CustomRecursiveTask2(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected Integer compute() {
        if (arr.length <= SIZE) {
            return process();
        } else {
            List<CustomRecursiveTask2> tasks = divideTasks();
            tasks.get(0).fork();
            Integer right = tasks.get(1).process();
            return tasks.get(0).join() + right;
        }
    }

    private List<CustomRecursiveTask2> divideTasks() {
        List<CustomRecursiveTask2> tasks = new ArrayList<>();

        tasks.add(new CustomRecursiveTask2(Arrays.copyOfRange(arr, 0, arr.length / 2)));
        tasks.add(new CustomRecursiveTask2(Arrays.copyOfRange(arr, arr.length / 2, arr.length)));

        return tasks;
    }

    private Integer process() {
        System.out.println(String.format("%s : Processing.", Thread.currentThread().getName()));

        return Arrays.stream(arr).sum();
    }
}

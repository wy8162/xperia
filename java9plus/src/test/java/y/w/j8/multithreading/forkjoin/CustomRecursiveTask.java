package y.w.j8.multithreading.forkjoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CustomRecursiveTask extends RecursiveTask<Integer> {
    private final int[] arr;
    private final int SIZE = 2;
    public CustomRecursiveTask(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected Integer compute() {
        if (arr.length > SIZE) {
            return ForkJoinTask.invokeAll(divideTasks())
                .stream()
                .mapToInt(ForkJoinTask::join).sum();
        } else {
            return process();
        }
    }

    private List<CustomRecursiveTask> divideTasks() {
        List<CustomRecursiveTask> tasks = new ArrayList<>();

        tasks.add(new CustomRecursiveTask(Arrays.copyOfRange(arr, 0, arr.length / 2)));
        tasks.add(new CustomRecursiveTask(Arrays.copyOfRange(arr, arr.length / 2, arr.length)));

        return tasks;
    }

    private Integer process() {
        System.out.println(String.format("%s : Processing:", Thread.currentThread().getName()));

        return Arrays.stream(arr).sum();
    }
}

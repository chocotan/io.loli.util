package io.loli.util.concurrent;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutor {

    public static ExecutorService service = Executors.newFixedThreadPool(20);

    public void execute(Task task) {
        service.execute(() -> task.execute());
    }

    public void execute(final List<Task> tasks) {
        service.execute(() -> {
            for (Task task : tasks) {
                task.execute();
            }
        });
    }

    public void execute(final List<Task> tasks, int groupSize) {
        int taskSize = tasks.size();
        int groupCount = taskSize / groupSize;
        for (int i = 0; i < groupCount; i++) {
            int end = groupSize;
            if (i == groupCount - 1) {
                end = taskSize % groupSize - 1;
            }
            this.execute(tasks.subList(i * groupSize, end));
        }
    }
}

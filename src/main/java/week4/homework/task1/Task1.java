package week4.homework.task1;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Task1 {

    private static final int REJECTED_TASKS_MAX_NUMBER = 10;

    public static void main(String[] args) {

        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " Doing hard work...");
                Thread.sleep(1_000);
                System.out.println(Thread.currentThread().getName() + " Done.");
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        };

        var rejectedTasks = new AtomicInteger();

        var executorService = new ThreadPoolExecutor(1, 4,
                30L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),
                (r, executor) -> rejectedTasks.incrementAndGet());

        for (int i = 0; i < 50; i++) {
            executorService.execute(task);
            if (rejectedTasks.get() == REJECTED_TASKS_MAX_NUMBER) {
                executorService.shutdown();
            }
        }

    }

}

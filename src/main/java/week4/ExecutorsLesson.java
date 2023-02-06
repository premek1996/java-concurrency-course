package week4;

import lombok.SneakyThrows;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorsLesson {

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " - Starting...");

        var executorService = Executors.newFixedThreadPool(4);

        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " Doing hard work...");
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + " Done.");
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        };

        try {
            executorService.execute(task);
            executorService.execute(task);
            executorService.execute(task);
            executorService.execute(task);
            executorService.execute(task);
            executorService.execute(task);
            executorService.execute(task);
            executorService.execute(task);
            executorService.execute(task);
            executorService.execute(task);
            executorService.execute(task);
        } finally {
            executorService.shutdown();
            System.out.println(executorService.isShutdown() + " " + executorService.isTerminated());
            executorService.awaitTermination(10, TimeUnit.SECONDS);
            System.out.println(executorService.isShutdown() + " " + executorService.isTerminated());
        }

        System.out.println(Thread.currentThread().getName() + " - Done");
    }

}

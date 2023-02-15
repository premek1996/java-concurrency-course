package week5;

import lombok.SneakyThrows;

import java.util.concurrent.*;

public class CallableLesson {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println(Thread.currentThread().getName() + " - Starting...");

        Callable<Long> task = new Callable<Long>() {
            @SneakyThrows
            @Override
            public Long call() {
                System.out.println(Thread.currentThread().getName() + " - Doing hard work..");
                Thread.sleep(5_000);
                return 123L;
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Future<Long> future = executorService.submit(task);

        Long result = future.get();
        System.out.println(result);

        executorService.shutdown();

        System.out.println(Thread.currentThread().getName() + " - Done");
    }

}

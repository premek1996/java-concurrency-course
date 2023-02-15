package week5;

import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureLesson1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println(Thread.currentThread().getName() + " - Starting...");

        CompletableFuture<Long> cf = new CompletableFuture();

        Runnable task = new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                System.out.println(Thread.currentThread().getName() + " - Starting hard work...");
                Thread.sleep(500);
                cf.complete(42L);
                System.out.println(Thread.currentThread().getName() + " - Done, returning result...");
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.submit(task);

        Long result = cf.get();
        System.out.println(Thread.currentThread().getName() + " Result is: " + result);

        executorService.shutdown();

        CompletableFuture<String> stringCompletableFuture = CompletableFuture.completedFuture("I've already got this one");
        String resultString = stringCompletableFuture.get();
        System.out.println(resultString);

        CompletableFuture<Long> longCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " - Starting hard work...");
                Thread.sleep(500);
                return 123L;
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });

        Long resultLong = longCompletableFuture.get();
        System.out.println(resultLong);

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " - Starting hard work...");
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + " - CompletableFuture finish hard work...");
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });

        voidCompletableFuture.get();

        System.out.println(Thread.currentThread().getName() + " - Done");
    }

}

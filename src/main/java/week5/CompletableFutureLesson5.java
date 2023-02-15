package week5;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureLesson5 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println(Thread.currentThread().getName() + " - Starting...");

        CompletableFuture<Long> asyncLong = CompletableFuture.supplyAsync(() ->
        {
            try {
                System.out.println(Thread.currentThread().getName() + " - Computing asyncLong...");
                Thread.sleep(1_500);
                System.out.println(Thread.currentThread().getName() + " - Done...");
                return 42L;
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });

        CompletableFuture<String> asyncString = CompletableFuture.supplyAsync(() ->
        {
            try {
                System.out.println(Thread.currentThread().getName() + " - Computing asyncString...");
                Thread.sleep(3_500);
                System.out.println(Thread.currentThread().getName() + " - Done...");
                return "Consumer name is: John Doe";
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        CompletableFuture<Void> voidCompletableFuture = asyncLong.thenAcceptAsync(value -> {
            try {
                System.out.println(Thread.currentThread().getName() + " - Accepting value...");
                Thread.sleep(5_000);
                System.out.println(Thread.currentThread().getName() + "- Consumed value is: " + value);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }, executorService);

        asyncLong.get();

        Thread.sleep(3_000);

        executorService.shutdown();

        CompletableFuture<Long> cf = new CompletableFuture<>();
        cf.completeExceptionally(new RuntimeException("Ooops...."));

        cf.get();

        System.out.println(Thread.currentThread().getName() + " - Done");

    }

}

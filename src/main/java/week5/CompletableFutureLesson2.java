package week5;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureLesson2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println(Thread.currentThread().getName() + " - Starting...");

        CompletableFuture<Long> asyncLong = CompletableFuture.supplyAsync(() ->
        {
            try {
                System.out.println(Thread.currentThread().getName() + " - Computing...");
                Thread.sleep(1_500);
                System.out.println(Thread.currentThread().getName() + " - Done...");
                return 42L;
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });

        CompletableFuture<Long> asyncLong2 = CompletableFuture.supplyAsync(() ->
        {
            try {
                System.out.println(Thread.currentThread().getName() + " - Computing...");
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + " - Done...");
                return 97L;
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });

        CompletableFuture<Void> voidCompletableFuture = asyncLong.acceptEither(asyncLong2, value -> {
            System.out.println("The faster one was: " + value);
        });

        System.out.println(voidCompletableFuture.get());

        CompletableFuture<Void> voidCompletableFuture2 =
                asyncLong.thenAcceptBoth(asyncLong2, (aLong, aLong2) -> System.out.println("The values are: " + aLong + " and: " + aLong2));

        System.out.println(voidCompletableFuture2.get());

        CompletableFuture<Object> objectCompletableFuture = CompletableFuture.anyOf(asyncLong, asyncLong2);
        System.out.println("Any of: " + objectCompletableFuture.get());

        CompletableFuture<Void> voidCompletableFuture1 = CompletableFuture.allOf(asyncLong, asyncLong2);
        System.out.println("All of: " + voidCompletableFuture1.get());

//        Long result = asyncLong.get();
//        System.out.println(Thread.currentThread().getName() + " - Value is: " + result);

        CompletableFuture<String> stringCompletableFuture = asyncLong.thenApply(value -> "The answer to all question is ... " + value);
        System.out.println(stringCompletableFuture.get());

        CompletableFuture<String> stringCompletableFuture2 = asyncLong.thenApply(value -> value * 2)
                .thenApply(value -> value + 2)
                .thenApply(value -> value * 15)
                .thenApply(value -> "Final value is: " + value);

        System.out.println(stringCompletableFuture2.get());


        CompletableFuture<String> asyncString = CompletableFuture.supplyAsync(() ->
        {
            try {
                System.out.println(Thread.currentThread().getName() + " - Computing...");
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + " - Done...");
                return "Consumer name is: John Doe";
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });

        CompletableFuture<String> stringCompletableFuture1 = asyncLong.thenCombine(asyncString, (number, text) -> "Combined value is: " + text + " - " + number);
        System.out.println(stringCompletableFuture1.get());

        CompletableFuture<String> stringCompletableFuture3 = asyncLong.thenCompose(value -> {
            try {
                System.out.println(Thread.currentThread().getName() + " - Computing...");
                Thread.sleep(500);
                return asyncAnswer(value);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });

        System.out.println(stringCompletableFuture3.get());

        System.out.println(Thread.currentThread().getName() + " - Done");
    }

    private static CompletableFuture<String> asyncAnswer(Long value) {
        return CompletableFuture.completedFuture("The answer to all question is ... " + value);
    }

}

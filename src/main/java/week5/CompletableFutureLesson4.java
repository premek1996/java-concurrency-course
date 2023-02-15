package week5;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureLesson4 {

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
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + " - Done...");
                return "Consumer name is: John Doe";
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });

        CompletableFuture<Long> futureValue = new MyTask(-10).square();

//        CompletableFuture<Long> longCompletableFuture = futureValue.whenComplete((aLong, throwable) -> {
//            if (throwable == null) {
//                System.out.println("Long is: " + aLong);
//            } else {
//                System.out.println("Throwable is: " + throwable);
//            }
//        });

        CompletableFuture<Optional<Long>> longCompletableFuture = futureValue.handle((aLong, throwable) -> {
            if (throwable == null) {
                System.out.println("Long is: " + aLong);
                return Optional.of(aLong);
            } else {
                System.out.println("Throwable is: " + throwable);
                return Optional.empty();
            }
        });

        System.out.println("Result is: " + longCompletableFuture.get());

        System.out.println(Thread.currentThread().getName() + " - Done");
    }

    static class MyTask {
        private long value;

        public MyTask(long value) {
            this.value = value;
        }

        public CompletableFuture<Long> square() {
            return CompletableFuture.supplyAsync(() ->
            {
                if (value <= 0) {
                    throw new IllegalArgumentException("This must be at least one;");
                }
                try {
                    System.out.println(Thread.currentThread().getName() + " - Computing square...");
                    Thread.sleep(1_500);
                    System.out.println(Thread.currentThread().getName() + " - Done...");
                    return value * value;
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            });
        }
    }


}

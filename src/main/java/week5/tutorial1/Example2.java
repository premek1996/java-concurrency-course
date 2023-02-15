package week5.tutorial1;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Example2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        OrderProcessor orderProcessor = new OrderProcessor();
        CompletableFuture<String> futureOrder = orderProcessor.processOrder()
                .thenApply(String::toUpperCase);

        CompletableFuture<Void> futureOrder2 = orderProcessor.processOrder()
                .thenApply(String::toUpperCase)
                .thenApply(x -> {
                    throw new RuntimeException("Cannot process order");
                })
                .exceptionally(ex -> {
                    System.out.println(ex.getMessage());
                    return "Error";
                })
                .thenAccept(message -> System.out.println("futureOrder2 " + message));

        System.out.println("Doing sth else");
        System.out.println("Doing sth else");

        Thread.sleep(2_000);


        String result = futureOrder.get();
        System.out.println(result);

        futureOrder2.get();
    }

}

class OrderProcessor {

    public CompletableFuture<String> processOrder() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " Begin - order processing");
                Thread.sleep(2_000);
                System.out.println(Thread.currentThread().getName() + " End - order processing");
                return "Order no 1";
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });
    }

}

package week5.tutorial1;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Example3 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> calc1 = CompletableFuture.supplyAsync(() ->
        {
            try {
                System.out.println(Thread.currentThread().getName() + " Calculation #1 in progress");
                Thread.sleep(2_000);
                return 1;
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });

        CompletableFuture<Integer> calc2 = CompletableFuture.supplyAsync(() ->
        {
            try {
                System.out.println(Thread.currentThread().getName() + " Calculation #2 in progress");
                Thread.sleep(1_000);
                return 5;
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });

        calc1.thenCombine(calc2, (n1, n2) -> n1 * n2)
                .thenAccept(result -> System.out.println("Result is: " + result))
                .get();
    }

}

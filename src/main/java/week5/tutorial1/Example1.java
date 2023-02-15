package week5.tutorial1;

import java.util.concurrent.*;

public class Example1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        OrderDataCallable orderDataCallable = new OrderDataCallable();
        Future<String> futureOrderProcessor = executorService.submit(orderDataCallable);

        while (!futureOrderProcessor.isDone()) {
            System.out.println("Doing something else");
            Thread.sleep(300);
        }

        System.out.println(futureOrderProcessor.get());

        executorService.shutdown();
    }

}

class OrderDataCallable implements Callable<String> {

    @Override
    public String call() {
        try {
            System.out.println(Thread.currentThread().getName() + " Begin - order processing");
            Thread.sleep(1_000);
            System.out.println(Thread.currentThread().getName() + " End - order processing");
            return "Order no 1";
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}

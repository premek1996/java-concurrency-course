package week5.tutorial2;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.*;

public class Example2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Order order = new Order();
        Future<Order> submit = executorService.submit(new GetOrderTask(order));
        order = submit.get(); //blocking
        submit = executorService.submit(new EnchrichTask(order));
        order = submit.get(); //blocking
        submit = executorService.submit(new PerformPaymentTask(order));
        order = submit.get(); //blocking
        submit = executorService.submit(new DispatchTask(order));
        order = submit.get(); //blocking
        submit = executorService.submit(new SendEmailTask(order));
        order = submit.get(); //blocking

        executorService.shutdown();
    }

    static class Order {
    }

    @RequiredArgsConstructor
    static class GetOrderTask implements Callable<Order> {

        private final Order order;

        @Override
        public Order call() throws InterruptedException {
            System.out.println("OrderTask");
            Thread.sleep(1_000);
            return order;
        }

    }

    @RequiredArgsConstructor
    static class EnchrichTask implements Callable<Order> {

        private final Order order;

        @Override
        public Order call() throws InterruptedException {
            System.out.println("EnchrichTask");
            Thread.sleep(1_000);
            return order;
        }

    }

    @RequiredArgsConstructor
    static class PerformPaymentTask implements Callable<Order> {

        private final Order order;

        @Override
        public Order call() throws InterruptedException {
            System.out.println("PerformPaymentTask");
            Thread.sleep(1_000);
            return order;
        }

    }

    @RequiredArgsConstructor
    static class DispatchTask implements Callable<Order> {

        private final Order order;

        @Override
        public Order call() throws InterruptedException {
            System.out.println("DispatchTask");
            Thread.sleep(1_000);
            return order;
        }

    }

    @RequiredArgsConstructor
    static class SendEmailTask implements Callable<Order> {

        private final Order order;

        @Override
        public Order call() throws InterruptedException {
            System.out.println("SendEmailTask");
            Thread.sleep(1_000);
            return order;
        }

    }

}

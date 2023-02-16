package week5.tutorial2;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public class Example3 {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            CompletableFuture.supplyAsync(() -> new GetOrderTask(order).call())
                    .thenApply(o -> new EnchrichTask(order).call())
                    .thenApply(o -> new PerformPaymentTask(order).call())
                    .thenApply(o -> new DispatchTask(order).call())
                    .thenAccept(o -> new SendEmailTask(order).call());
            Thread.sleep(500);
        }

        Thread.sleep(15_000);
    }

    static class Order {
    }

    @RequiredArgsConstructor
    static class GetOrderTask implements Callable<Order> {

        private final Order order;

        @SneakyThrows
        @Override
        public Order call() {
            System.out.println(Thread.currentThread().getName() + " OrderTask");
            Thread.sleep(1_000);
            return order;
        }

    }

    @RequiredArgsConstructor
    static class EnchrichTask implements Callable<Order> {

        private final Order order;

        @SneakyThrows
        @Override
        public Order call() {
            System.out.println(Thread.currentThread().getName() + " EnchrichTask");
            Thread.sleep(1_000);
            return order;
        }

    }

    @RequiredArgsConstructor
    static class PerformPaymentTask implements Callable<Order> {

        private final Order order;

        @SneakyThrows
        @Override
        public Order call() {
            System.out.println(Thread.currentThread().getName() + " PerformPaymentTask");
            Thread.sleep(1_000);
            return order;
        }

    }

    @RequiredArgsConstructor
    static class DispatchTask implements Callable<Order> {

        private final Order order;

        @SneakyThrows
        @Override
        public Order call() {
            System.out.println(Thread.currentThread().getName() + " DispatchTask");
            Thread.sleep(1_000);
            return order;
        }

    }

    @RequiredArgsConstructor
    static class SendEmailTask implements Callable<Order> {

        private final Order order;

        @SneakyThrows
        @Override
        public Order call() {
            System.out.println(Thread.currentThread().getName() + " SendEmailTask");
            Thread.sleep(1_000);
            return order;
        }

    }

}

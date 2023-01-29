package week3;

import lombok.SneakyThrows;

import java.util.concurrent.CyclicBarrier;

public class BarriersLesson {

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " - Starting...");

        Task task = new Task();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(task);
            thread.start();
            Thread.sleep(1_000);
        }

        System.out.println(Thread.currentThread().getName() + " - Done");
    }

    private static class Task implements Runnable {

        private final CyclicBarrier cyclicBarrier;

        public Task() {
            this.cyclicBarrier = new CyclicBarrier(5);
        }

        @Override
        @SneakyThrows
        public void run() {
            System.out.println(Thread.currentThread().getName() + " - Waiting...");
            cyclicBarrier.await();
            System.out.println(Thread.currentThread().getName() + " - Doing heavy work...");
        }

    }


}

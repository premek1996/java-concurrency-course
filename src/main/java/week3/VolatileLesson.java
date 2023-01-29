package week3;

import lombok.SneakyThrows;

import java.util.concurrent.atomic.AtomicLong;

public class VolatileLesson {

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " - Starting...");
        Counter counter = new Counter();
        Thread thread = new Thread(counter);
        thread.start();
        Thread.sleep(1_500);
        counter.stop();

        System.out.println(Thread.currentThread().getName() + " - Done");
    }

    static class Counter implements Runnable {

        private final AtomicLong value = new AtomicLong(0);
        private volatile boolean isRunning = true;

        @Override
        @SneakyThrows
        public void run() {
            while (isRunning) {
                long currentValue = value.incrementAndGet();
                System.out.println("Incremented counter to: " + currentValue);
                Thread.sleep(100);
            }
        }

        public void stop() {
            System.out.println("Stop task!");
            isRunning = false;
        }

    }

}

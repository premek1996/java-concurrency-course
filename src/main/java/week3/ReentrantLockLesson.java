package week3;

import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockLesson {

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " - Starting...");

        /////////////////////////////////////////
        // Counter
        /////////////////////////////////////////
        var counter = new Counter();
        var incrementValueTask = new IncrementValueTask(counter);

        var thread1 = new Thread(incrementValueTask);
        var thread2 = new Thread(incrementValueTask);
        var thread3 = new Thread(incrementValueTask);

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println(counter.getValue());

        /////////////////////////////////////////
        // Inventory
        /////////////////////////////////////////

        var inventory = new Inventory();
        var putItemTask = new PutItemTask(inventory);

        var thread4 = new Thread(putItemTask);
        var thread5 = new Thread(putItemTask);

        thread4.start();
        thread5.start();

        thread4.join();
        thread5.join();

        inventory.printState();

        System.out.println(Thread.currentThread().getName() + " - Done");
    }

    static class Counter {

        private long value;
        private final Lock lock = new ReentrantLock();

        public void incrementValue() {
            try {
                lock.lock();
                value++;
            } finally {
                lock.unlock();
            }
        }

        public long getValue() {
            return value;
        }

    }

    static class IncrementValueTask implements Runnable {

        private final Counter counter;

        IncrementValueTask(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100000; i++) {
                counter.incrementValue();
            }
        }
    }

    static class Inventory {
        private final Map<String, Integer> state = new HashMap<>();
        private final Lock lock = new ReentrantLock();

        @SneakyThrows
        public void put(String item, Integer quantity) {
            boolean tryLock = lock.tryLock(50, TimeUnit.MILLISECONDS);
            if (tryLock) {
                try {
                    System.out.println("Got lock: " + Thread.currentThread().getName());
                    Thread.sleep(100);
                    state.put(item, quantity);
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("Unable to get lock: " + Thread.currentThread().getName());
            }
        }

        public void printState() {
            System.out.println(state);
        }

    }

    static class PutItemTask implements Runnable {

        private final Inventory inventory;

        PutItemTask(Inventory inventory) {
            this.inventory = inventory;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                inventory.put("tomato", i);
            }
        }

    }

}


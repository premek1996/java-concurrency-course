package week3;

import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockLesson {

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " - Starting...");

        /////////////////////////////////////////
        // Inventory
        /////////////////////////////////////////

        var inventory = new Inventory();
        var putItemTask = new PutItemTask(inventory);
        var printStateTask = new PrintStateTask(inventory);

        var thread1 = new Thread(putItemTask);

        var thread2 = new Thread(printStateTask);
        var thread3 = new Thread(printStateTask);

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println(Thread.currentThread().getName() + " - Done");
    }


    static class Inventory {
        private final Map<String, Integer> state = new HashMap<>();
        private final ReadWriteLock lock = new ReentrantReadWriteLock();

        @SneakyThrows
        public void put(String item, Integer quantity) {
            Lock writeLock = lock.writeLock();

            boolean tryLock = writeLock.tryLock(50, TimeUnit.MILLISECONDS);
            if (tryLock) {
                try {
                    System.out.println("Got write lock: " + Thread.currentThread().getName());
                    Thread.sleep(100);
                    state.put(item, quantity);
                } finally {
                    System.out.println("Released write lock: " + Thread.currentThread().getName());
                    writeLock.unlock();
                }
            } else {
                System.out.println("Unable to get write lock: " + Thread.currentThread().getName());
            }
        }

        @SneakyThrows
        public void printState() {
            Lock readLock = lock.readLock();
            boolean tryLock = readLock.tryLock(50, TimeUnit.MILLISECONDS);
            if (tryLock) {
                try {
                    System.out.println("Got read lock: " + Thread.currentThread().getName());
                    System.out.println(state);
                } finally {
                    System.out.println("Released read lock: " + Thread.currentThread().getName());
                    readLock.unlock();
                }
            } else {
                System.out.println("Unable to get read lock: " + Thread.currentThread().getName());
            }
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

    static class PrintStateTask implements Runnable {

        private final Inventory inventory;

        PrintStateTask(Inventory inventory) {
            this.inventory = inventory;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                inventory.printState();
            }
        }
    }

}


package week3;

import lombok.SneakyThrows;

public class SynchronizedLesson {

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " - Starting...");
        Counter counter = new Counter();

        var incrementValueTask = new IncrementValueTask(counter);
        var incrementOtherValueTask = new IncrementOtherValueTask(counter);

        var thread = new Thread(incrementValueTask);
        var thread2 = new Thread(incrementValueTask);
        var thread3 = new Thread(incrementValueTask);

        var thread4 = new Thread(incrementOtherValueTask);
        var thread5 = new Thread(incrementOtherValueTask);
        var thread6 = new Thread(incrementOtherValueTask);

        thread.start();
        thread2.start();
        thread3.start();

        thread4.start();
        thread5.start();
        thread6.start();

        thread.join();
        thread2.join();
        thread3.join();

        thread4.join();
        thread5.join();
        thread6.join();

        System.out.println(counter.getValue());
        System.out.println(counter.getOtherValue());
        System.out.println(Thread.currentThread().getName() + " - Done");
    }

    static class IncrementValueTask implements Runnable {

        private final Counter counter;

        IncrementValueTask(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000000; i++) {
                this.counter.increment();
            }
        }

    }

    static class IncrementOtherValueTask implements Runnable {

        private final Counter counter;

        IncrementOtherValueTask(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000000; i++) {
                this.counter.incrementOther();
            }
        }

    }

    static class Counter {

        private long value;
        private long otherValue;
        private final Object lock1 = new Object();
        private final Object lock2 = new Object();

        public void increment() {
            synchronized (lock1) {
                value++;
            }
        }

        public void incrementOther() {
            synchronized (lock2) {
                otherValue++;
            }
        }

        public long getValue() {
            return value;
        }

        public long getOtherValue() {
            return otherValue;
        }
    }

}

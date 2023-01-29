package week3;

import lombok.SneakyThrows;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLesson {

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " - Starting...");
        Counter counter = new Counter();
        IncrementValueTask incrementValueTask = new IncrementValueTask(counter);
        Thread t0 = new Thread(incrementValueTask);
        Thread t1 = new Thread(incrementValueTask);
        Thread t2 = new Thread(incrementValueTask);

        t0.start();
        t1.start();
        t2.start();

        t0.join();
        t1.join();
        t2.join();

        counter.printValues();

        System.out.println(Thread.currentThread().getName() + " - Done");
    }

    static class Counter {

        private final AtomicLong value = new AtomicLong(0);
        private long primitiveValue;

        public void printValues() {
            System.out.println("Atomic value: " + value.longValue());
            System.out.println("Primitive value: " + primitiveValue);
        }

        public void increment() {
            value.incrementAndGet();
            primitiveValue++;
        }

    }

    static class IncrementValueTask implements Runnable {

        private final Counter counter;

        IncrementValueTask(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100_000; i++) {
                counter.increment();
            }
        }

    }

}

package week3;

import lombok.SneakyThrows;

public class ScopeLesson {

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " - Starting...");
        Counter counter = new Counter();
        Runnable task = () -> System.out.println("Value is: " + counter.increment(11));

        Thread t0 = new Thread(task);
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t0.start();
        t1.start();
        t2.start();

        t0.join();
        t1.join();
        t2.join();

        System.out.println(Thread.currentThread().getName() + " - Done");
    }

    static class Counter {

        public int increment(int newValue) {
            int localValue;
            localValue = newValue * 3;
            return localValue;
        }
    }

}

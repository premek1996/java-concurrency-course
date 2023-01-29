package week3;

import lombok.SneakyThrows;

public class DeadLockLesson {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " - Starting...");
        Task task = new Task();
        Thread thread1 = new Thread(task::doTheJob);
        Thread thread2 = new Thread(task::doTheJob2);

        thread1.start();
        thread2.start();

        System.out.println(Thread.currentThread().getName() + " - Done");
    }

    static class Task {

        private final Object lock1 = new Object();
        private final Object lock2 = new Object();

        @SneakyThrows
        public void doTheJob() {
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + " - holding lock1..");
                Thread.sleep(1_000);
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + " - holding lock2..");
                    Thread.sleep(1_000);
                    System.out.println(Thread.currentThread().getName() + " - releasing lock2..");
                }
                System.out.println(Thread.currentThread().getName() + " - releasing lock1..");
            }
        }

        @SneakyThrows
        public void doTheJob2() {
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + " - holding lock2..");
                Thread.sleep(1_000);
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + " - holding lock1..");
                    Thread.sleep(1_000);
                    System.out.println(Thread.currentThread().getName() + " - releasing lock1..");
                }
                System.out.println(Thread.currentThread().getName() + " - releasing lock2..");
            }
        }
    }

}
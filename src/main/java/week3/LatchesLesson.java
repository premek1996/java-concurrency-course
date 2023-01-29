package week3;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LatchesLesson {

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " - Starting...");

        CountDownLatch countDownLatch = new CountDownLatch(3);

        Runnable task = new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                System.out.println(Thread.currentThread().getName() + " Starting run...");
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName() + " Waiting for other threads");
                countDownLatch.await(10, TimeUnit.MINUTES);
                System.out.println(Thread.currentThread().getName() + " Working hard");
                Thread.sleep(1_000);
                System.out.println(Thread.currentThread().getName() + " Finishing");

            }
        };

        Thread t0 = new Thread(task);
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);

        t0.start();
        Thread.sleep(1_000);
        t1.start();
        Thread.sleep(1_000);
        t2.start();
        Thread.sleep(1_000);
        t3.start();

        System.out.println("All threads started!");

        t0.join();
        t1.join();
        t2.join();
        t3.join();

        System.out.println(Thread.currentThread().getName() + " - Done");
    }

}

package week4;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolsLesson {

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName() + " - Starting...");

        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " Doing hard work...");
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + " Done.");
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        };

        //var executorService = Executors.newFixedThreadPool(4);
        //var executorService = Executors.newSingleThreadExecutor();
        // var executorService = Executors.newCachedThreadPool();
        //var executorService = Executors.newWorkStealingPool();


        var executorService = Executors.newSingleThreadScheduledExecutor();

        //executorService.schedule(task, 5_000, TimeUnit.MILLISECONDS);
        //executorService.scheduleAtFixedRate(task, 0, 5_000, TimeUnit.MILLISECONDS);
        executorService.scheduleWithFixedDelay(task, 0, 5_000, TimeUnit.MILLISECONDS);

//        executorService.execute(task);
//        executorService.execute(task);
//        executorService.execute(task);
//        executorService.execute(task);
//        executorService.execute(task);
//        executorService.execute(task);
//        executorService.execute(task);
//        executorService.execute(task);
//        executorService.execute(task);
//        executorService.execute(task);
//        executorService.execute(task);
//        executorService.execute(task);
//        executorService.execute(task);
//        executorService.execute(task);
//        executorService.execute(task);
//        executorService.execute(task);

        //executorService.shutdown();

        System.out.println(Thread.currentThread().getName() + " - Done");

    }

}

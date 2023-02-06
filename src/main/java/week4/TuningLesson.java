package week4;

import java.util.concurrent.*;

public class TuningLesson {

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName() + " - Starting...");

        ThreadPoolExecutor executorService = new ThreadPoolExecutor(4, 4,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1),
                (r, executor) -> {
                    throw new UnsupportedOperationException("rejection message!");
                }
        );

        ThreadPoolExecutor cachedExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1),
                (r, executor) -> {
                    throw new UnsupportedOperationException("rejection message!");
                });

        ExecutorService daemonExecutor = Executors.newFixedThreadPool(4, r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });

        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " Doing hard work...");
                Thread.sleep(500);
                System.out.println(Thread.currentThread().getName() + " Done.");
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        };

        daemonExecutor.execute(task);
        daemonExecutor.execute(task);
        daemonExecutor.execute(task);
        daemonExecutor.execute(task);
        daemonExecutor.execute(task);
        daemonExecutor.execute(task);
        daemonExecutor.execute(task);

        System.out.println(Thread.currentThread().getName() + " - Done");

    }

}

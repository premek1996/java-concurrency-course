package week5;

import java.util.concurrent.*;

public class FutureLesson1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        System.out.println(Thread.currentThread().getName() + " - Starting...");

        Callable<Long> longRunningTask = () -> {
            System.out.println(Thread.currentThread().getName() + " - Starting hard work...");
            Thread.sleep(1_500);
            System.out.println(Thread.currentThread().getName() + " - Done, returning result...");
            return 123L;
        };

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Future<Long> future1 = executorService.submit(longRunningTask);
        Future<Long> future2 = executorService.submit(longRunningTask);

        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " - Doing sth other...");
        }

        while (!future1.isDone() || !future2.isDone()) {
            System.out.println(future1.isDone() + " " + future2.isDone());
            Thread.sleep(300);
        }

        System.out.println(future1.get());
        System.out.println(future2.get());

        executorService.shutdown();
        System.out.println(Thread.currentThread().getName() + " - Done");

    }

}

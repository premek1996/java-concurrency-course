package week5;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.*;

public class FutureLesson2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        System.out.println(Thread.currentThread().getName() + " - Starting...");

        Callable<Long> longRunningTask = () -> {
            System.out.println(Thread.currentThread().getName() + " - Starting hard work...");
            Thread.sleep(15_000);
            System.out.println(Thread.currentThread().getName() + " - Done, returning result...");
            return 123L;
        };

        MyTask myTask = new MyTask(10);
        MyTask myTask2 = new MyTask(-15);
        MyTask myTask3 = new MyTask(12);
        MyTask myTask4 = new MyTask(11);

        MyTask myTask5 = new MyTask(12);
        MyTask myTask6 = new MyTask(11);

        ExecutorService executorService = Executors.newFixedThreadPool(4);


        //invokeAny
        Long result = executorService.invokeAny(List.of(myTask3, myTask4));
        System.out.println(result);

        //invokeAll
        List<Future<Long>> futures = executorService.invokeAll(List.of(myTask5, myTask6));
        futures.forEach(future -> {
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });


        Future<Long> future = executorService.submit(longRunningTask);
        Future<Long> future2 = executorService.submit(myTask);
        Future<Long> future3 = executorService.submit(myTask2);

        System.out.println(future2.get());
        //System.out.println(future3.get());

        if (!future.isDone()) {
            Thread.sleep(1_000);
        }

        if (!future.isDone()) {
            boolean cancel = future.cancel(true);
            System.out.println("Is canceled :" + cancel);
        }

        //System.out.println(future.get());

        executorService.shutdown();
        System.out.println(Thread.currentThread().getName() + " - Done");

    }

    @RequiredArgsConstructor
    static class MyTask implements Callable<Long> {

        private final long value;

        @Override
        public Long call() throws Exception {
            if (value <= 0) {
                throw new IllegalArgumentException("Value must be positive!");
            }
            System.out.println(Thread.currentThread().getName() + " - Starting hard work...");
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName() + " - Done, returning result...");
            return value * 2;
        }
    }

}

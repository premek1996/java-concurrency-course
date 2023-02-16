package week5.tutorial2;

import java.util.Random;
import java.util.concurrent.*;

public class Example1 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<Integer> futureResult = executorService.submit(new Task());

        try {
            Integer result = futureResult.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }

    static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws InterruptedException {
            Thread.sleep(2_000);
            return new Random().nextInt();
        }
    }

}

package week4.homework.task4;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Task4 {

    public static void main(String[] args) {
        var forkJoinPool = ForkJoinPool.commonPool();
        var value = forkJoinPool.invoke(new FibonacciElementRecursiveTask(8));
        System.out.println(value);
    }

    @RequiredArgsConstructor
    static class FibonacciElementRecursiveTask extends RecursiveTask<Integer> {

        private final int n;

        @Override
        protected Integer compute() {
            if (n == 1 || n == 2) {
                return 1;
            } else {
                var fibonacciElementRecursiveTask1 = new FibonacciElementRecursiveTask(n - 1);
                var fibonacciElementRecursiveTask2 = new FibonacciElementRecursiveTask(n - 2);
                fibonacciElementRecursiveTask2.fork();
                return fibonacciElementRecursiveTask1.compute() + fibonacciElementRecursiveTask2.join();
            }
        }

    }

}

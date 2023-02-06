package week4.homework.task2;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class Task2 {

    public static void main(String[] args) {
        List<Integer> numbers = IntStream.rangeClosed(1, 1000)
                .boxed()
                .toList();

        System.out.println(numbers.stream().reduce(0, Integer::sum));

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        int sum = forkJoinPool.invoke(new SumTask(numbers));
        System.out.println(sum);
    }

    @RequiredArgsConstructor
    static class SumTask extends RecursiveTask<Integer> {

        private final List<Integer> numbers;

        @Override
        protected Integer compute() {
            if (numbers.size() <= 2) {
                return numbers.stream().reduce(0, Integer::sum);
            } else {
                var sumTask1 = new SumTask(numbers.subList(0, numbers.size() / 2));
                var sumTask2 = new SumTask(numbers.subList(numbers.size() / 2, numbers.size()));
                return sumTask1.fork().join() + sumTask2.fork().join();
            }
        }

    }

}

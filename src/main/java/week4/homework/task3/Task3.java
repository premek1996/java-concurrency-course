package week4.homework.task3;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.stream.IntStream;

public class Task3 {

    public static void main(String[] args) {
        var numbers = IntStream.rangeClosed(1, 10)
                .boxed()
                .toList();

        var forkJoinPool = ForkJoinPool.commonPool();
        forkJoinPool.invoke(new PrintDoubledListRecursiveAction(numbers));
    }

    @RequiredArgsConstructor
    static class PrintDoubledListRecursiveAction extends RecursiveAction {

        private final List<Integer> numbers;

        @Override
        protected void compute() {
            if (numbers.size() <= 2) {
                numbers.stream()
                        .map(x -> x * 2)
                        .forEach(System.out::println);
            } else {
                var printListAction1 = new PrintDoubledListRecursiveAction(numbers.subList(0, numbers.size() / 2));
                var printListAction2 = new PrintDoubledListRecursiveAction(numbers.subList(numbers.size() / 2, numbers.size()));
                invokeAll(printListAction1, printListAction2);
            }
        }

    }

}



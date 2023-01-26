package week2.homework.task;

import lombok.SneakyThrows;
import week2.homework.data.Data;

import java.util.stream.IntStream;

public class CalculateSumTask implements Runnable {

    private final Data data;

    public CalculateSumTask(Data data) {
        this.data = data;
    }

    @Override
    @SneakyThrows
    public void run() {
        while (true) {
            try {
                System.out.println("Sum: " + calculateSum());
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private int calculateSum() {
        var number = data.readNumber();
        return IntStream.rangeClosed(1, number).sum();
    }

}

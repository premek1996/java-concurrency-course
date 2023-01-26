package week2.homework.task;

import lombok.SneakyThrows;
import week2.homework.data.Data;

import java.util.concurrent.ThreadLocalRandom;

public class GenerateNumberTask implements Runnable {

    private final Data data;
    private final int minRange;
    private final int maxRange;

    public GenerateNumberTask(Data data, int minRange, int maxRange) {
        this.data = data;
        this.minRange = minRange;
        this.maxRange = maxRange;
    }

    @Override
    @SneakyThrows
    public void run() {
        while (true) {
            try {
                var number = ThreadLocalRandom.current()
                        .nextInt(minRange, maxRange + 1);
                data.saveNumber(number);
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}

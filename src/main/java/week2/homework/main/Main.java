package week2.homework.main;

import lombok.SneakyThrows;
import week2.homework.data.Data;
import week2.homework.task.CalculateSumTask;
import week2.homework.task.GenerateNumberTask;
import week2.homework.task.StatisticsTask;

public class Main {

    private static final long DURATION_TIME = 5_000;

    @SneakyThrows
    public static void main(String[] args) {

        var data = new Data();

        var generateNumberTask1 = new GenerateNumberTask(data, 1, 50);
        var generateNumberTask2 = new GenerateNumberTask(data, 100, 500);
        var calculateSumTask = new CalculateSumTask(data);
        var statisticsTask = new StatisticsTask();


        var generateNumberThread1 = new Thread(generateNumberTask1, "generateNumberThread1");
        var generateNumberThread2 = new Thread(generateNumberTask2, "generateNumberThread2");
        var calculateSumThread = new Thread(calculateSumTask, "calculateSumThread");
        var statisticsThread = new Thread(statisticsTask, "statisticsThread");
        statisticsThread.setDaemon(true);

        generateNumberThread1.start();
        generateNumberThread2.start();
        calculateSumThread.start();
        statisticsThread.start();

        Thread.sleep(DURATION_TIME);

        generateNumberThread1.interrupt();
        generateNumberThread2.interrupt();
        calculateSumThread.interrupt();

        System.out.println("Done");
    }

}

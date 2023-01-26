package week2.homework.task;

import lombok.SneakyThrows;

public class StatisticsTask implements Runnable {

    private static final String MAIN_GROUP_NAME = "main";

    @Override
    @SneakyThrows
    public void run() {
        while (true) {
            printAllThreadsInfo();
            Thread.sleep(1_000);
        }
    }

    private static void printAllThreadsInfo() {
        Thread.getAllStackTraces().keySet()
                .stream()
                .filter(StatisticsTask::isInMainGroup)
                .forEach(StatisticsTask::printThreadInfo);
    }

    private static void printThreadInfo(Thread thread) {
        System.out.println(thread.getName() + " " + thread.getState());
    }

    private static boolean isInMainGroup(Thread thread) {
        return thread.getThreadGroup() != null && thread.getThreadGroup()
                .getName()
                .equals(MAIN_GROUP_NAME);
    }

}

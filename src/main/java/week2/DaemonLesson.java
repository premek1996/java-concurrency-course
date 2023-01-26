package week2;

public class DaemonLesson {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " - Starting...");

        Runnable diagnosticTask = () -> {
            try {
                while (true) {
                    Runtime runtime = Runtime.getRuntime();
                    System.out.println("maxMemory: " + runtime.maxMemory());
                    System.out.println("allocatedMemory: " + runtime.totalMemory());
                    System.out.println("feeMemory: " + runtime.freeMemory());
                    System.out.println();
                    Thread.sleep(1_000);
                }
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        };

        Thread diagnosticThread = new Thread(diagnosticTask);
        diagnosticThread.setDaemon(true);
        diagnosticThread.start();
        System.out.println(diagnosticThread.isDaemon());

        Thread.sleep(5_000);

        System.out.println(Thread.currentThread().getName() + " - Done");
    }

}

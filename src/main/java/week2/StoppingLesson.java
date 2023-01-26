package week2;

public class StoppingLesson {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " - Starting...");
        Telemetric task = new Telemetric();
        Thread t0 = new Thread(task);
        t0.start();
        Thread.sleep(5_000);
        task.stopTask();
        t0.join();
        System.out.println(t0.getState());
        System.out.println(Thread.currentThread().getName() + " - Done...");
    }

    static class Telemetric implements Runnable {

        private volatile boolean isRunning = false;

        @Override
        public void run() {
            isRunning = true;
            while (isRunning) {
                System.out.println("Free memory: " + Runtime.getRuntime().freeMemory());
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            }
        }

        public void stopTask() {
            isRunning = false;
        }
    }

}

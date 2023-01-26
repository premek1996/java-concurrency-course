package week2;

public class PrioritiesLesson {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " - Starting...");

        Thread thread1 = new Thread(new Task("Running in a MAX_PRIORITY"));
        thread1.setPriority(Thread.MAX_PRIORITY);
        thread1.start();

        Thread thread2 = new Thread(new Task("Running in a MIN_PRIORITY"));
        thread2.setPriority(Thread.MIN_PRIORITY);
        thread2.start();

        System.out.println(Thread.currentThread().getName() + " - Done...");
    }

    static class Task implements Runnable {

        private final String message;

        Task(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            for (int i = 0; i < 30; i++) {
                System.out.println(Thread.currentThread().getName() + " - Task... " + message);
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

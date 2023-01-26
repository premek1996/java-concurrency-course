package week2;

public class JoinLesson {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " - Starting...");

        Runnable task = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " - Runnable");
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread = new Thread(task);
        thread.start();
        thread.join(2_000);

        System.out.println(Thread.currentThread().getName() + " - Done...");
    }


}

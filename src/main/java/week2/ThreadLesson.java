package week2;

public class ThreadLesson {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " - Starting...");

        var thread = new Thread("Working Thread") {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " - I'm running in a thread");
            }
        };
        thread.start();

        System.out.println(Thread.currentThread().getName() + " - Done");
    }

}

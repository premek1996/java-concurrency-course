package week2;

public class RunnableLesson {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " - Starting...");
        MyCarTask myCarTask = new MyCarTask();
        Thread thread = new Thread(myCarTask);
        thread.start();
        Thread thread2 = new Thread(myCarTask);
        thread2.start();
        Thread thread3 = new Thread(myCarTask);
        thread3.start();
        System.out.println(Thread.currentThread().getName() + " - Done");

        MyTaskThread myTaskThread = new MyTaskThread();
        myTaskThread.start();
        MyTaskThread myTaskThread2 = new MyTaskThread();
        myTaskThread2.start();
        MyTaskThread myTaskThread3 = new MyTaskThread();
        myTaskThread3.start();
    }

    abstract static class Car {

        public String drive() {
            return " Drive....";
        }
    }

    static class MyCarTask extends Car implements Runnable {
        int counter = 0;

        @Override
        public void run() {
            counter++;
            System.out.println(Thread.currentThread().getName() + drive() + " " + counter);

        }
    }

    static class MyTaskThread extends Thread {
        int counter = 0;

        @Override
        public void run() {
            counter++;
            System.out.println(Thread.currentThread().getName() + " " + counter);
        }
    }

}

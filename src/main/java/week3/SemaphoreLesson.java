package week3;

import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;

public class SemaphoreLesson {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " - Starting...");
        Restaurant restaurant = new Restaurant(5);
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(restaurant::stepInto);
            thread.start();
        }
        System.out.println(Thread.currentThread().getName() + " - Done");
    }

    static class Restaurant {

        private Semaphore semaphore;

        Restaurant(int seats) {
            this.semaphore = new Semaphore(seats);
        }

        @SneakyThrows
        public void stepInto() {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " - enter restaurant");
                Thread.sleep(3_000);
            } finally {
                System.out.println(Thread.currentThread().getName() + " - leave restaurant");
                semaphore.release();
            }

        }

    }

}

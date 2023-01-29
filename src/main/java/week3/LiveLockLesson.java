package week3;

import lombok.SneakyThrows;

public class LiveLockLesson {

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " - Starting...");
        Object spoon = new Object();
        Person wife = new Person(spoon);
        Person husband = new Person(spoon);

        Thread t0 = new Thread(() -> wife.eatWith(husband), "WIFE");
        Thread t1 = new Thread(() -> husband.eatWith(wife), "HUSBAND");

        t0.start();
        t1.start();

        t0.join();
        t1.join();

        System.out.println(Thread.currentThread().getName() + " - Done");
    }

    static class Person {

        private final Object spoon;
        private boolean isHungry = true;

        public Person(Object spoon) {
            this.spoon = spoon;
        }

        public boolean isHungry() {
            return isHungry;
        }

        @SneakyThrows
        public void eatWith(Person partner) {
            while (isHungry) {
                synchronized (spoon) {
                    if (partner.isHungry()) {
                        System.out.println(Thread.currentThread().getName() + " Please eat - I can wait!");
                    } else {
                        System.out.println(Thread.currentThread().getName() + " Delicious!");
                        isHungry = false;
                    }
                }
            }
        }

    }

}



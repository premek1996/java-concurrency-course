package week2.homework.data;

import lombok.SneakyThrows;

public class Data {

    private Integer number;

    @SneakyThrows
    public synchronized void saveNumber(Integer num) {
        while (this.number != null) {
            wait();
        }
        this.number = num;
        System.out.println("Generated number: " + number);
        notifyAll();
    }

    @SneakyThrows
    public synchronized Integer readNumber() {
        while (number == null) {
            wait();
        }
        Integer temp = number;
        number = null;
        System.out.println("Read number: " + temp);
        notifyAll();
        return temp;
    }

}

package week2;

public class CommunicationLesson {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " - Starting...");
        Data data = new Data();
        WriterTask writerTask = new WriterTask(data);
        ReaderTask readerTask = new ReaderTask(data);

        Thread writerThread = new Thread(writerTask);
        Thread readerThread = new Thread(readerTask);

        writerThread.start();
        readerThread.start();

        System.out.println(Thread.currentThread().getName() + " - Done");
    }

    static class Data {

        private String message = null;

        public synchronized void write(String msg) throws InterruptedException {
            while (message != null) {
                System.out.println("Waiting in writing");
                wait();
            }
            System.out.println("Writing");
            message = msg;
            notifyAll();
        }

        public synchronized String read() throws InterruptedException {
            while (message == null) {
                System.out.println("Waiting in reading");
                wait();
            }
            System.out.println("Reading");
            String tmp = message;
            message = null;
            notifyAll();
            return tmp;
        }

    }

    static class WriterTask implements Runnable {

        private final Data data;
        int counter = 0;

        public WriterTask(Data data) {
            this.data = data;
        }

        @Override
        public void run() {
            while (true) {
                counter++;
                String message = "Message: " + counter;
                try {
                    data.write(message);
                    Thread.sleep(2_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ReaderTask implements Runnable {

        private final Data data;

        ReaderTask(Data data) {
            this.data = data;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println(data.read());
                    Thread.sleep(2_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}



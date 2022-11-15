package ex1;

public class Main {
    public static void main(String[] args) {
        int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
        Thread[] t = new Thread[NUMBER_OF_THREADS];

        for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
            t[i] = new MyThread(i);
            t[i].start();
        }

        for (int i = 0; i < NUMBER_OF_THREADS; ++i) {
            try {
                t[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class MyThread extends Thread {
    private final int id;

    public MyThread(int id) {
        this.id = id;
    }

    public void run() {
        System.out.println("Hello from thread " + id);
    }
}
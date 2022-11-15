package doubleVectorElements;

import static java.lang.Math.ceil;
import static java.lang.Math.min;

public class Main extends Thread {
    private final int[] v;
    private final int tid;
    private final double N;
    private final double numThreads;

    Main(int[] v, int tid, int N, int numThreads) {
        this.v          = v;
        this.tid        = tid;
        this.N          = (double)N;
        this.numThreads = (double)numThreads;
    }

    public void run() {
        final int start = tid * (int)ceil(N / numThreads);
        final int end   = min((int)N, (tid + 1) * (int)ceil(N / numThreads));

        for (int i = start; i < end; ++i) {
            v[i] *= 2;
        }
    }

    public static void main(String[] args) {
        int N = 100000013;
        int[] v = new int[N];
        int P = 4; // the program should work for any P <= N

        for (int i = 0; i < N; i++) {
            v[i] = i;
        }

        // Parallelize me using P threads
        /*for (int i = 0; i < N; i++) {
            v[i] = v[i] * 2;
        }*/
        Thread[] threads = new Thread[P];
        for (int i = 0; i < P; i++) {
            threads[i] = new Main(v, i, N, P);
            threads[i].start();
        }
        for (int i = 0; i < P; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < N; i++) {
            if (v[i] != i * 2) {
                System.out.println("Wrong answer");
                System.exit(1);
            }
        }
        System.out.println("Correct");
    }

}

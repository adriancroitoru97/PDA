package shortestPathsFloyd_Warshall;

import static java.lang.Math.ceil;
import static java.lang.Math.min;

public class Main extends Thread {
    private final int[][] graph;
    private final int tid;
    private final double N;
    private final double numThreads;

    Main(int[][] graph, int tid, int N, int numThreads) {
        this.graph      = graph;
        this.tid        = tid;
        this.N          = (double)N;
        this.numThreads = (double)numThreads;
    }

    public void run() {
        final int start = tid * (int)ceil(N / numThreads);
        final int end   = min((int)N, (tid + 1) * (int)ceil(N / numThreads));

        for (int k = start; k < end; k++) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    graph[i][j] = Math.min(graph[i][k] + graph[k][j], graph[i][j]);
                }
            }
        }
    }

    public static void main(String[] args) {
        int M = 9;
        int[][] graph = {{0, 1, M, M, M},
                {1, 0, 1, M, M},
                {M, 1, 0, 1, 1},
                {M, M, 1, 0, M},
                {M, M, 1, M, 0}};

        // Parallelize me (You might want to keep the original code in order to compare)
        /*for (int k = 0; k < 5; k++) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    graph[i][j] = Math.min(graph[i][k] + graph[k][j], graph[i][j]);
                }
            }
        }*/
        int P = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[P];
        for (int i = 0; i < P; i++) {
            threads[i] = new Main(graph, i, 5, P);
            threads[i].start();
        }
        for (int i = 0; i < P; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }
    }
}

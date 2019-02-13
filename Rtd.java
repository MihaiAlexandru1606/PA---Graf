import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class Rtd {
    private static final String INPUT_FILE_NAME     = "rtd.in";
    private static final String OUTPUT_FILE_NAME    = "rtd.out";

    public static void main(String[] args) throws IOException {
        /**citirea datelor */
        BufferedReader bufferedReader = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        int N, M, K, Sx, Sy, Fx, Fy;
        StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine().trim());
        N = Integer.parseInt(stringTokenizer.nextToken());
        M = Integer.parseInt(stringTokenizer.nextToken());
        Sx = Integer.parseInt(stringTokenizer.nextToken()) - 1;
        Sy = Integer.parseInt(stringTokenizer.nextToken()) - 1;
        Fx = Integer.parseInt(stringTokenizer.nextToken()) - 1;
        Fy = Integer.parseInt(stringTokenizer.nextToken()) - 1;
        K = Integer.parseInt(stringTokenizer.nextToken());
        int[][] block = new int[N][M];
        int[] cost = new int[6];
        stringTokenizer = new StringTokenizer(bufferedReader.readLine().trim());
        cost[0] = Integer.parseInt(stringTokenizer.nextToken());
        cost[1] = Integer.parseInt(stringTokenizer.nextToken());
        cost[2] = Integer.parseInt(stringTokenizer.nextToken());
        cost[3] = Integer.parseInt(stringTokenizer.nextToken());
        cost[4] = Integer.parseInt(stringTokenizer.nextToken());
        cost[5] = Integer.parseInt(stringTokenizer.nextToken());

        for (int k = 0; k < K; k++) {
            stringTokenizer = new StringTokenizer(bufferedReader.readLine().trim());
            int i, j;
            i = Integer.parseInt(stringTokenizer.nextToken());
            j = Integer.parseInt(stringTokenizer.nextToken());
            block[i - 1][j - 1] = 1;
        }

        /**printarea rezultatului*/
        bufferedWriter.write(ShortestPathAux.getShortestPath(N, M, cost, block, Sx, Sy, Fx, Fy) + "");
        bufferedReader.close();
        bufferedWriter.close();
    }
}

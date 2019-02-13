import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class Revedges {
    private static final int INF = 1 << 29;
    private static final int EXIST_EDGE = 0;
    private static final int CREATE_EDGE = 1;
    private static final String INPUT_FILE_NAME = "revedges.in";
    private static final String OUTPUT_FILE_NAME = "revedges.out";

    /**
     * Idea de rezolvarea una simpla, pentru aflarrea numarul de muchi ce trebui inversate, consideram
     * ca muchile care exista au cost 0, si pentru fiecare muchie intre doua noduri u si v care
     * avem u -> v, dar nu exista v -> u, asa ca creem o muchie fictiva cu costul 1.
     * Pe idea asta si faptul ca stim ca graful este conex, aplicam un algoritm de distanta minima
     * pe nou graf creat si distanta este 0, daca aveam inante cale intre cele doua noduri sau
     * numarul de muchii crete pentru a avea cela intre cele 2 noduri
     */
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        int numberVertex, numberEdge, query;
        StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine().trim());
        numberVertex = Integer.parseInt(stringTokenizer.nextToken());
        numberEdge = Integer.parseInt(stringTokenizer.nextToken());
        query = Integer.parseInt(stringTokenizer.nextToken());
        long[][] d = new long[numberVertex + 1][numberVertex + 1];

        for (int i = 1; i <= numberVertex; i++) {
            for (int j = 1; j <= numberVertex; j++) {
                if (i == j) {
                    d[i][j] = EXIST_EDGE;/** tot timplu va exista o muche de la un nod la el
                     insuri */
                } else
                    d[i][j] = INF;
            }
        }

        for (int i = 0; i < numberEdge; i++) {
            int u, v;
            stringTokenizer = new StringTokenizer(bufferedReader.readLine().trim());
            u = Integer.parseInt(stringTokenizer.nextToken());
            v = Integer.parseInt(stringTokenizer.nextToken());
            d[u][v] = EXIST_EDGE;

            if (d[v][u] == INF) /** daca nu exita muchie crea o muchie fictiva */
                d[v][u] = CREATE_EDGE;
        }

        /** se aplica algoritmul Floyd Warshall pentru aflarea distante minime*/
        for (int k = 1; k <= numberVertex; k++) {
            for (int i = 1; i <= numberVertex; i++) {
                for (int j = 1; j <= numberVertex; j++) {
                    if (d[i][j] > d[i][k] + d[k][j]) {
                        d[i][j] = d[i][k] + d[k][j];
                    }
                }
            }
        }

        /**se raspunde la toate query */
        for (int i = 0; i < query; i++) {
            int u, v;
            stringTokenizer = new StringTokenizer(bufferedReader.readLine().trim());
            u = Integer.parseInt(stringTokenizer.nextToken());
            v = Integer.parseInt(stringTokenizer.nextToken());

            bufferedWriter.write(d[u][v] + "\n");
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Disjcnt {

    /**
     * dest -> nodul destinatie
     * remove -> daca a fost eliminata de algorimul lui tarjan pentru aflarea puntilor
     * twin -> "geamana" sa, pentru (1, 2) , fiind un graf neorientat, avem muchie de la 1 la 2
     * si muchie de la 2 la 1, geamana lui 1 -> 2 este 2 -> 1 si invers
     */
    private static class Edge {
        private int dest;
        private boolean remove;
        private Edge twin;

        public Edge(int dest) {
            this.dest = dest;
            this.remove = false;
            this.twin = null;
        }

        public int getDest() {
            return dest;
        }

        public boolean isRemove() {
            return remove;
        }

        public void setRemove(boolean remove) {
            this.remove = remove;
        }

        public Edge getTwin() {
            return twin;
        }

        public void setTwin(Edge twin) {
            this.twin = twin;
        }
    }

    private static final String INPUT_FILE_NAME     = "disjcnt.in";
    private static final String OUTPUT_FILE_NAME    = "disjcnt.out";

    private static int[] timeDiscover;
    private static int[] low;
    private static boolean[] visited;
    private static int[] parent;
    private static int time = 0;

    /**
     * dfs utilizat in algoritmul lui tarjan
     * @param current nodul curent
     * @param graph graful
     */
    private static void dfs_util_tarjan(int current, ArrayList<Edge>[] graph) {
        timeDiscover[current] = time;
        low[current] = time;
        time++;
        visited[current] = true;

        for (Edge neighbour : graph[current]) {
            int i = neighbour.getDest();

            if (visited[i] == false) {
                parent[i] = current;
                dfs_util_tarjan(i, graph);

                low[current] = Math.min(low[i], low[current]);

                if (timeDiscover[current] < low[i]) {
                    /**se gasescte muchie ca fiind o punte si se macheza ca si cum nu ar mai
                     * exisata
                     */
                    neighbour.setRemove(true);
                    neighbour.getTwin().setRemove(true);
                }

            } else if (i != parent[current]) {
                low[current] = Math.min(low[current], timeDiscover[i]);
            }
        }
    }

    private static void tarjan(int numberVertex, ArrayList<Edge>[] graph) {
        for (int i = 1; i <= numberVertex; i++) {
            if (visited[i] == false)
                dfs_util_tarjan(i, graph);
        }

    }

    /**
     * dfs care numara cate noduri sunt conectate cu current, se folosesc decat muchile care nu
     * au fost marcate ca fiind eliminate de tarjan, care nu sunt punti,
     * @param current nodul curent pentru dfs
     * @param graph garul
     */
    private static void dfs(int current, ArrayList<Edge>[] graph, long[] number) {
        visited[current] = true;

        for (Edge neighbour : graph[current]) {
            int v = neighbour.getDest();
            if (visited[v] == false && neighbour.isRemove() == false) {
                dfs(v, graph, number);
            }
        }

        number[0]++;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        int numberVertex, numberEdge;
        StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
        numberVertex = Integer.parseInt(stringTokenizer.nextToken());
        numberEdge = Integer.parseInt(stringTokenizer.nextToken());

        @SuppressWarnings("unchecked")
        ArrayList<Edge>[] graph = new ArrayList[numberVertex + 1];
        for (int i = 1; i <= numberVertex; i++) {
            graph[i] = new ArrayList<>();
        }
        /** citirea garfului */
        for (int i = 1; i <= numberEdge; i++) {
            int u, v;
            stringTokenizer = new StringTokenizer(bufferedReader.readLine().trim());
            u = Integer.parseInt(stringTokenizer.nextToken());
            v = Integer.parseInt(stringTokenizer.nextToken());
            Edge e = new Edge(v);
            Edge twinE = new Edge(u);
            e.setTwin(twinE);
            twinE.setTwin(e);

            graph[u].add(e);
            graph[v].add(twinE);
        }

        visited = new boolean[numberVertex + 1];
        parent = new int[numberVertex + 1];
        low = new int[numberVertex + 1];
        timeDiscover = new int[numberVertex + 1];
        tarjan(numberVertex, graph); /** eliminarea puntilor folosind tarjan */

        for (int i = 0; i <= numberVertex; i++) {
            visited[i] = false;
        }

        long numberPair = 0; /** numarul de perechi care respecta propritatea */

        for (int i = 1; i <= numberVertex; i++) {
            /** se aplica un dfs care numar cate mai sunt conecate cu i */
            if (visited[i] == false) {
                long[] number = new long[1];
                dfs(i, graph, number);
                numberPair += (number[0] - 1) * (number[0]) / 2;
            }
        }

        bufferedWriter.write(numberPair + "\n");
        bufferedReader.close();
        bufferedWriter.close();
    }
}

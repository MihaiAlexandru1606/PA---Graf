import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Queue;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.ArrayList;

public class Minlexbfs {

    private static final String INPUT_FILE_NAME = "minlexbfs.in";
    private static final String OUTPUT_FILE_NAME = "minlexbfs.out";
    private static final int SOURCE_NODE = 1;

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        int numberVertex, numberEdge;
        StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine().trim());
        numberVertex = Integer.parseInt(stringTokenizer.nextToken());
        numberEdge = Integer.parseInt(stringTokenizer.nextToken());
        Queue<Integer> queue = new LinkedList<>();/**folosita pentru BFS */
        boolean[] visited = new boolean[numberVertex + 1];

        /** vom tine "muchile" ordonate dupa destinatie intr-un TreeSet */
        /** pastran intr-un TreeSet fata de un ArratList, asta ne garanteaza ca parcurgerea v
         *  vecinilot lui v se face in ordine lexico-grafica
         */
        @SuppressWarnings("unchecked")
        TreeSet<Integer>[] graph = new TreeSet[numberVertex + 1];
        for (int i = 1; i <= numberVertex; i++) {
            /** initializare grafului */
            graph[i] = new TreeSet<>();
        }

        /**citirea muchilor din fisier, cum este neorienta avem muchie de la u -> v, dar si v-> u*/
        for (int i = 0; i < numberEdge; i++) {
            int u, v;
            stringTokenizer = new StringTokenizer(bufferedReader.readLine().trim());
            u = Integer.parseInt(stringTokenizer.nextToken());
            v = Integer.parseInt(stringTokenizer.nextToken());
            graph[u].add(v);
            graph[v].add(u);
        }

        /** pentru a obtine cea mai mica in ordine lexico-grafica se porneste de la 1 */
        /** un BFS normal */
        queue.add(SOURCE_NODE);
        visited[SOURCE_NODE] = true;
        int numberVisited = 1;       
        ArrayList<Integer> ansewr = new ArrayList<>();
        BFS:
        while (!queue.isEmpty()) {
            int current = queue.poll();
            ansewr.add(current); /** se adauga nodul curent la raspuns */
                        
            /** pentru fiecare vecin care nu a fost vizitat */
            for (int i : graph[current]) {
                if (visited[i] == false) {
                    queue.add(i);
                    visited[i] = true;
                    numberVisited++;
                    if(numberVisited == numberVertex){
                       while(!queue.isEmpty()) {
                          int c = queue.poll();
                          ansewr.add(c); /** se adauga nodul curent la raspuns */
                       }
                       break BFS;
                    }
                }

            }
        }

        /** printarea raspunsului */
        for (int i : ansewr) {
            bufferedWriter.write(i + " ");
        }
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }

}

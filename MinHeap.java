import java.util.TreeSet;

public class MinHeap {

    /**
     * Clasa interna folosita pentru informatile despre nod:
     * dist -> distanta minima pana la sursa
     * node -> nodul
     */
    private static class InfoNode implements Comparable<InfoNode> {
        int dist;
        int node;

        public InfoNode(int dist, int node) {
            this.dist = dist;
            this.node = node;
        }

        @Override
        public int compareTo(InfoNode o) {
            if (dist == o.dist)
                return node - o.node;
            else
                return dist - o.dist;
        }
    }

    private InfoNode[] array;
    private TreeSet<InfoNode> ordonete; 
    private boolean[] contains; 
    private int size;

    public MinHeap(int n) {
        this.array = new InfoNode[n];
        this.ordonete = new TreeSet<>();
        this.contains = new boolean[n];
        this.size = n - 1;
    }

    /**
     * Adauga nod-ul daca nu il contine TreeSet-ul altfel, actualizarea distanata catre sursa
     * @param node nod-ul
     * @param dist distanat nodului catre sursa
     */
    public void add(int node, int dist) {
        if (!contains[node]) {
            array[node] = new InfoNode(dist, node);
            ordonete.add(array[node]);
        } else {
            ordonete.remove(array[node]);
            array[node] = new InfoNode(dist, node);
            ordonete.add(array[node]);
        }
        contains[node] = true;
    }

    /** daca un nod se mai afla*/
    public boolean contains(int node) {
        return this.contains[node];
    }

    /**
     * @return nodul cu distanta minima catre sursa si il elimina
     */
    public int getMinNode(){
        InfoNode ret = ordonete.pollFirst();
        contains[ret.node] = false;
        this.size--;
        return ret.node;
    }

    /**
     * @return daca nu mai sunt noduri in tree
     */
    public boolean isEmpty(){
        return ordonete.isEmpty();
    }

}

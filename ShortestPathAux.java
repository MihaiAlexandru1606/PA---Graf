public class ShortestPathAux {

    private static final int INF = 1 << 27;
    private static final int NUMBER_STATE = 24;

    public static int getShortestPath(int N, int M, int[] cost, int[][] block, int Sx, int Sy,
                                      int Fx, int Fy) {
        int[][][] grid = new int[N][M][NUMBER_STATE]; /** distanta minima de la sursa la celalate
                                                          poziti pentru fiecare stare a zarului*/
        MinHeap heap = new MinHeap(N * M * NUMBER_STATE + 1);
        Dice dice = new Dice(cost);


        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (block[i][j] == 1)
                    continue;

                /** k este numarul stari in care se afla zarul */
                for (int k = 1; k <= NUMBER_STATE; k++) {
                    if (i == Sx && j == Sy && k == 1) {
                        grid[i][j][k - 1] = cost[0]; /** pozitia de start a zarului*/
                        heap.add((i * M + j) * NUMBER_STATE + k, grid[i][j][k - 1]);
                    } else {
                        grid[i][j][k - 1] = INF;
                        heap.add((i * M + j) * NUMBER_STATE + k, grid[i][j][k - 1]);
                    }
                }
            }
        }

        int[] dx = {1, 0, -1, 0}; /** pentru deplasarea pe Ox*/
        int[] dy = {0, 1, 0, -1}; /** pentru deplasarea pe Oy*/

        while (!heap.isEmpty()) {

            /** "decoficarea", fiecare perechi pozitie, stare zar i se atribui un nod */
            int cod = heap.getMinNode() - 1; /**extargem minimul */
            int state = cod % NUMBER_STATE + 1;

            cod /= NUMBER_STATE;
            int x = cod / M;
            int y = cod % M;

            if (grid[x][y][state - 1] == INF)
                break;

            /** pentru fiecare vecin */
            for (int i = 0; i < 4; i++) {
                int new_x = x + dx[i];
                int new_y = y + dy[i];

                /** daca este o pozitie valida */
                if (new_x >= 0 && new_x < N && new_y >= 0 && new_y < M) {
                    /**stare in care se va afla zarul dupa efectuarea mutari */
                    int nexStare = dice.getNextState(dx[i], dy[i], state);
                    /**costul acelei mutari */
                    int costMove = dice.costMove(dx[i], dy[i], state);

                    /** nu s-a calculat distanta minima inca*/
                    if (heap.contains((new_x * M + new_y) * NUMBER_STATE + nexStare)) {
                        /** se aplica relaxrea */
                        if (grid[new_x][new_y][nexStare - 1] > grid[x][y][state - 1] + costMove) {
                            grid[new_x][new_y][nexStare - 1] = grid[x][y][state - 1] + costMove;
                            /**actualiazam noua distanta*/
                            heap.add((new_x * M + new_y) * NUMBER_STATE + nexStare,
                                    grid[new_x][new_y][nexStare - 1]);
                        }

                    }
                }
            }

        }

        /** cautam stara cu distanta cea mai mica*/
        int min = Integer.MAX_VALUE;
        for (int k = 0; k < NUMBER_STATE; k++) {
            min = Math.min(min, grid[Fx][Fy][k]);
        }

        return min;
    }
}

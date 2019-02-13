import java.util.TreeMap;

/**
 * Clasa care codifica zarului
 */
public class Dice {
    /** configuratia unui zar se retine astfel, sub forma unui numar de 6 cifre :
     *  cifra sutelor de mii -> fata pe care se afla zarul
     *  cifra zecilor de mii -> fata din spate, fata care s-ar afla/ ar sta zarul la mutarea din
     *                          (x, y) in (x - 1, y)
     *  cifra miilor         -> fata din  drepta, fata care s-ar afla/ ar sta zarul la mutarea din
     *                          (x, y) in (x, y + 1)
     *  cifra sutelor        -> fata din  stanga, fata care s-ar afla/ ar sta zarul la mutarea din
     *                         (x, y) in (x, y - 1)
     *  cifra zecilor        -> fata din  fata/inainte, fata care s-ar afla/ ar sta zarul la mutarea
     *                          din (x, y) in (x + 1, y)
     *  cifra unitatilor     -> fata diamentra opusa fatei pe care se afla zarul
     */
    private final static int DOWN_POSITION  = 100000;
    private final static int BACK_POSITION  = 10000;
    private final static int RIGHT_POSITION = 1000;
    private final static int LEFT_POSITION  = 100;
    private final static int FRONT_POSITION = 10;
    private final static int UP_POSITION    = 1;

    private final static int N = 7;

    public final static int DOWN    = 1;
    public final static int BACK    = 2;
    public final static int RIGHT   = 3;
    public final static int LEFT    = 4;
    public final static int FRONT   = 5;
    public final static int UP      = 6;

    private final int[] value; /** vectorul in care sunt tinute costul fiecarei fete */
    private final int[] key;   /** care sunt tinute configuratia fiecarei stari */
    private final TreeMap<Integer, Integer> decotor;/** creaza o mapare key[i] la i */

    public Dice(int[] value) {
        this.value = new int[N];
        this.key = new int[25];
        this.decotor = new TreeMap<>();

        this.value[1] = value[0];
        this.value[2] = value[1];
        this.value[3] = value[2];
        this.value[4] = value[3];
        this.value[5] = value[4];
        this.value[6] = value[5];

        this.key[1] = 123456;
        this.key[2] = 135246;
        this.key[3] = 154326;
        this.key[4] = 142536;

        this.key[5] = 214365;
        this.key[6] = 246135;
        this.key[7] = 263415;
        this.key[8] = 231645;

        this.key[9] = 312564;
        this.key[10] = 326154;
        this.key[11] = 365214;
        this.key[12] = 351624;

        this.key[13] = 415263;
        this.key[14] = 456123;
        this.key[15] = 462513;
        this.key[16] = 421653;

        this.key[17] = 513462;
        this.key[18] = 536142;
        this.key[19] = 564312;
        this.key[20] = 541632;

        this.key[21] = 624351;
        this.key[22] = 645231;
        this.key[23] = 653421;
        this.key[24] = 632541;

        for (int i = 1; i <= 24; i++) {
            decotor.put(key[i], i);
        }

    }

    /**
     * "expandeaza" numarul corespunzator unei stari intr-un vector
     * @param state starea in care se afla zarul
     * @return configuratia sub forma unui vector
     */
    private int[] stateToConfiguration(int state) {
        int[] config = new int[N];
        int cod = key[state];

        config[DOWN] = cod / 100000;
        cod %= 100000;
        config[BACK] = cod / 10000;
        cod %= 10000;
        config[RIGHT] = cod / 1000;
        cod %= 1000;
        config[LEFT] = cod / 100;
        cod %= 100;
        config[FRONT] = cod / 10;
        cod %= 10;
        config[UP] = cod;

        return config;
    }

    /**
     * trasnforma vectorul intr-un numar
     * @param configuration configuratia sub foma unui zar
     * @return "cod" stari
     */
    private int configurationToState(int[] configuration) {
        int key = 0;

        key += configuration[DOWN] * DOWN_POSITION + configuration[BACK] * BACK_POSITION +
                configuration[RIGHT] * RIGHT_POSITION + configuration[LEFT] * LEFT_POSITION +
                configuration[FRONT] * FRONT_POSITION + configuration[UP] * UP_POSITION;

        return decotor.get(key);
    }

    /**
     * costul unei mutari
     * @param dx depalsarea fata de pozitia acutala pe Ox
     * @param dy depalsarea fata de pozitia acutala pe Oy
     * @param state starea in care afla zarul
     * @return costul mutari
     */
    public int costMove(int dx, int dy, int state) {
        int[] configuration = stateToConfiguration(state);

        if (dx == 0 && dy == 1)
            return value[configuration[RIGHT]];

        if (dx == 0 && dy == -1)
            return value[configuration[LEFT]];

        if (dx == -1 && dy == 0)
            return value[configuration[BACK]];

        if (dx == 1 && dy == 0)
            return value[configuration[FRONT]];

        return 0;
    }


    /**
     * @param configuration configuratia actula
     * @return configuratia zarului pentru mutearea in fata
     */
    private int[] moveFront(int[] configuration) {
        int[] config = new int[N];

        config[DOWN]    = configuration[FRONT];
        config[BACK]    = configuration[DOWN];
        config[RIGHT]   = configuration[RIGHT];
        config[LEFT]    = configuration[LEFT];
        config[FRONT]   = configuration[UP];
        config[UP]      = configuration[BACK];

        return config;
    }

    /**
     * @param configuration configuratia actula
     * @return configuratia zarului pentru mutearea in spate
     */
    private int[] moveBack(int[] configuration) {
        int[] config = new int[N];

        config[DOWN]    = configuration[BACK];
        config[BACK]    = configuration[UP];
        config[RIGHT]   = configuration[RIGHT];
        config[LEFT]    = configuration[LEFT];
        config[FRONT]   = configuration[DOWN];
        config[UP]      = configuration[FRONT];

        return config;
    }

    /**
     * @param configuration configuratia actula
     * @return configuratia zarului pentru mutearea in dreapta
     */
    private int[] moveRight(int[] configuration) {
        int[] config = new int[N];

        config[DOWN]    = configuration[RIGHT];
        config[BACK]    = configuration[BACK];
        config[RIGHT]   = configuration[UP];
        config[LEFT]    = configuration[DOWN];
        config[FRONT]   = configuration[FRONT];
        config[UP]      = configuration[LEFT];

        return config;
    }

    /**
     * @param configuration configuratia actula
     * @return configuratia zarului pentru mutearea in stanga
     */
    private int[] moveLeft(int[] configuration) {
        int[] config = new int[N];

        config[DOWN]    = configuration[LEFT];
        config[BACK]    = configuration[BACK];
        config[RIGHT]   = configuration[DOWN];
        config[LEFT]    = configuration[UP];
        config[FRONT]   = configuration[FRONT];
        config[UP]      = configuration[RIGHT];

        return config;
    }

    /**
     * determina urmatoare stare a unui zar pentru o mutare
     * @param dx depalsarea fata de pozitia acutala pe Ox
     * @param dy depalsarea fata de pozitia acutala pe Oy
     * @param state starea in care afla zarul
     * @return urmatoarea stare
     */
    public int getNextState(int dx, int dy, int state) {
        int[] config = this.stateToConfiguration(state);

        if (dx == 0 && dy == 1) {
            return this.configurationToState(moveRight(config));
        }

        if (dx == 0 && dy == -1) {
            return this.configurationToState(moveLeft(config));
        }

        if (dx == 1 && dy == 0) {
            return this.configurationToState(moveFront(config));
        }

        if (dx == -1 && dy == 0) {
            return this.configurationToState(moveBack(config));
        }
        return 0;
    }

}

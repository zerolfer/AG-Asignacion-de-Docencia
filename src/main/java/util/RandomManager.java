package main.java.util;

import java.util.Random;

public class RandomManager {

    public static int seed = -1;
    private static Random random;
    private static RandomManager self;

    private RandomManager(int seed) {
        random = new Random(seed);
        RandomManager.seed = seed;
    }

    private RandomManager() {
        random = new Random();
        RandomManager.seed = seed;
        print("se construye con semilla de: " + seed);
    }

    private static void print(Object n) {
        System.out.println(n.toString());
    }

    public static RandomManager getInstance() {
        if (self == null) {
            if (seed == -1)
                self = new RandomManager();
            else self = new RandomManager(seed);
        }
        return self;
    }

    public static void destroyInstance() {
        self = null;
    }

    /**
     * Desde 0 hasta "hasta" INCLUSIVE
     *
     * @param hasta
     * @return
     */
    public int getRandomNumberInclusive(int hasta) {
        return random.nextInt(hasta + 1);
    }

    /**
     * Exclusivo, hasta <b>hasta</b>-1
     *
     * @param hasta
     * @return
     */
    public int getRandomNumber(int hasta) {
        return random.nextInt(hasta);
    }

    public int getRandomNumber(int desde, int hasta) {
        return random.nextInt(hasta - desde) + desde;
    }

    public float getRandomProbability() {
        return random.nextFloat();
    }

    public float getFloatRandomNumber(float desde, float hasta) {
        return (hasta - desde) * random.nextFloat() + desde;
    }
}

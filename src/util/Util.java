package util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Ravi Mohan
 */
public class Util {

    /**
     * Desde 0 hasta "hasta" INCLUSIVE
     *
     * @param hasta
     *
     * @return
     */
    public static int getRandomNumberInclusive(int hasta) {
        return new Random().nextInt(hasta+1);
    }

    public static int getRandomNumber(int hasta) {
        return new Random().nextInt(hasta);
    }


}
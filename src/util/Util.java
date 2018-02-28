package util;

import java.util.List;
import java.util.Random;

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
        return new Random().nextInt(hasta + 1);
    }

    public static int getRandomNumber(int hasta) {
        return new Random().nextInt(hasta);
    }

    public static String arrayToString(List<?> enume, String separador) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < enume.size(); i++) {
            sb.append(enume.get(i).toString());
            if (i != enume.size() - 1)
                sb.append(separador);
        }
        return sb.toString();
    }

    public static String arrayToString(List<?> enume) {
        return arrayToString(enume, ", ");
    }


}
package main.java.genetico.algoritmos.cruce;

import main.java.genetico.Individuo;
import main.java.util.RandomManager;

/**
 * Created by Sergio Florez on 25/03/2018.
 */
public class CrucePositionBased extends AbstractCruce {

    public CrucePositionBased(final float probabilidad) {
        super(probabilidad);
    }

    public CrucePositionBased() {
        super();
    }

    @Override
    public Individuo[] aplicar(Individuo padre1, Individuo padre2) {
        int length = padre1.size();
        boolean[] mascara = new boolean[length];
        for (int i = 0; i < length; i++) {
            if (RandomManager.getInstance().getRandomProbability() <= probabilidad)
                mascara[i] = true;
        }

        return aplicar(padre1, padre2, mascara);
    }

    public Individuo[] aplicar(Individuo padre1, Individuo padre2, boolean[] posiciones) {
        assert padre1.size() == padre2.size();
        int length = padre1.size();

        Individuo hijo1 = new Individuo(padre1.getCromosoma().clone());
        Individuo hijo2 = new Individuo(padre2.getCromosoma().clone());

        if (RandomManager.getInstance().getRandomProbability() <= probabilidad) {

            int idxHijo1 = 0;
            int idxHijo2 = 0;

            for (int idxPadres = 0; idxPadres < length; idxPadres++) {

                while (idxHijo1 < length && posiciones[idxHijo1])
                    idxHijo1++;
                while (idxHijo2 < length && posiciones[idxHijo2])
                    idxHijo2++;

//                if (posiciones[idxPadres]) {
                if (idxHijo1 < length && !contains(hijo1, posiciones, padre2.getCromosoma()[idxPadres]))
                    hijo1.getCromosoma()[idxHijo1++] = padre2.getCromosoma()[idxPadres];

                if (idxHijo2 < length && !contains(hijo2, posiciones, padre1.getCromosoma()[idxPadres]))
                    hijo2.getCromosoma()[idxHijo2++] = padre1.getCromosoma()[idxPadres];
//                }
            }
        }

        if (debug) {
            System.out.println("Padre 1: " + padre1);
            System.out.println("Padre 2: " + padre2);
            System.out.println("mascara: " + posiciones);
            System.out.println("HIJO 1: " + hijo1);
            System.out.println("HIJO 2: " + hijo2);
            System.out.println("HAY REPETIDOS HIJO 1= " + hijo1.checkHayRepetidos());
            System.out.println("HAY REPETIDOS HIJO 2= " + hijo2.checkHayRepetidos());
        }

        return new Individuo[]{hijo1, hijo2};

    }

    private boolean contains(Individuo individuo, boolean[] mascara, int integer) {
        for (int i = 0; i < individuo.size(); i++) {
            if (mascara[i] && integer == individuo.getCromosoma()[i])
                return true;
        }
        return false;
    }
}

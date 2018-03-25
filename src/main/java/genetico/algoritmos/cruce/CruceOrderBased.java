package main.java.genetico.algoritmos.cruce;

import main.java.genetico.Individuo;
import main.java.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public class CruceOrderBased extends AbstractCruce {

    public CruceOrderBased(final float probabilidad) {
        super(probabilidad);
    }

    @Override
    public Individuo[] aplicar(Individuo padre1, Individuo padre2, int desde, int hasta) {
        assert padre1.size() == padre2.size();
        int length = padre1.size();

        Individuo hijo1 = new Individuo(padre1.getCromosoma().clone());
        Individuo hijo2 = new Individuo(padre2.getCromosoma().clone());

        if (Math.random() <= probabilidad) {

            int idxHijo1 = 0;
            int idxHijo2 = 0;
            for (int idxPadres = 0; idxPadres < length; idxPadres++) {
                //if (idxPadres < desde || idxPadres > hasta) {
                while (idxHijo1 >= desde && idxHijo1 <= hasta)
                    idxHijo1++;
                while (idxHijo2 >= desde && idxHijo2 <= hasta)
                    idxHijo2++;

                if (!hijo1.contains(desde, hasta, idxHijo1, padre2.getCromosoma()[idxPadres]) && idxHijo1 < length) {
                    hijo1.getCromosoma()[idxHijo1++] = padre2.getCromosoma()[idxPadres];
                }

                if (!hijo2.contains(desde, hasta, idxHijo2, padre1.getCromosoma()[idxPadres]) && idxHijo2 < length) {
                    hijo2.getCromosoma()[idxHijo2++] = padre1.getCromosoma()[idxPadres];
                }
            }
        }

        if (debug) {
            System.out.println("Padre 1: " + padre1);
            System.out.println("Padre 2: " + padre2);
            System.out.println("desde: " + desde);
            System.out.println("hasta: " + hasta);
            System.out.println("HIJO 1: " + hijo1);
            System.out.println("HIJO 2: " + hijo2);
            System.out.println("HAY REPETIDOS HIJO 1= " + hijo1.checkHayRepetidos());
            System.out.println("HAY REPETIDOS HIJO 2= " + hijo2.checkHayRepetidos());
        }

        return new Individuo[]{hijo1, hijo2};

    }

}

package main.java.genetico.algoritmos.mutacion;

import main.java.genetico.Individuo;
import main.java.util.Util;

import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public class MutacionIntercambio implements AlgoritmoMutacion {

    private final float probabilidad;
    private static final boolean debug=false;

    public MutacionIntercambio(final float probabilidad) {
        this.probabilidad = probabilidad;
    }

    @Override
    public void mutar(List<Individuo[]> individuos) {
        for (Individuo[] par : individuos) {
            for (Individuo individuo : par) {
                if (Math.random() <= probabilidad) {
                    if(debug) System.out.println("MUTACION:\n\tAntes: \t "+individuo);
                    int pos1 = Util.getRandomNumber(individuo.size());
                    int pos2;
                    do {
                        pos2 = Util.getRandomNumber(individuo.size());
                    } while ((pos1 == pos2));

                    // swap
                    int valor = individuo.getCromosoma()[pos1];
                    individuo.getCromosoma()[pos1] = individuo.getCromosoma()[pos2];
                    individuo.getCromosoma()[pos2] = valor;

                    if(debug) System.out.println("\tDespues: "+individuo);
                }
            }

        }
    }
}

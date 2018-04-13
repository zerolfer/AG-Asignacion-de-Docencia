package main.java.genetico.algoritmos.mutacion;

import main.java.genetico.Individuo;
import main.java.util.RandomManager;

import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public class MutacionIntercambio implements AlgoritmoMutacion {

    private static final boolean debug = false;
    private final float probabilidad;

    public MutacionIntercambio(final float probabilidad) {
        this.probabilidad = probabilidad;
    }

    @Override
    public void mutar(List<Individuo[]> individuos) {
        for (Individuo[] par : individuos) {
            for (Individuo individuo : par) {
                mutar(individuo);
            }

        }
    }

    @Override
    public void mutar(Individuo individuo) {
        if (RandomManager.getInstance().getTrialProbability() <= probabilidad) {
            if (debug) System.out.println("MUTACION:\n\tAntes: \t " + individuo);
            int pos1 = RandomManager.getInstance().getRandomNumber(individuo.size());
            int pos2;
            do {
                pos2 = RandomManager.getInstance().getRandomNumber(individuo.size());
            } while ((pos1 == pos2));

            // swap
            int valor = individuo.getCromosoma()[pos1];
            individuo.getCromosoma()[pos1] = individuo.getCromosoma()[pos2];
            individuo.getCromosoma()[pos2] = valor;

            if (debug) System.out.println("\tDespues: " + individuo);
        }
    }
}

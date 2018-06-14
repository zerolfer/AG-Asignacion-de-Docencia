package main.java.genetico.algoritmos.mutacion;

import main.java.genetico.Individuo;
import main.java.util.Util;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public class MutacionIntercambio extends AbstractMutacion {

    public MutacionIntercambio(final float probabilidad) {
        super(probabilidad);
    }

    @Override
    protected void estrategiaDeMutacion(Individuo individuo, int pos1, int pos2) {
        // intercambio
        Util.swap(individuo.getCromosoma(), pos1, pos2);
    }

}

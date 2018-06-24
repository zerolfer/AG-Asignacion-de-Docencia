package main.java.genetico.operadores.mutacion;

import main.java.genetico.Individuo;
import main.java.util.Util;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public class MutacionInversion extends AbstractMutacion {

    public MutacionInversion(final float probabilidad) {
        super(probabilidad);
    }

    @Override
    protected void estrategiaDeMutacion(Individuo individuo, int pos1, int pos2) {
        if (pos2 < pos1) {
            int aux = pos2;
            pos2 = pos1;
            pos1 = aux;
        }
        while (pos2 > pos1)
            Util.swap(individuo.getCromosoma(), pos1++, pos2--);
    }


}

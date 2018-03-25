package main.java.genetico.algoritmos.cruce;

import main.java.genetico.Individuo;

import java.util.List;

/**
 * Created by Sergio Florez on 25/03/2018.
 */
public class CrucePositionBased extends AbstractCruce {


    public CrucePositionBased(float probabilidad) {
        super(probabilidad);
    }

    public Individuo[] aplicar(Individuo padre1, Individuo padre2, int desde, int hasta) {
        return new Individuo[0];
    }

    @Override
    public Individuo[] aplicar(Individuo padre1, Individuo padre2) {
        return new Individuo[0];
    }
}

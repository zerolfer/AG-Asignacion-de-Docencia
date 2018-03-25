package main.java.genetico.algoritmos.cruce;

import main.java.genetico.Individuo;
import main.java.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Florez on 25/03/2018.
 */
public abstract class AbstractCruce implements AlgoritmoCruce {

    protected float probabilidad = 1f;
    protected static final boolean debug = false;

    public AbstractCruce(float probabilidad){
        this.probabilidad=probabilidad;
    }

    @Override
    public Individuo[] aplicar(Individuo padre1, Individuo padre2) {
        int length = padre1.size();
        int desde = Util.getRandomNumber(length);
        int hasta = Util.getRandomNumber(desde, length);

        return aplicar(padre1, padre2, desde, hasta);
    }

    @Override
    public Individuo[] aplicar(Individuo[] padres) {
        return aplicar(padres[0], padres[1]);
    }



    @Override
    public List<Individuo[]> aplicar(List<Individuo[]> pares) {
        List<Individuo[]> result = new ArrayList<>();
        for (Individuo[] par : pares) {
            assert par.length == 2;
            result.add(aplicar(par[0], par[1]));
        }
        return result;
    }
}

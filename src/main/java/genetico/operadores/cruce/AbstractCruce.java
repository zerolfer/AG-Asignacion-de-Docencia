package main.java.genetico.operadores.cruce;

import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.Individuo;
import main.java.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Florez on 25/03/2018.
 */
public abstract class AbstractCruce implements OperadorCruce {

    protected static final boolean debug = false;
    protected float probabilidad;

    public AbstractCruce() {
        //probabilidad por defecto
        this.probabilidad=AlgoritmoGenetico.PROBABILIDAD_CRUCE;
    }

    public AbstractCruce(float probabilidad) {
        Util.checkProbabilidadValida(probabilidad);
        this.probabilidad = probabilidad;
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
            Individuo[] hijos = aplicar(par[0], par[1]);
            hijos[0].setYaEvaluado(false);
            hijos[1].setYaEvaluado(false);
            result.add(hijos);
        }
        return result;
    }

    @Override
    public Float getProbabilidad() {
        return this.probabilidad;
    }

}

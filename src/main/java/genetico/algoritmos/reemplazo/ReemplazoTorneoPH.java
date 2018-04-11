package main.java.genetico.algoritmos.reemplazo;

import main.java.genetico.Generacion;
import main.java.genetico.Individuo;
import main.java.util.Util;

import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public class ReemplazoTorneoPH implements AlgoritmoReemplazo {

    @Override
    public Generacion aplicar(List<Individuo[]> padres, List<Individuo[]> hijos) {
        Individuo[] resultados = new Individuo[padres.size()];
        assert padres.size() == hijos.size();
        for (int i = 0; i < padres.size(); i++) {
            resultados[i] = torneo(agrupar(padres.get(i), hijos.get(i)));
        }
        return new Generacion(resultados);
    }

    private Individuo[] agrupar(Individuo[] padres, Individuo[] hijos) {
        Individuo[] result = new Individuo[padres.length + hijos.length];
        for (int i = 0; i < padres.length + hijos.length; i++) {
            result[i] = padres[i];
            result[i + padres.length] = hijos[i];
        }
        return result;
    }

    private Individuo torneo(Individuo[] individuos) {
        Individuo elMejor = individuos[Util.getRandomNumber(individuos.length)];
        for (int i = 1; i < individuos.length; i++) {
            Individuo candidato = individuos[Util.getRandomNumber(individuos.length)];
            if (candidato.compareTo(elMejor) > 0) // si es mejor el candidato
                elMejor = candidato;
        }
        return elMejor;

    }
}

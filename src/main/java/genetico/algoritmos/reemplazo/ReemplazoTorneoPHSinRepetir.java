package main.java.genetico.algoritmos.reemplazo;

import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.Generacion;
import main.java.genetico.Individuo;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public class ReemplazoTorneoPHSinRepetir implements AlgoritmoReemplazo {

    @Override
    public Generacion aplicar(List<Individuo[]> padres, List<Individuo[]> hijos) {
        Individuo[] resultados = new Individuo[AlgoritmoGenetico.populationSize];
        assert padres.size() == hijos.size();
        for (int i = 0, j = 0; i < resultados.length && j < padres.size(); i += 2, j++) {
            Individuo[] ordenados = agrupar(padres.get(j), hijos.get(j));
            resultados[i] = ordenados[ordenados.length - 1];
            resultados[i + 1] = buscarNoRepetido(resultados[i], ordenados);
        }
        return new Generacion(resultados);
    }

    private Individuo buscarNoRepetido(Individuo resultado, Individuo[] ordenados) {
        for (int i = ordenados.length-2; i >=0 ; i--) {
            if(!ordenados[i].equals(resultado))
                return ordenados[i];
        }
        return ordenados[0]; // todos son iguales asi que uno cualquiera
    }

    private Individuo[] agrupar(Individuo[] padres, Individuo[] hijos) {
        Individuo[] result = new Individuo[padres.length + hijos.length];
        for (int i = 0; i < padres.length; i++) {
            assert padres.length == hijos.length;
            result[i] = padres[i];
            result[i + padres.length] = hijos[i];
        }
        Arrays.sort(result);
        return result;
    }
/*
    private Individuo[] torneo(Individuo[] individuos) {
        Individuo elMejor = individuos[Util.getRandomNumber(individuos.length)];
        return ;
        for (int i = 1; i < individuos.length; i++) {
            Individuo candidato = individuos[Util.getRandomNumber(individuos.length)];
            if (candidato.compareTo(elMejor) > 0) // si es mejor el candidato
                elMejor = candidato;
        }
        return elMejor;

    }*/
}

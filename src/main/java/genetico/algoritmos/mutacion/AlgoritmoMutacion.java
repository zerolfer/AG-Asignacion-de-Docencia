package main.java.genetico.algoritmos.mutacion;

import main.java.genetico.Individuo;

import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public interface AlgoritmoMutacion {
    void mutar(List<Individuo[]> individuo);
}

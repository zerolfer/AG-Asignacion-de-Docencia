package main.java.algoritmos;

import main.java.Individuo;

/**
 * Created by Sergio Florez on 09/03/2018.
 */
public interface AlgoritmoCreacion {
    Individuo[] createPopulation(final int populationSize);
    Individuo generateIndividual();
}

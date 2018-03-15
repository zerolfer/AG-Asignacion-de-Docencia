package main.java.genetico.algoritmos.creacion;

import main.java.genetico.Generacion;
import main.java.genetico.Individuo;

/**
 * Created by Sergio Florez on 09/03/2018.
 */
public interface AlgoritmoCreacion {
    Generacion createPopulation(final int populationSize);
    Individuo generateIndividual();
}

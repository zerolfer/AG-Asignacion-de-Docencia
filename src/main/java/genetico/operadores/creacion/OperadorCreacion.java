package main.java.genetico.operadores.creacion;

import main.java.genetico.Generacion;
import main.java.genetico.Individuo;

/**
 * Created by Sergio Florez on 09/03/2018.
 */
public interface OperadorCreacion {
    Generacion createPopulation(final int populationSize);

    Individuo generateIndividual();
}

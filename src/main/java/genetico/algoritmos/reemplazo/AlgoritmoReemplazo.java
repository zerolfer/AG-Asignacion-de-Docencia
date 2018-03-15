package main.java.genetico.algoritmos.reemplazo;

import main.java.genetico.Individuo;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public interface AlgoritmoReemplazo {
    Individuo[] aplicar(Individuo[] generacion);
}

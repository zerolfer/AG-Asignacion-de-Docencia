package main.java.genetico.algoritmos.seleccion;

import main.java.genetico.Generacion;
import main.java.genetico.Individuo;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public interface AlgoritmoSeleccion {
    Individuo[] aplicar(Generacion generacion);
}

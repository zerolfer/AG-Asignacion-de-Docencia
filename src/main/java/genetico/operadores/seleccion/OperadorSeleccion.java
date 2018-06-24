package main.java.genetico.operadores.seleccion;

import main.java.genetico.Generacion;
import main.java.genetico.Individuo;

import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public interface OperadorSeleccion {
    List<Individuo[]> aplicar(Generacion generacion);
}

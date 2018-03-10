package main.java.algoritmos;

import main.java.Individuo;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;

import java.util.List;

/**
 * Created by Sergio Florez on 09/03/2018.
 */
public interface AlgoritmoDecodificacion {
    void aplicar(Individuo individuo, Profesor[] profesores, GrupoAsignatura[] asignaturas);

}

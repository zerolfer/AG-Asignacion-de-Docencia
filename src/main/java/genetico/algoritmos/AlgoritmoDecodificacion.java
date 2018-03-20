package main.java.genetico.algoritmos;

import main.java.genetico.Individuo;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;

import java.util.List;

/**
 * Created by Sergio Florez on 09/03/2018.
 */
public interface AlgoritmoDecodificacion {
    void aplicar(Individuo individuo, List<Profesor> profesores, List<GrupoAsignatura> asignaturas);

}

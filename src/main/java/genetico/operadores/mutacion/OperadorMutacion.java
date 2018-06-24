package main.java.genetico.operadores.mutacion;

import main.java.genetico.Individuo;

import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public interface OperadorMutacion {
    void mutar(List<Individuo[]> individuo);

    void mutar(Individuo individuo);

    Float getProbabilidad();
}

package main.java.genetico.algoritmos.mutacion;

import main.java.genetico.Individuo;

import javax.naming.NamingException;
import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public interface AlgoritmoMutacion {
    void mutar(List<Individuo[]> individuo);

    void mutar(Individuo individuo);

    Float getProbabilidad();
}

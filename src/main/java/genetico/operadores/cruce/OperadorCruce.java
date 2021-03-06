package main.java.genetico.operadores.cruce;

import main.java.genetico.Individuo;

import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public interface OperadorCruce {
    Individuo[] aplicar(Individuo padre1, Individuo padre2);

    Individuo[] aplicar(Individuo[] padres);
//    Individuo[] aplicar(Individuo padre1, Individuo padre2,int desde, int hasta);

    List<Individuo[]> aplicar(List<Individuo[]> pares);

    Float getProbabilidad();
}

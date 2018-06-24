package main.java.genetico.operadores.reemplazo;

import main.java.genetico.Generacion;
import main.java.genetico.Individuo;

import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public interface OperadorReemplazo {
    Generacion aplicar(List<Individuo[]> padres, List<Individuo[]> hijos);
}

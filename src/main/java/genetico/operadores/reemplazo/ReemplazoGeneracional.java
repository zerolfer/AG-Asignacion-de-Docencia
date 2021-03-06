package main.java.genetico.operadores.reemplazo;

import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.Generacion;
import main.java.genetico.Individuo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public class ReemplazoGeneracional implements OperadorReemplazo {
    @Override
    public Generacion aplicar(List<Individuo[]> padres, List<Individuo[]> hijos) {
        List<Individuo> generacion = new ArrayList<>();
        for (Individuo[] par : hijos)
            for (Individuo hijo : par)
                if (generacion.size() < AlgoritmoGenetico.getPopulationSize())
                    generacion.add(hijo);
        return new Generacion(generacion.toArray(new Individuo[generacion.size()]));
    }
}

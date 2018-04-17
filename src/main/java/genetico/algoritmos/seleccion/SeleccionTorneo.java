package main.java.genetico.algoritmos.seleccion;

import main.java.genetico.Generacion;
import main.java.genetico.Individuo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public class SeleccionTorneo implements AlgoritmoSeleccion {

    private int torneoSize;

    public SeleccionTorneo(int torneoSize) {
        this.torneoSize = torneoSize;
    }

    @Override
    public List<Individuo[]> aplicar(Generacion generacion) {
        List<Individuo[]> result = new ArrayList<>();
        for (int i = 0; i < generacion.size() / 2; i++) {
            Individuo individuo1 = torneo(generacion);
            Individuo individuo2 = torneo(generacion);
            result.add(new Individuo[]{individuo1, individuo2});
        }
        return result;
    }

    private Individuo torneo(Generacion generacion) {
        Individuo elMejor = generacion.getRandomIndividual();
        for (int i = 1; i < torneoSize; i++) {
            Individuo candidato = generacion.getRandomIndividual();
            if (candidato.compareTo(elMejor) > 0) // si es mejor el candidato
                elMejor = candidato;
        }
        return elMejor;
    }
}

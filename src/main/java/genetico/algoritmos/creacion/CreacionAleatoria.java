package main.java.genetico.algoritmos.creacion;

import main.java.genetico.Generacion;
import main.java.genetico.Individuo;
import main.java.model.GrupoAsignatura;
import main.java.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Florez on 09/03/2018.
 */
public class CreacionAleatoria implements AlgoritmoCreacion {

    @Override
    public Individuo generateIndividual() {
        int ultimo = GrupoAsignatura.getUltimoId();
        int[] elementos = inicializar(ultimo);
        if (ultimo == GrupoAsignatura.INICIO) throw new RuntimeException("No hay asignaturas");

        // permutación de elementos == elementos no repetidos
        for (int i = 0; i < ultimo; i++) {
            int n = Util.getRandomNumber(ultimo);
            int anterior = elementos[i];
            elementos[i] = elementos[n];
            elementos[n] = anterior;
        }
//        assert ! new Individuo(elementos).checkHayRepetidos();
        return new Individuo(elementos);
    }

    private int[] inicializar(int ultimo) {
        int[] elementos = new int[ultimo];
        for (int i = 0; i < ultimo; i++) {
            elementos[i] = i;
        }
        return elementos;
    }

    @Override
    public Generacion createPopulation(final int size) {
        List<Individuo> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(generateIndividual());
        }
        return new Generacion(result.toArray(new Individuo[size]));
    }
}

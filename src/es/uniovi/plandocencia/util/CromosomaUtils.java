package es.uniovi.plandocencia.util;

import es.uniovi.plandocencia.Individuo;
import es.uniovi.plandocencia.model.GrupoAsignatura;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Florez on 06/03/2018.
 */
public class CromosomaUtils {

    public static Individuo generateRandomIndividual() {
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
        return new Individuo(elementos);
    }

    private static int[] inicializar(int ultimo) {
        int[] elementos = new int[ultimo];
        for (int i = 0; i < ultimo; i++) {
            elementos[i] = i;
        }
        return elementos;
    }

    public static Individuo[] generateRandomPopulation(int size) {
        List<Individuo> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(generateRandomIndividual());
        }
        return result.toArray(new Individuo[size]);
    }
}

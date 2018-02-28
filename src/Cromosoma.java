import model.GrupoAsignatura;
import util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergio Florez on 27/02/2018.
 */
public class Cromosoma {

    private int[] elementos; // cada elemento representa el id de una asignatura

    public Cromosoma(int[] elementos) {
        this.elementos = elementos;
    }

    public int[] getElementos() {
        return elementos;
    }

    public void setElementos(int[] elementos) {
        this.elementos = elementos;
    }

    public static Cromosoma generateRandomIndividual() {
        int ultimo = GrupoAsignatura.getUltimoId();
        int[] elementos = inicializar(ultimo);
        if (ultimo == GrupoAsignatura.INICIO) throw new RuntimeException("No hay asignaturas");

        // permutación de elementos == elementos no repetidos
        for (int i = 0; i < ultimo; i++) {
            int n = Util.getRandomNumber(ultimo);
            int anterior = elementos[i]; // TODO: esto está mal
            elementos[i] = elementos[n];
            elementos[n] = anterior;
        }
        return new Cromosoma(elementos);
    }

    private static int[] inicializar(int ultimo) {
        int[] elementos = new int[ultimo];
        for (int i = 0; i < ultimo; i++) {
            elementos[i] = i;
        }
        return elementos;
    }

    public static Cromosoma[] generateRandomPopulation(int size) {
        List<Cromosoma> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(generateRandomIndividual());
        }
        return result.toArray(new Cromosoma[size]);
    }

    @Override
    public String toString() {
        return Arrays.toString(elementos);
    }
}

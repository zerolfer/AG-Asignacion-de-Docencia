package main.java.genetico.algoritmos.seleccion;

import main.java.genetico.Generacion;
import main.java.genetico.Individuo;
import main.java.util.RandomManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public class SeleccionRuleta implements AlgoritmoSeleccion {
    @Override
    public List<Individuo[]> aplicar(Generacion generacion) {
        List<Individuo[]> result = new ArrayList<>();
        Arrays.sort(generacion.getGenotipo());  // ordenamos Individios por su fitness
        float[] probabilidades = calcularProbabilidades(generacion);
        for (int i = 0; i < generacion.size() / 2; i++) {
            Individuo individuo1 = ruleta(generacion, probabilidades);
            Individuo individuo2 = ruleta(generacion, probabilidades);
            result.add(new Individuo[]{individuo1, individuo2});
        }
        return result;
    }

    private Individuo ruleta(Generacion generacion, float[] probabilidades) {
        float acumulado = 0;
        float random = RandomManager.getInstance().getRandomProbability();
        for (int i = 0; i < generacion.size(); i++) {
            acumulado += probabilidades[i];
            if (acumulado >= random)
                return generacion.getIndividuo(i);
        }
        throw new RuntimeException("No se ha seleccionado un individuo por ruleta");
    }

    private float[] calcularProbabilidades(Generacion generacion) {
        float[] result = new float[generacion.size()];
        int n = 1;
        for (int i = 0; i < generacion.size(); i++, n++)
            result[i] = n;
        float N = (n * (n - 1)) / 2; // n vale 101, que sería "n+1" en N=n*(n+1)/2
        for (int i = 0; i < generacion.size(); i++)
            result[i] /= N;
        return result;
    }
}

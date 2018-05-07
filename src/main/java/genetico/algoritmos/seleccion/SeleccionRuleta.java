package main.java.genetico.algoritmos.seleccion;

import main.java.genetico.Generacion;
import main.java.genetico.Individuo;
import main.java.util.RandomManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public class SeleccionRuleta implements AlgoritmoSeleccion {
    @Override
    public List<Individuo[]> aplicar(Generacion generacion) {
        List<Individuo[]> result = new ArrayList<>();
        float[] probabilidades = calcularProbabilidades(generacion.getGenotipo().clone());
        for (int i = 0; i < generacion.size() / 2; i++) {
            Individuo individuo1 = ruleta(generacion, probabilidades);
            Individuo individuo2 = ruleta(generacion, probabilidades);
            result.add(new Individuo[]{individuo1, individuo2});
        }
        return result;
    }

    private Individuo ruleta(Generacion generacion, float[] total) {
        float acumulado = 0;
        for (int i = 0; i < generacion.size(); i++) {
            float random = RandomManager.getInstance().getFloatRandomNumber(0, total[i]);
            acumulado += generacion.getIndividuo(i).getFitnessAsigProfesor();
            if (acumulado >= random)
                return generacion.getIndividuo(i);
        }
        throw new RuntimeException("No se ha seleccionado un individuo por ruleta");
    }

    private float[] calcularProbabilidades(Individuo[] generacion) {
        float[] result = new float[generacion.length];
        int n = 1;
        for (int i = generacion.length - 1; i >= 0; i--, n++)
            result[i] = n;
        float N = (n * (n + 1)) / 2;
        for (int i = 0; i < generacion.length; i++)
            result[i] /= N;
        return result;
    }
}

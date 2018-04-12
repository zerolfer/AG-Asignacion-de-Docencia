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
        float total = sumaTotal(generacion);
        for (int i = 0; i < generacion.size() / 2 + 1; i++) {
            Individuo individuo1 = seleccionar(generacion, total);
            Individuo individuo2 = seleccionar(generacion, total);
            result.add(new Individuo[]{individuo1, individuo2});
        }
        return result;
    }

    private Individuo seleccionar(Generacion generacion, float total) {
        float random = RandomManager.getInstance().getFloatRandomNumber(0, total);
        float acumulado = 0;
        for (Individuo individuo : generacion.getGenotipo()) {
            acumulado += individuo.getFitnessAsigProfesor();
            if (acumulado >= random) {
                return individuo;
            }
        }
        return null;
    }

    private float sumaTotal(Generacion generacion) {
        float total = 0;
        for (Individuo individuo : generacion.getGenotipo()) {
            total += individuo.getFitnessAsigProfesor();
        }
        return total;
    }
}

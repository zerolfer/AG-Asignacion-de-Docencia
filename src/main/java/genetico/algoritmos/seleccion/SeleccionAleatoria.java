package main.java.genetico.algoritmos.seleccion;

import main.java.genetico.Generacion;
import main.java.genetico.Individuo;
import main.java.util.RandomManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public class SeleccionAleatoria implements AlgoritmoSeleccion {
    @Override
    public List<Individuo[]> aplicar(Generacion generacion) {
        List<Individuo[]> resultado = new ArrayList<>();
        List<Integer> yaAsignados = new ArrayList<>();
        for (int i = 0; i < generacion.size() / 2; i++) {
            int random1 = RandomManager.getInstance().getRandomNumber(generacion.size());
            int random2;
            Individuo gen1 = generacion.getIndividuo(random1);
            Individuo gen2;
            yaAsignados.add(random1);
            do {
                random2 = RandomManager.getInstance().getRandomNumber(generacion.size());
                gen2 = generacion.getIndividuo(random2);
            } while (!yaAsignados.contains(random2));
            yaAsignados.add(random2);
            resultado.add(new Individuo[]{gen1, gen2});
        }
        return resultado;
    }
}

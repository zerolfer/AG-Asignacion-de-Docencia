package main.java.genetico.operadores.seleccion;

import main.java.genetico.Generacion;
import main.java.genetico.Individuo;
import main.java.util.RandomManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public class SeleccionParesAleatorios implements OperadorSeleccion {
    @Override
    public List<Individuo[]> aplicar(Generacion generacion) {
        List<Individuo[]> resultado = new ArrayList<>();
        List<Integer> yaAsignados = new ArrayList<>();
        for (int i = 0; i < generacion.size() / 2; i++) {
            int random1;
            int random2;
            Individuo gen1;
            Individuo gen2;
            do {
                random1 = RandomManager.getInstance().getRandomNumber(generacion.size());
                gen1 = generacion.getIndividuo(random1);
            } while (yaAsignados.contains(random1));
            yaAsignados.add(random1);
            do {
                random2 = RandomManager.getInstance().getRandomNumber(generacion.size());
                gen2 = generacion.getIndividuo(random2);
            } while (yaAsignados.contains(random2));
            yaAsignados.add(random2);
            resultado.add(new Individuo[]{gen1, gen2});

        }
        return resultado;
    }
}

package main.java.genetico.algoritmos.seleccion;

import main.java.genetico.Generacion;
import main.java.genetico.Individuo;
import main.java.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public class SeleccionAleatoria implements AlgoritmoSeleccion {
    @Override
    public List<Individuo[]> aplicar(Generacion generacion) {
        List<Individuo[]> resultado = new ArrayList<>();

        for (int i = 0; i < generacion.size() / 2 + 1; i++) {

            Individuo gen1 = generacion.getIndividuo(Util.getRandomNumber(generacion.size()));
            Individuo gen2;
            do {
                gen2 = generacion.getIndividuo(Util.getRandomNumber(generacion.size()));
            } while (gen2.equals(gen1));
            resultado.add(new Individuo[]{gen1, gen2});
        }
        return resultado;
    }
}

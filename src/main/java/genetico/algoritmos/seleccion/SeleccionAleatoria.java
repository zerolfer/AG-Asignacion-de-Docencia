package main.java.genetico.algoritmos.seleccion;

import main.java.genetico.Generacion;
import main.java.genetico.Individuo;
import main.java.util.Util;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public class SeleccionAleatoria implements AlgoritmoSeleccion {
    @Override
    public Individuo[] aplicar(Generacion generacion) {
        Individuo gen1 = generacion.getIndividuo(Util.getRandomNumber(generacion.size()));
        Individuo gen2;
        do {
            gen2 = generacion.getIndividuo(Util.getRandomNumber(generacion.size()));
        } while (gen2.equals(gen1));
        return new Individuo[]{gen1,gen2};
    }
}

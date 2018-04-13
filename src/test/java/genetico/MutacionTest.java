package test.java.genetico;

import main.java.genetico.Individuo;
import main.java.genetico.algoritmos.mutacion.AlgoritmoMutacion;
import main.java.genetico.algoritmos.mutacion.MutacionIntercambio;
import main.java.util.Util;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Sergio Florez on 23/03/2018.
 */
public class MutacionTest {

    private AlgoritmoMutacion mutador;

    @Test
    public void intercambioTest() {
        mutador = new MutacionIntercambio(1);

        Individuo individuo = Util.createIndividual(1, 2, 3, 4);
        System.out.println("INTERCAMBIO - ALGORITMO DE MUTACION - TEST 1 - RESULTADOS:");
        System.out.println("Inicial: " + individuo);

        Map<Individuo, Integer> posibilidades = new HashMap<>();
        posibilidades.put(Util.createIndividual(1, 4, 3, 2), 0);
        posibilidades.put(Util.createIndividual(3, 4, 1, 2), 0);
        posibilidades.put(Util.createIndividual(3, 1, 4, 2), 0);
        posibilidades.put(Util.createIndividual(2, 1, 4, 3), 0);
        posibilidades.put(Util.createIndividual(2, 3, 4, 1), 0);
        posibilidades.put(Util.createIndividual(3, 2, 4, 1), 0);
        posibilidades.put(Util.createIndividual(2, 4, 1, 3), 0);
        posibilidades.put(Util.createIndividual(2, 4, 3, 1), 0);
        posibilidades.put(Util.createIndividual(1, 2, 3, 4), 0);
        posibilidades.put(Util.createIndividual(4, 1, 3, 2), 0);
        posibilidades.put(Util.createIndividual(1, 3, 4, 2), 0);
        posibilidades.put(Util.createIndividual(2, 1, 3, 4), 0);
        posibilidades.put(Util.createIndividual(3, 4, 2, 1), 0);
        posibilidades.put(Util.createIndividual(3, 2, 1, 4), 0);
        posibilidades.put(Util.createIndividual(3, 1, 2, 4), 0);
        posibilidades.put(Util.createIndividual(4, 1, 2, 3), 0);
        posibilidades.put(Util.createIndividual(4, 2, 3, 1), 0);
        posibilidades.put(Util.createIndividual(1, 3, 2, 4), 0);
        posibilidades.put(Util.createIndividual(1, 2, 4, 3), 0);
        posibilidades.put(Util.createIndividual(4, 3, 1, 2), 0);
        posibilidades.put(Util.createIndividual(4, 3, 2, 1), 0);
        posibilidades.put(Util.createIndividual(4, 2, 1, 3), 0);
        posibilidades.put(Util.createIndividual(2, 3, 1, 4), 0);
        posibilidades.put(Util.createIndividual(1, 4, 2, 3), 0);

        assertFalse(individuo.checkHayRepetidos());

        for (int i = 0; i < 1000; i++) {
            mutador.mutar(individuo);
            assertFalse(individuo.checkHayRepetidos());
//            System.out.println("Final:  " + individuo);
            assertTrue(posibilidades.containsKey(individuo));
            posibilidades.replace(individuo, posibilidades.get(individuo) + 1);
//            if (!posibilidades.contains(individuo))
//                posibilidades.add(individuo.clone());
        }
        for (Individuo ind : posibilidades.keySet()) {
            int veces = posibilidades.get(ind);
            System.out.println(ind + " " + veces + " veces");
            assertTrue(veces > 0);
        }
    }

}

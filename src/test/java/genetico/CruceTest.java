package test.java.genetico;

import main.java.CSVReader;
import main.java.genetico.Individuo;
import main.java.genetico.algoritmos.cruce.AlgoritmoCruce;
import main.java.genetico.algoritmos.cruce.CruceOrderBased;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;
import main.java.util.Util;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Sergio Florez on 23/03/2018.
 */
public class CruceTest {
    private AlgoritmoCruce crossover = new CruceOrderBased(1);

    @Test
    public void orderBasedCrossover1() {
        Individuo padre1 = Util.createIndividual(1, 2, 3, 4);
        Individuo padre2 = Util.createIndividual(4, 3, 2, 1);
        Individuo[] hijosEsperados = new Individuo[]{
                Util.createIndividual(2, 1, 3, 4),
                Util.createIndividual(3, 4, 2, 1)
        };

        Individuo[] hijos = crossover.aplicar(padre1, padre2, 2, 3);
        assertArrayEquals(hijosEsperados, hijos);

        System.out.println("ORDER-BASED CROSSOVER - ALGORITMO DE CRUCE - TEST 1 - RESULTADOS:");
        for (Individuo hijo : hijos) {
            System.out.println(hijo);
        }
    }

    @Test
    public void orderBasedCrossover2() {
        Individuo padre1 = Util.createIndividual(2, 4, 1, 5, 7, 6, 0, 3);
        Individuo padre2 = Util.createIndividual(4, 1, 6, 2, 3, 5, 7, 0);
        Individuo[] hijosEsperados = new Individuo[]{
                Util.createIndividual(4, 6, 1, 5, 7, 2, 3, 0),
                Util.createIndividual(4, 1, 6, 2, 3, 5, 7, 0)
        };

        Individuo[] hijos = crossover.aplicar(padre1, padre2, 2, 4);
        assertArrayEquals(hijosEsperados, hijos);

        System.out.println("ORDER-BASED CROSSOVER - ALGORITMO DE CRUCE - TEST 2 - RESULTADOS:");
        for (Individuo hijo : hijos) {
            System.out.println(hijo);
        }
    }

    @Test
    public void randomTest() {
        for (int j = 0; j < 30; j++) {
            int c = 0, d = 0;
            for (int i = 0; i < 10000; i++) {
                float ran = (float) Math.random();
                float rand = Util.getRandomNumberInclusive(100);
                rand = rand / 100;
                if (ran <= 0.75)
                    d++;
//            System.out.print(ran);
                if (rand <= 0.75) {
//                    System.out.print("\t\t\t\t\t\t" + rand);
//                    System.out.println();
                    c++;
                }
            }
            System.out.println("contador c: " + c);
            System.out.println("contador d: " + d);

            Assert.assertEquals(7500, c, 130);
            Assert.assertEquals(7500, d, 130);
        }

    }

    @Test
    public void orderBasedCrossover3() {

        crossover = new CruceOrderBased(1);
        Individuo padre1 = Util.createIndividual(3, 1, 4, 0, 8, 7, 9, 6, 5, 2);
        Individuo padre2 = Util.createIndividual(3, 8, 1, 2, 0, 6, 5, 7, 9, 4);
        Individuo[] hijosEsperados = new Individuo[]{
                Util.createIndividual(3, 8, 1, 2, 0, 7, 9, 6, 5, 4),
                Util.createIndividual(3, 1, 4, 0, 8, 6, 5, 7, 9, 2)};

        Individuo[] hijos = crossover.aplicar(padre1, padre2, 6, 8);
        assertArrayEquals(hijosEsperados, hijos);

        System.out.println("ORDER-BASED CROSSOVER - ALGORITMO DE CRUCE - TEST 2 - RESULTADOS:");

        System.out.println("Padre 1: " + padre1);
        System.out.println("Padre 2: " + padre2);

        for (Individuo hijo : hijos) {
            System.out.println("Hijo: \t " + hijo);
            assertFalse(hijo.checkHayRepetidos());
        }
    }

    @Test
    public void orderBasedCrossover4() {


        crossover = new CruceOrderBased(1);
        Individuo padre1 = Util.createIndividual(58, 33, 16, 12, 61, 21, 75, 6, 17, 20, 25, 65, 42, 40, 78, 74, 26, 64, 15, 35, 66, 27, 22, 4, 7, 80, 30, 10, 3, 48, 50, 62, 18, 67, 9, 11, 46, 81, 63, 5, 38, 72, 24, 31, 52, 79, 53, 68, 0, 82, 56, 8, 41, 32, 77, 59, 39, 23, 45, 50, 2, 11, 0, 9, 47, 49, 19, 52, 1, 24, 55, 76, 43, 57, 73, 54, 44, 13, 29, 60, 37, 28, 70);

        Individuo padre2 = Util.createIndividual(58, 33, 16, 12, 14, 25, 78, 21, 15, 20, 1, 51, 6, 61, 3, 18, 67, 11, 46, 38, 72, 24, 52, 53, 82, 56, 8, 77, 35, 66, 31, 50, 27, 22, 4, 5, 7, 80, 30, 81, 63, 10, 32, 55, 76, 45, 43, 9, 48, 23, 68, 39, 57, 19, 73, 54, 44, 13, 41, 29, 60, 37, 0, 47, 28, 70, 34, 62, 79, 36, 59, 2, 49, 46, 0, 47, 29, 60, 24, 61, 65, 6, 37);

        Individuo[] hijosEsperados = new Individuo[]{
                Util.createIndividual(58, 33, 16, 12, 14, 25, 78, 21, 15, 20, 51, 6, 61, 35, 66, 27, 22, 4, 7, 80, 30, 10, 55, 76, 43, 57, 73, 54, 3, 48, 50, 62, 18, 67, 9, 11, 46, 81, 63, 5, 38, 72, 24, 31, 52, 79, 53, 68, 0, 82, 56, 8, 41, 32, 77, 59, 39, 23, 45, 50, 2, 11, 0, 9, 47, 49, 19, 52, 1, 24, 44, 13, 29, 60, 37, 28, 70, 34, 36, 65, 37, 28, 70),
                Util.createIndividual(58, 33, 16, 12, 14, 25, 78, 21, 15, 20, 65, 42, 40, 74, 26, 64, 3, 18, 67, 11, 46, 38, 72, 24, 52, 53, 82, 56, 35, 66, 31, 50, 27, 22, 4, 5, 7, 80, 30, 81, 63, 10, 32, 55, 76, 45, 43, 9, 48, 23, 68, 39, 57, 19, 73, 54, 44, 13, 41, 29, 60, 37, 0, 47, 28, 70, 34, 62, 79, 36, 8, 77, 59, 2, 49, 1, 29, 60, 24, 61, 65, 6, 37)
        };

        Individuo[] hijos = crossover.aplicar(padre1, padre2, 28, 69);
        assertArrayEquals(hijosEsperados, hijos);

        System.out.println("ORDER-BASED CROSSOVER - ALGORITMO DE CRUCE - TEST 2 - RESULTADOS:");

        System.out.println("Padre 1: " + padre1);
        System.out.println("Padre 2: " + padre2);

        for (Individuo hijo : hijos) {
            System.out.println("Hijo: " + hijo);
            assertFalse(hijo.checkHayRepetidos());
        }


    }
}

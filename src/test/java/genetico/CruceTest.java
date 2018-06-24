package test.java.genetico;

import main.java.genetico.Individuo;
import main.java.genetico.operadores.cruce.CruceOrderBased;
import main.java.genetico.operadores.cruce.CrucePositionBased;
import main.java.util.RandomManager;
import main.java.util.Util;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Sergio Florez on 23/03/2018.
 */
public class CruceTest {
    private CruceOrderBased orderCrossover = new CruceOrderBased(1);
    private CrucePositionBased positionCrossover = new CrucePositionBased(1);

    @Test
    public void orderBasedCrossover() {
        orderBasedCrossover1();
        orderBasedCrossover2();
        orderBasedCrossover3();
        orderBasedCrossover4();
        orderBasedCrossover5();

    }

    public void orderBasedCrossover1() {
        Individuo padre1 = Util.createIndividual(1, 2, 3, 4);
        Individuo padre2 = Util.createIndividual(4, 3, 2, 1);
        Individuo[] hijosEsperados = new Individuo[]{
                Util.createIndividual(2, 1, 3, 4),
                Util.createIndividual(3, 4, 2, 1)
        };

        Individuo[] hijos = orderCrossover.aplicar(padre1, padre2, 2, 3);
        assertArrayEquals(hijosEsperados, hijos);

        System.out.println("ORDER-BASED CROSSOVER - ALGORITMO DE CRUCE - TEST 1 - RESULTADOS:");
        for (Individuo hijo : hijos) {
            System.out.println(hijo);
        }
    }

    public void orderBasedCrossover2() {
        Individuo padre1 = Util.createIndividual(2, 4, 1, 5, 7, 6, 0, 3);
        Individuo padre2 = Util.createIndividual(4, 1, 6, 2, 3, 5, 7, 0);
        Individuo[] hijosEsperados = new Individuo[]{
                Util.createIndividual(4, 6, 1, 5, 7, 2, 3, 0),
                Util.createIndividual(4, 1, 6, 2, 3, 5, 7, 0)
        };

        Individuo[] hijos = orderCrossover.aplicar(padre1, padre2, 2, 4);
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
                float ran = RandomManager.getInstance().getRandomProbability();
                float rand = RandomManager.getInstance().getRandomNumberInclusive(100);
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

            Assert.assertEquals(7500, c, 200);
            Assert.assertEquals(7500, d, 200);
        }

    }

    public void orderBasedCrossover3() {

        orderCrossover = new CruceOrderBased(1);
        Individuo padre1 = Util.createIndividual(3, 1, 4, 0, 8, 7, 9, 6, 5, 2);
        Individuo padre2 = Util.createIndividual(3, 8, 1, 2, 0, 6, 5, 7, 9, 4);
        Individuo[] hijosEsperados = new Individuo[]{
                Util.createIndividual(3, 8, 1, 2, 0, 7, 9, 6, 5, 4),
                Util.createIndividual(3, 1, 4, 0, 8, 6, 5, 7, 9, 2)};

        Individuo[] hijos = orderCrossover.aplicar(padre1, padre2, 6, 8);
        assertArrayEquals(hijosEsperados, hijos);

        System.out.println("ORDER-BASED CROSSOVER - ALGORITMO DE CRUCE - TEST 2 - RESULTADOS:");

        System.out.println("Padre 1: " + padre1);
        System.out.println("Padre 2: " + padre2);

        for (Individuo hijo : hijos) {
            System.out.println("Hijo: \t " + hijo);
            assertFalse(hijo.checkHayRepetidos());
        }
    }


    public void orderBasedCrossover4() {

        orderCrossover = new CruceOrderBased(1);
        Individuo padre1 = Util.createIndividual(3, 11, 1, 4, 0, 10, 8, 7, 9, 6, 5, 2, 12);
        Individuo padre2 = Util.createIndividual(10, 3, 8, 1, 11, 2, 0, 6, 5, 12, 7, 9, 4);
        Individuo[] hijosEsperados = new Individuo[]{
                Util.createIndividual(10, 3, 1, 11, 2, 0, 8, 7, 9, 6, 5, 12, 4),
                Util.createIndividual(3, 11, 1, 4, 10, 8, 0, 6, 5, 12, 7, 9, 2)};

        Individuo[] hijos = orderCrossover.aplicar(padre1, padre2, 6, 9);
        assertArrayEquals(hijosEsperados, hijos);

        System.out.println("ORDER-BASED CROSSOVER - ALGORITMO DE CRUCE - TEST 2 - RESULTADOS:");

        System.out.println("Padre 1: " + padre1);
        System.out.println("Padre 2: " + padre2);

        for (Individuo hijo : hijos) {
            System.out.println("Hijo: \t " + hijo);
            assertFalse(hijo.checkHayRepetidos());
        }
    }

    public void orderBasedCrossover5() {

        orderCrossover = new CruceOrderBased(1);

        Individuo padre1 = Util.createIndividual(30, 78, 4, 35, 58, 71, 60, 11, 9, 1, 36, 23, 76, 63, 66, 61, 48, 3, 8, 52, 18, 32, 26, 28, 6, 79, 64, 74, 21, 51, 43, 45, 47, 40, 37, 46, 5, 72, 27, 24, 10, 44, 56, 7, 33, 50, 54, 59, 38, 81, 20, 55, 41, 16, 14, 67, 12, 17, 19, 53, 62, 13, 0, 68, 73, 75, 34, 57, 49, 42, 22, 82, 25, 2, 65, 29, 77, 39, 70, 15, 31, 69, 80);
        Individuo padre2 = Util.createIndividual(31, 30, 20, 26, 61, 60, 68, 48, 22, 38, 54, 14, 64, 44, 5, 70, 51, 36, 71, 24, 2, 79, 6, 45, 40, 35, 59, 49, 39, 34, 80, 53, 72, 15, 55, 62, 3, 33, 81, 9, 17, 29, 74, 78, 1, 50, 82, 57, 63, 19, 42, 27, 56, 8, 66, 67, 25, 65, 43, 23, 7, 77, 13, 16, 37, 75, 73, 58, 11, 10, 52, 12, 41, 46, 47, 4, 76, 0, 21, 28, 18, 69, 32);

        assertFalse(padre1.checkHayRepetidos());
        assertFalse(padre2.checkHayRepetidos());

        Individuo[] hijosEsperados = new Individuo[]{
                Util.createIndividual(31, 30, 26, 61, 60, 48, 22, 64, 70, 36, 71, 2, 79, 6, 35, 39, 80, 15, 3, 9, 29, 74, 78, 1, 82, 63, 8, 66, 21, 51, 43, 45, 47, 40, 37, 46, 5, 72, 27, 24, 10, 44, 56, 7, 33, 50, 54, 59, 38, 81, 20, 55, 41, 16, 14, 67, 12, 17, 19, 53, 62, 13, 0, 68, 73, 75, 34, 57, 49, 42, 25, 65, 23, 77, 58, 11, 52, 4, 76, 28, 18, 69, 32),
                Util.createIndividual(30, 4, 35, 71, 60, 36, 76, 61, 48, 52, 18, 32, 26, 28, 6, 79, 64, 21, 51, 45, 47, 40, 46, 5, 24, 44, 54, 59, 39, 34, 80, 53, 72, 15, 55, 62, 3, 33, 81, 9, 17, 29, 74, 78, 1, 50, 82, 57, 63, 19, 42, 27, 56, 8, 66, 67, 25, 65, 43, 23, 7, 77, 13, 16, 37, 75, 73, 58, 11, 10, 38, 20, 41, 14, 12, 0, 68, 49, 22, 2, 70, 31, 69)
        };

        Individuo[] hijos = orderCrossover.aplicar(padre1, padre2, 28, 69);
        assertArrayEquals(hijosEsperados, hijos);

        System.out.println("ORDER-BASED CROSSOVER - ALGORITMO DE CRUCE - TEST 2 - RESULTADOS:");

        System.out.println("Padre 1: " + padre1);
        System.out.println("Padre 2: " + padre2);

        for (Individuo hijo : hijos) {
            System.out.println("Hijo: " + hijo);
            assertFalse(hijo.checkHayRepetidos());
        }


    }

//    public void positionBasedCrossover() {
//        positionBasedCrossover1();
//    }

    @Test
    public void positionBasedCrossover1() {
        Individuo padre1 = Util.createIndividual(2, 4, 1, 5, 7, 6, 0, 3);
        Individuo padre2 = Util.createIndividual(4, 1, 6, 2, 3, 5, 7, 0);
        Individuo[] hijosEsperados = new Individuo[]{
                Util.createIndividual(2, 4, 1, 6, 7, 5, 0, 3),
                Util.createIndividual(4, 1, 2, 5, 3, 7, 6, 0)
        };

        Individuo[] hijos = positionCrossover.aplicar(padre1, padre2, new boolean[]{true, true, false, false, true, false, false, true});
        assertArrayEquals(hijosEsperados, hijos);

        System.out.println("ORDER-BASED CROSSOVER - ALGORITMO DE CRUCE - TEST 1 - RESULTADOS:");
        for (Individuo hijo : hijos) {
            System.out.println(hijo);
        }
    }

    @Test
    public void semillaTest() {
        Individuo padre1 = Util.createIndividual(3, 1, 4, 0, 8, 7, 9, 6, 5, 2);
        Individuo padre2 = Util.createIndividual(3, 8, 1, 2, 0, 6, 5, 7, 9, 4);
        RandomManager.destroyInstance();


        RandomManager.seed = 1;
        Individuo[] hijos1_1 = orderCrossover.aplicar(padre1, padre2);
        RandomManager.destroyInstance();

        RandomManager.seed = 1;
        Individuo[] hijos1_2 = orderCrossover.aplicar(padre1, padre2);
        RandomManager.destroyInstance();

        RandomManager.seed = 5;
        Individuo[] hijos2_1 = positionCrossover.aplicar(padre1, padre2);
        RandomManager.destroyInstance();

        RandomManager.seed = 5;
        Individuo[] hijos2_2 = positionCrossover.aplicar(padre1, padre2);
        RandomManager.destroyInstance();


        Assert.assertArrayEquals(hijos1_1, hijos1_2);
        Assert.assertArrayEquals(hijos2_1, hijos2_2);
    }
}

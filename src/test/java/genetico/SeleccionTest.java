package test.java.genetico;

import main.java.genetico.Generacion;
import main.java.genetico.Individuo;
import main.java.genetico.algoritmos.seleccion.AlgoritmoSeleccion;
import main.java.genetico.algoritmos.seleccion.SeleccionParesAleatorios;
import main.java.genetico.algoritmos.seleccion.SeleccionTorneo;
import main.java.util.Util;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SeleccionTest {

    private AlgoritmoSeleccion selector;

/*
    @Before
    public void inicializar() {
        profesores = CSVReader.CsvLoadProfesores();
        asignaturas = CSVReader.CsvLoadAsignaturas();
    }
*/

    /*  Se evitará el uso de generaciones de tamaño IMPAR
        private Generacion generacionA() {
        Individuo individuo1 = Util.createIndividual(41, 45, 4, 9, 38, 21, 60, 59, 63, 27, 12, 67, 82, 58, 78, 3, 2, 11, 55, 34, 81, 64, 1, 37, 80, 15, 49, 30, 47, 18, 35, 24, 23, 51, 39, 0, 33, 8, 44, 70, 68, 7, 19, 6, 50, 43, 42, 71, 14, 66, 79, 29, 75, 72, 73, 76, 74, 56, 62, 5, 65, 54, 52, 53, 69, 28, 46, 20, 10, 32, 48, 36, 17, 16, 57, 26, 25, 13, 61, 77, 31, 22, 40);
        Individuo individuo2 = Util.createIndividual(76, 25, 27, 21, 10, 34, 48, 77, 12, 0, 74, 6, 39, 59, 55, 82, 71, 56, 49, 58, 36, 4, 8, 50, 61, 16, 67, 5, 26, 15, 45, 80, 37, 54, 1, 14, 41, 64, 9, 29, 66, 22, 31, 69, 17, 62, 42, 51, 19, 40, 7, 52, 20, 44, 30, 2, 81, 68, 38, 23, 35, 72, 32, 70, 33, 46, 43, 79, 53, 65, 63, 11, 18, 75, 13, 60, 47, 24, 57, 3, 28, 73, 78);
        Individuo individuo3 = Util.createIndividual(5, 8, 77, 24, 2, 82, 35, 22, 70, 32, 48, 64, 0, 30, 56, 33, 28, 65, 78, 59, 34, 53, 31, 81, 12, 9, 25, 16, 18, 10, 15, 7, 1, 21, 72, 58, 61, 43, 66, 14, 80, 46, 74, 49, 29, 51, 17, 26, 37, 75, 11, 36, 67, 3, 55, 62, 40, 6, 27, 63, 69, 44, 57, 38, 45, 73, 60, 13, 41, 42, 71, 47, 76, 20, 68, 39, 79, 50, 19, 52, 54, 23, 4);
        return new Generacion(individuo1, individuo2, individuo3);
    }
*/
    private Generacion generacionB() {
        Individuo individuo1 = Util.createIndividual(41, 45, 4, 9, 38, 21, 60, 59, 63, 27, 12, 67, 82, 58, 78, 3, 2, 11, 55, 34, 81, 64, 1, 37, 80, 15, 49, 30, 47, 18, 35, 24, 23, 51, 39, 0, 33, 8, 44, 70, 68, 7, 19, 6, 50, 43, 42, 71, 14, 66, 79, 29, 75, 72, 73, 76, 74, 56, 62, 5, 65, 54, 52, 53, 69, 28, 46, 20, 10, 32, 48, 36, 17, 16, 57, 26, 25, 13, 61, 77, 31, 22, 40);
        Individuo individuo2 = Util.createIndividual(76, 25, 27, 21, 10, 34, 48, 77, 12, 0, 74, 6, 39, 59, 55, 82, 71, 56, 49, 58, 36, 4, 8, 50, 61, 16, 67, 5, 26, 15, 45, 80, 37, 54, 1, 14, 41, 64, 9, 29, 66, 22, 31, 69, 17, 62, 42, 51, 19, 40, 7, 52, 20, 44, 30, 2, 81, 68, 38, 23, 35, 72, 32, 70, 33, 46, 43, 79, 53, 65, 63, 11, 18, 75, 13, 60, 47, 24, 57, 3, 28, 73, 78);
        Individuo individuo3 = Util.createIndividual(5, 8, 77, 24, 2, 82, 35, 22, 70, 32, 48, 64, 0, 30, 56, 33, 28, 65, 78, 59, 34, 53, 31, 81, 12, 9, 25, 16, 18, 10, 15, 7, 1, 21, 72, 58, 61, 43, 66, 14, 80, 46, 74, 49, 29, 51, 17, 26, 37, 75, 11, 36, 67, 3, 55, 62, 40, 6, 27, 63, 69, 44, 57, 38, 45, 73, 60, 13, 41, 42, 71, 47, 76, 20, 68, 39, 79, 50, 19, 52, 54, 23, 4);
        Individuo individuo4 = Util.createIndividual(76, 25, 27, 21, 10, 59, 55, 82, 71, 56, 49, 58, 36, 4, 8, 50, 61, 16, 67, 5, 26, 34, 48, 77, 12, 0, 74, 6, 39, 15, 45, 80, 37, 54, 1, 14, 41, 64, 9, 29, 66, 22, 31, 69, 17, 62, 42, 51, 19, 40, 7, 52, 20, 44, 30, 2, 81, 68, 38, 23, 35, 72, 32, 70, 33, 46, 43, 79, 53, 65, 63, 11, 18, 75, 13, 60, 47, 24, 57, 3, 28, 73, 78);

        return new Generacion(individuo1, individuo2, individuo3, individuo4);
    }

    public void parejasAleatorias(int test, int resultado, Generacion generacion) {
        selector = new SeleccionParesAleatorios();
        List<Individuo[]> res = selector.aplicar(generacion);

        System.out.println("PAREJAS ALEATORIAS - ALGORITMO DE SELECCION - TEST " + test + " - RESULTADOS:");

        for (Individuo[] par : res) {
            System.out.println(Arrays.deepToString(par));
        }

        assertEquals(resultado, res.size());
    }

    public void torneo(int idTest, int resultado, Generacion generacion) {
        selector = new SeleccionTorneo(generacion.size());
        List<Individuo[]> res = selector.aplicar(generacion);

        System.out.println("TORNEO - ALGORITMO DE SELECCION - TEST " + idTest + " - RESULTADOS:");

        for (Individuo[] par : res) {
            System.out.println(Arrays.deepToString(par));
        }

        assertEquals(resultado, res.size());
    }

    //@Test
    //public void test1() {
    //    parejasAleatorias(1, 2, generacionA());
    //}


    @Test
    public void parejasAleatorias2() {
        parejasAleatorias(2, 2, generacionB());
    }

    @Test
    public void torneo1() {
        torneo(1, 2, generacionB());
    }


}

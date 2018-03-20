package test.java.genetico;

import main.java.CSVReader;
import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.Individuo;
import main.java.genetico.algoritmos.AlgoritmoDecodificacion;
import main.java.genetico.algoritmos.Decodificacion;
import main.java.util.Util;
import org.junit.Test;

/**
 * Created by Sergio Florez on 06/03/2018.
 */
public class FitnessTest {
    AlgoritmoGenetico a = new AlgoritmoGenetico();
    @Test
    public void tetst1(){
        Individuo v = new Individuo(new int[]{2, 52, 0, 36, 24, 1, 12, 80, 4, 82, 59, 64, 39, 41, 45, 3, 15, 43, 63, 28, 66, 11, 21, 81, 8, 14, 33, 58, 34, 35, 61, 18, 60, 42, 50, 20, 79, 16, 48, 22, 74, 26, 71, 19, 49, 75, 40, 5, 7, 65, 51, 70, 30, 55, 54, 10, 9, 56, 73, 6, 76, 68, 67, 44, 29, 25, 69, 78, 77, 37, 27, 32, 17, 38, 23, 31, 57, 62, 72, 53, 47, 13, 46});
       AlgoritmoDecodificacion decoder = new Decodificacion();
       decoder.aplicar(v, Util.listToArrayProfesor(CSVReader.CsvLoadProfesores()),Util.listToArrayGrupo(CSVReader.CsvLoadAsignaturas()));
        System.out.println(v.getFenotipo());
    }
}

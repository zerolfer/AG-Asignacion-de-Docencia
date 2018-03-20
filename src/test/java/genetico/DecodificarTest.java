package test.java.genetico;

import main.java.CSVReader;
import main.java.genetico.Individuo;
import main.java.genetico.algoritmos.AlgoritmoDecodificacion;
import main.java.genetico.algoritmos.Decodificacion;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DecodificarTest {

    private AlgoritmoDecodificacion decoder = new Decodificacion();
    private Profesor[] profesores;
    private GrupoAsignatura[] asignaturas;

    @Before
    public void inicializar() {
        List<Profesor> profesores = CSVReader.CsvLoadProfesores();
        List<GrupoAsignatura> asignaturas = CSVReader.CsvLoadAsignaturas();
        this.profesores = profesores.toArray(new Profesor[profesores.size()]);
        this.asignaturas = asignaturas.toArray(new GrupoAsignatura[asignaturas.size()]);

    }

    @Test
    public void test1() {
        inicializar();
        Individuo individuo = new Individuo(
                new int[]{41, 45, 4, 9, 38, 21, 60, 59, 63, 27, 12, 67, 82, 58, 78, 3, 2, 11, 55, 34, 81, 64, 1,
                        37, 80, 15, 49, 30, 47, 18, 35, 24, 23, 51, 39, 0, 33, 8, 44, 70, 68, 7, 19, 6, 50, 43,
                        42, 71, 14, 66, 79, 29, 75, 72, 73, 76, 74, 56, 62, 5, 65, 54, 52, 53, 69, 28, 46, 20, 10,
                        32, 48, 36, 17, 16, 57, 26, 25, 13, 61, 77, 31, 22, 40});
        decoder.aplicar(individuo, profesores, asignaturas);

        assertEquals("el fitnes no era el esperado", 15, individuo.getFitnessAsigProfesor());
    }
}

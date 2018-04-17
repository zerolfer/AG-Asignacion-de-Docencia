package test.java.genetico;

import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.Generacion;
import main.java.genetico.Individuo;
import main.java.genetico.algoritmos.creacion.AlgoritmoCreacion;
import main.java.genetico.algoritmos.creacion.CreacionAleatoria;
import main.java.genetico.algoritmos.cruce.AlgoritmoCruce;
import main.java.genetico.algoritmos.cruce.CruceOrderBased;
import main.java.genetico.algoritmos.mutacion.AlgoritmoMutacion;
import main.java.genetico.algoritmos.mutacion.MutacionIntercambio;
import main.java.genetico.algoritmos.reemplazo.AlgoritmoReemplazo;
import main.java.genetico.algoritmos.reemplazo.ReemplazoGeneracional;
import main.java.genetico.algoritmos.seleccion.AlgoritmoSeleccion;
import main.java.genetico.algoritmos.seleccion.SeleccionAleatoria;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;
import main.java.util.RandomManager;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Sergio Florez on 23/03/2018.
 */
public class GeneticoTest {

    private List<Profesor> profesores;
    private List<GrupoAsignatura> asignaturas;

/*
    @Before
    public void inicializar() {
        profesores = CSVReader.CsvLoadProfesores();
        asignaturas = CSVReader.CsvLoadAsignaturas();
    }
*/

    @Test
    public void algortimoHastaElCruce() {
        AlgoritmoCreacion creacion = new CreacionAleatoria();
        AlgoritmoSeleccion seleccion = new SeleccionAleatoria();
        AlgoritmoCruce cruce = new CruceOrderBased(1);
        AlgoritmoMutacion mutacion = new MutacionIntercambio(1);
        AlgoritmoReemplazo reemplazo = new ReemplazoGeneracional();

        for (int idx = 0; idx < 30; idx++) {
            Generacion generacion = creacion.createPopulation(20);
            generacion.evaluar();

            List<Individuo[]> padres = seleccion.aplicar(generacion);

            for (Individuo[] par : padres) {
                for (Individuo i : par) {
                    assertFalse(i.checkHayRepetidos());
                }
            }

            List<Individuo[]> hijos = cruce.aplicar(padres);

            for (Individuo[] par : hijos) {
                for (Individuo i : par) {
                    assertFalse(i.checkHayRepetidos());
                }
            }

            generacion.evaluar();

            mutacion.mutar(hijos);

            for (Individuo[] par : hijos) {
                for (Individuo i : par) {
                    assertFalse(i.checkHayRepetidos());
                }
            }
            generacion = agrupar(hijos);
            for (Individuo[] par : hijos) {
                for (Individuo i : par) {
                    assertFalse(i.checkHayRepetidos());
                }
            }
            generacion.evaluar();

            int sizeAnterior = generacion.size();
            generacion = reemplazo.aplicar(padres, hijos);
            for (Individuo[] par : hijos) {
                for (Individuo i : par) {
                    assertFalse(i.checkHayRepetidos());
                }
            }
            assertEquals(sizeAnterior, generacion.size());
        }


    }

    private Generacion agrupar(List<Individuo[]> individuos) {
        List<Individuo> result = new ArrayList<>();
        for (Individuo[] par : individuos) {
            for (Individuo indi : par) {
                result.add(indi);
            }
        }
        return new Generacion(result.toArray(new Individuo[result.size()]));
    }

    @Test
    public void semillaTest1() {
        for (int i = 0; i < 30; i++) {
            RandomManager.destroyInstance();

            RandomManager.seed = i;
            int n1_1 = RandomManager.getInstance().getRandomNumber(1000);
            int n1_2 = RandomManager.getInstance().getRandomNumber(1000);
            int n1_3 = RandomManager.getInstance().getRandomNumber(1000);
            RandomManager.destroyInstance();
            int n2_1 = RandomManager.getInstance().getRandomNumber(1000);
            int n2_2 = RandomManager.getInstance().getRandomNumber(1000);
            int n2_3 = RandomManager.getInstance().getRandomNumber(1000);
            RandomManager.destroyInstance();

            Assert.assertEquals("iteracion " + i, n1_1, n2_1);
            Assert.assertEquals("iteracion " + i, n1_2, n2_2);
            Assert.assertEquals("iteracion " + i, n1_3, n2_3);

            float n1 = RandomManager.getInstance().getFloatRandomNumber(1, 100);
            float n2 = RandomManager.getInstance().getTrialProbability();
            assertTrue(n1 >= 1 && n1 < 100);
            assertTrue(n2 >= 0 && n2 < 1);

        }
        RandomManager.destroyInstance();


    }

    @Test
    public void semillaTest2() {
        AlgoritmoGenetico g = new AlgoritmoGenetico();
        g.iniciar("semillaTest2 - 1", 1);
        String r1 = g.getMejorIndividuo().toStringFull();
        g.iniciar("semillaTest2 - 2", 1);
        String r2 = g.getMejorIndividuo().toStringFull();

        AlgoritmoGenetico g2 = new AlgoritmoGenetico();
        g2.iniciar("semillaTest2 - 3", 1);
        String r3 = g.getMejorIndividuo().toStringFull();


        Assert.assertEquals(r1, r2);
        Assert.assertEquals(r1, r3);

    }

}

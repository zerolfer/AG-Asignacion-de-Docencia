package test.java.genetico;

import main.java.genetico.algoritmos.reemplazo.ReemplazoGeneracional;
import main.java.genetico.Generacion;
import main.java.genetico.Individuo;
import main.java.genetico.algoritmos.creacion.AlgoritmoCreacion;
import main.java.genetico.algoritmos.creacion.CreacionAleatoria;
import main.java.genetico.algoritmos.cruce.AlgoritmoCruce;
import main.java.genetico.algoritmos.cruce.CruceOrderBased;
import main.java.genetico.algoritmos.mutacion.AlgoritmoMutacion;
import main.java.genetico.algoritmos.mutacion.MutacionIntercambio;
import main.java.genetico.algoritmos.reemplazo.AlgoritmoReemplazo;
import main.java.genetico.algoritmos.seleccion.AlgoritmoSeleccion;
import main.java.genetico.algoritmos.seleccion.SeleccionAleatoria;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
            generacion = reemplazo.aplicar(padres,hijos);
            for (Individuo[] par : hijos) {
                for (Individuo i : par) {
                    assertFalse(i.checkHayRepetidos());
                }
            }
            assertEquals(sizeAnterior,generacion.size());
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

}

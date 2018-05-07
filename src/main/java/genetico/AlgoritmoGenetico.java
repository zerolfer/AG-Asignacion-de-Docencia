package main.java.genetico;


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
import main.java.model.BD;
import main.java.util.RandomManager;
import main.java.util.Stopwatch;
import main.java.util.writer.CSVWriter;
import main.java.util.writer.DatosDetalladosEjecuciones;
import main.java.util.writer.DatosFenotipoEjecuciones;

import java.io.File;
import java.util.List;

public class AlgoritmoGenetico {

    // VARIABLES DE ENTRADA DEL GENÉTICO
    public static final Integer POPULATION_SIZE = 100;
    public static final Float PROBABILIDAD_CRUCE = 0.7f;
    public static final Float PROBABILIDAD_MUTACION = 0.075f;
    public static final Integer NUMERO_GENERACIONES = 1000;

    // ALGORITMOS
    AlgoritmoCreacion creacion;
    AlgoritmoSeleccion seleccion;
    AlgoritmoCruce cruce;
    AlgoritmoMutacion mutacion;
    AlgoritmoReemplazo reemplazo;

    // ESTRUCTURAS AUXILIARES
    private Individuo mejorIndividuo;
    private boolean debug = true;
    private Stopwatch timer = new Stopwatch();
    private boolean printed = false;

    public AlgoritmoGenetico(AlgoritmoCreacion creator, AlgoritmoSeleccion seleccion, AlgoritmoCruce cruce,
                             AlgoritmoMutacion mutacion, AlgoritmoReemplazo reemplazo) {
        this.creacion = creator;
        this.seleccion = seleccion;
        this.cruce = cruce;
        this.mutacion = mutacion;
        this.reemplazo = reemplazo;
        // this.decodificacion = new Decodificacion();
        // ordenarAsignaturas();
        ordenarProfesores();

    }

    public AlgoritmoGenetico() {
        this(new CreacionAleatoria(), new SeleccionAleatoria(), new CruceOrderBased(PROBABILIDAD_CRUCE),
                new MutacionIntercambio(PROBABILIDAD_MUTACION), new ReemplazoGeneracional());
    }

    public void iniciar(String ejecucion, int seed) {
        RandomManager.seed = seed;
        genetico(ejecucion);
        RandomManager.destroyInstance();
    }

    private void ordenarProfesores() {
        BD.getProfesores().sort(BD.comparatorProfesores);
    }

    private void ordenarAsignaturas() {
        BD.getAsignaturas().sort(BD.comparatorAsignaturas);
    }


    private void genetico(String ejecucion) {

        CSVWriter printer2 = null;
        CSVWriter printer3 = null;
        if (!printed) {
            printer2 = new DatosDetalladosEjecuciones(
                    "files/ejecuciones/DatosDetalladosEjecucion" + ejecucion + ".csv",
                    getAlgoritmos());
            printer3 = new DatosFenotipoEjecuciones(
                    "files/DatosFenotipoEjecuciones.csv", ejecucion);
        }

        Generacion generacion = creacion.createPopulation(POPULATION_SIZE);
        int numGeneraciones = 1;
        timer.start();

        do {
            generacion.evaluar();

            List<Individuo[]> padres = seleccion.aplicar(generacion);
            List<Individuo[]> hijos = cruce.aplicar(padres);

            mutar(hijos);

            evaluar(hijos);

            int sizeAnterior = generacion.size();
            generacion = reemplazo.aplicar(padres, hijos);

            assert sizeAnterior == generacion.size();

            timer.newLap(numGeneraciones);
            if (!printed) {
                Individuo mejor = obtenerMejor(generacion);
                float[] fitnessMedio = generacion.obtenerFitnessMedio();
                printer2.csvWriteData(this, //TODO: refartor this to the printer class
                        Integer.toString(numGeneraciones), timer.getTimeAtGeneration(numGeneraciones).toString(),
                        Integer.toString(mejor.getFitnessAsigProfesor()), Float.toString(mejor.getFitnessNumHoras()),
                        Float.toString(fitnessMedio[0]), Float.toString(fitnessMedio[1]));
            }
            numGeneraciones++;
        } while (numGeneraciones <= NUMERO_GENERACIONES);

        mejorIndividuo = obtenerMejor(generacion);
        if (!printed)
            printer3.csvWriteData(this);

        if (!printed) {
            printer2.close();
            printer3.close();
            printed = true;
        }
        if (debug) System.out.println("Mejor resultado: \n" + mejorIndividuo.toStringFull());
//            System.out.println("Mejor resultado: \n" + obtenerMejor(generacion).toString());
    }

    private Individuo obtenerMejor(Generacion generacion) {
        Individuo[] genotipo = generacion.getGenotipo();
        Individuo mejor = genotipo[0];
        for (Individuo indi : genotipo) {
            if (mejor.getFitnessAsigProfesor() == indi.getFitnessAsigProfesor()) {
                if (indi.getFitnessNumHoras() > mejor.getFitnessNumHoras())
                    mejor = indi;
            } else if (indi.getFitnessAsigProfesor() < mejor.getFitnessAsigProfesor())
                mejor = indi;
        }
        return mejor;
    }

    private void mutar(List<Individuo[]> individuos) {
        for (Individuo[] par : individuos)
            for (Individuo indi : par)
                mutacion.mutar(indi);
    }

    private void evaluar(List<Individuo[]> individuos) {
        for (Individuo[] par : individuos)
            for (Individuo indi : par)
                indi.evaluar();
    }

    public String[] getAlgoritmos() {
        return new String[]{
                /*unir(*/ creacion.getClass().getSimpleName() /*.split("(?=\\p{Upper})") )*/,
                /*unir(*/ seleccion.getClass().getSimpleName() /*.split("(?=\\p{Upper})") )*/,
                /*unir(*/ cruce.getClass().getSimpleName() /*.split("(?=\\p{Upper})") )*/,
                /*unir(*/ mutacion.getClass().getSimpleName() /*.split("(?=\\p{Upper})") )*/,
                /*unir(*/ reemplazo.getClass().getSimpleName() /*.split("(?=\\p{Upper})") )*/
        };
    }

    private String unir(String[] split) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            sb.append(split[i]);
            //if(i<split.length-1)
            //   sb.append(" ");
        }
        return sb.toString();
    }


    public Individuo getMejorIndividuo() {
        return mejorIndividuo;
    }

    public Stopwatch getTimer() {
        return timer;
    }
}

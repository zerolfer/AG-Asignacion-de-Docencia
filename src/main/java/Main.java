package main.java;

import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.algoritmos.creacion.CreacionAleatoria;
import main.java.genetico.algoritmos.cruce.CruceOrderBased;
import main.java.genetico.algoritmos.cruce.CrucePositionBased;
import main.java.genetico.algoritmos.mutacion.MutacionInversion;
import main.java.genetico.algoritmos.reemplazo.ReemplazoGeneracional;
import main.java.genetico.algoritmos.reemplazo.ReemplazoTorneo;
import main.java.genetico.algoritmos.reemplazo.ReemplazoTorneoPH;
import main.java.genetico.algoritmos.seleccion.SeleccionAleatoria;
import main.java.genetico.algoritmos.seleccion.SeleccionRuleta;
import main.java.genetico.algoritmos.seleccion.SeleccionTorneo;
import main.java.util.writer.CSVWriter;
import main.java.util.writer.DatosGlobalesEjecuciones;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static main.java.genetico.AlgoritmoGenetico.PROBABILIDAD_CRUCE;
import static main.java.genetico.AlgoritmoGenetico.PROBABILIDAD_MUTACION;

public class Main {
    private static final int NUM_EJECUCIONES = 10;
    private static CSVWriter printer1 =
            new DatosGlobalesEjecuciones("files/DatosGlobalesEjecuciones.csv");

    /*   private static CSVWriter printer2 =
               new DatosDetalladosEjecuciones("files/DatosDetalladosEjecuciones.csv");
       private static CSVWriter printer3 =
               new FenotipoEjecuciones("files/FenotipoEjecuciones.csv");
   */
    public static void main(String args[]) {

        //AbstractCSVWriter out = new AbstractCSVWriter("files/output.csv");

        AlgoritmoGenetico genetico1_1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionAleatoria(),
                new CruceOrderBased(PROBABILIDAD_CRUCE),
                new MutacionInversion(PROBABILIDAD_MUTACION),
                new ReemplazoTorneo());

        AlgoritmoGenetico genetico1_2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionAleatoria(),
                new CrucePositionBased(PROBABILIDAD_CRUCE),
                new MutacionInversion(PROBABILIDAD_MUTACION),
                new ReemplazoTorneo());

        AlgoritmoGenetico genetico2_1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionAleatoria(),
                new CruceOrderBased(PROBABILIDAD_CRUCE),
                new MutacionInversion(PROBABILIDAD_MUTACION),
                new ReemplazoTorneoPH());

        AlgoritmoGenetico genetico2_2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionAleatoria(),
                new CrucePositionBased(PROBABILIDAD_CRUCE),
                new MutacionInversion(PROBABILIDAD_MUTACION),
                new ReemplazoTorneoPH());

        AlgoritmoGenetico genetico3_1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionRuleta(),
                new CruceOrderBased(PROBABILIDAD_CRUCE),
                new MutacionInversion(PROBABILIDAD_MUTACION),
                new ReemplazoGeneracional());
        AlgoritmoGenetico genetico3_2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionRuleta(),
                new CrucePositionBased(PROBABILIDAD_CRUCE),
                new MutacionInversion(PROBABILIDAD_MUTACION),
                new ReemplazoGeneracional());

        AlgoritmoGenetico genetico4_1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionTorneo(4),
                new CruceOrderBased(PROBABILIDAD_CRUCE),
                new MutacionInversion(PROBABILIDAD_MUTACION),
                new ReemplazoGeneracional());
        AlgoritmoGenetico genetico4_2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionTorneo(4),
                new CrucePositionBased(PROBABILIDAD_CRUCE),
                new MutacionInversion(PROBABILIDAD_MUTACION),
                new ReemplazoGeneracional());

        lanzarAlgoritmo("2_1", genetico2_1);
        lanzarAlgoritmo("2_1", genetico2_1);
        lanzarAlgoritmo("1_1", genetico1_1);
        lanzarAlgoritmo("3_1", genetico3_1);
        lanzarAlgoritmo("4_1", genetico4_1);
        lanzarAlgoritmo("1_2", genetico1_2);
        lanzarAlgoritmo("2_2", genetico2_2);
        lanzarAlgoritmo("3_2", genetico3_2);
        lanzarAlgoritmo("4_2", genetico4_2);

        printer1.close();
        /*printer2.close();
        printer3.close();*/

    }

    private static void lanzarAlgoritmo(String id, AlgoritmoGenetico genetico) {

        List<String> configuracion = new ArrayList<>();

        configuracion.add(AlgoritmoGenetico.POPULATION_SIZE.toString());
        configuracion.add(AlgoritmoGenetico.PROBABILIDAD_CRUCE.toString());
        configuracion.add(AlgoritmoGenetico.PROBABILIDAD_MUTACION.toString());
        configuracion.add(AlgoritmoGenetico.NUMERO_GENERACIONES.toString());
        configuracion.addAll(Arrays.asList(genetico.getAlgoritmos()));

        for (int i = 1; i <= NUM_EJECUCIONES; i++) {

            System.out.println("ALGORITMO GENÉTICO - EJECUCIÓN " + id + ":");
            System.out.print("\tIteracion " + i + "...");

            long t1 = System.currentTimeMillis();
            genetico.iniciar(i);
            long t2 = System.currentTimeMillis();

            printer1.csvWriteData(genetico, configuracion);
            // printer2.csvWriteData(genetico);
            // printer3.csvWriteData(genetico);
/*
            Individuo resultado = genetico.getMejorIndividuo();
            List<String> especifico = new ArrayList<>();
            especifico.add(Integer.toString(resultado.getFitnessAsigProfesor()));
            especifico.add(Float.toString(resultado.getFitnessNumHoras()));
            especifico.add(genetico.getTimer().getFinalTime().toString());
            especifico.add(Long.toString(RandomManager.seed));

            out.csvWriteDataCollection(configuracion, especifico);
*/

            System.out.println("Hecho!");
        }
    }
}

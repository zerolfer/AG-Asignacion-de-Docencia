package main.java;

import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.Individuo;
import main.java.genetico.algoritmos.creacion.CreacionAleatoria;
import main.java.genetico.algoritmos.cruce.CruceOrderBased;
import main.java.genetico.algoritmos.cruce.CrucePositionBased;
import main.java.genetico.algoritmos.mutacion.MutacionIntercambio;
import main.java.genetico.algoritmos.reemplazo.ReemplazoGeneracional;
import main.java.genetico.algoritmos.reemplazo.ReemplazoTorneo;
import main.java.genetico.algoritmos.reemplazo.ReemplazoTorneoPH;
import main.java.genetico.algoritmos.seleccion.SeleccionAleatoria;
import main.java.genetico.algoritmos.seleccion.SeleccionRuleta;
import main.java.genetico.algoritmos.seleccion.SeleccionTorneo;
import main.java.util.CSVWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.java.genetico.AlgoritmoGenetico.PROBABILIDAD_CRUCE;
import static main.java.genetico.AlgoritmoGenetico.PROBABILIDAD_MUTACION;

public class Main {
    private static final int NUM_EJECUCIONES = 10;

    public static void main(String args[]) {

        CSVWriter out = new CSVWriter("files/output.csv");

        AlgoritmoGenetico genetico1_1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionAleatoria(),
                new CruceOrderBased(PROBABILIDAD_CRUCE),
                new MutacionIntercambio(PROBABILIDAD_MUTACION),
                new ReemplazoTorneo());

        AlgoritmoGenetico genetico1_2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionAleatoria(),
                new CrucePositionBased(PROBABILIDAD_CRUCE),
                new MutacionIntercambio(PROBABILIDAD_MUTACION),
                new ReemplazoTorneo());

        AlgoritmoGenetico genetico2_1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionAleatoria(),
                new CruceOrderBased(PROBABILIDAD_CRUCE),
                new MutacionIntercambio(PROBABILIDAD_MUTACION),
                new ReemplazoTorneoPH());
        AlgoritmoGenetico genetico2_2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionAleatoria(),
                new CrucePositionBased(PROBABILIDAD_CRUCE),
                new MutacionIntercambio(PROBABILIDAD_MUTACION),
                new ReemplazoTorneoPH());

        AlgoritmoGenetico genetico3_1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionRuleta(),
                new CruceOrderBased(PROBABILIDAD_CRUCE),
                new MutacionIntercambio(PROBABILIDAD_MUTACION),
                new ReemplazoGeneracional());
        AlgoritmoGenetico genetico3_2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionRuleta(),
                new CrucePositionBased(PROBABILIDAD_CRUCE),
                new MutacionIntercambio(PROBABILIDAD_MUTACION),
                new ReemplazoGeneracional());

        AlgoritmoGenetico genetico4_1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionTorneo(4),
                new CruceOrderBased(PROBABILIDAD_CRUCE),
                new MutacionIntercambio(PROBABILIDAD_MUTACION),
                new ReemplazoGeneracional());
        AlgoritmoGenetico genetico4_2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionTorneo(4),
                new CrucePositionBased(PROBABILIDAD_CRUCE),
                new MutacionIntercambio(PROBABILIDAD_MUTACION),
                new ReemplazoGeneracional());

        //lanzarAlgoritmo(genetico2_1, out);
        //lanzarAlgoritmo(genetico1_1, out);
        lanzarAlgoritmo("3_1", genetico3_1, out);
        lanzarAlgoritmo("4_1", genetico4_1, out);
        lanzarAlgoritmo("1_2", genetico1_2, out);
        lanzarAlgoritmo("2_2", genetico2_2, out);
        lanzarAlgoritmo("3_2", genetico3_2, out);
        lanzarAlgoritmo("4_2", genetico4_2, out);

    }

    private static void lanzarAlgoritmo(String id, AlgoritmoGenetico genetico, CSVWriter out) {

        List<String> configuracion = new ArrayList<>();

        configuracion.add(AlgoritmoGenetico.POPULATION_SIZE.toString());
        configuracion.add(AlgoritmoGenetico.PROBABILIDAD_CRUCE.toString());
        configuracion.add(AlgoritmoGenetico.PROBABILIDAD_MUTACION.toString());
        configuracion.add(AlgoritmoGenetico.NUMERO_GENERACIONES.toString());
        configuracion.addAll(Arrays.asList(genetico.getAlgoritmos()));
        System.out.print("ALGORITMO GENÉTICO - EJECUCIÓN " + id + ":");

        for (int i = 1; i <= NUM_EJECUCIONES; i++) {
            System.out.print("\tIteracion " + i + "...");

            long t1 = System.currentTimeMillis();
            genetico.iniciar();
            long t2 = System.currentTimeMillis();

            List<String> especifico = new ArrayList<>();
            Individuo resultado = genetico.getMejorIndividuo();
            especifico.add(Integer.toString(resultado.getFitnessAsigProfesor()));
            especifico.add(Float.toString(resultado.getFitnessNumHoras()));
            especifico.add(Long.toString(t2 - t1));

            out.CsvWriteDataCollection(configuracion, especifico);

            System.out.println("Hecho!");
        }

    }
}

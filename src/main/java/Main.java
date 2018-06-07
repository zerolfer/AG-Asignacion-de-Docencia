package main.java;

import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.algoritmos.creacion.CreacionAleatoria;
import main.java.genetico.algoritmos.cruce.CruceOrderBased;
import main.java.genetico.algoritmos.cruce.CrucePositionBased;
import main.java.genetico.algoritmos.mutacion.MutacionInversion;
import main.java.genetico.algoritmos.reemplazo.ReemplazoGeneracional;
import main.java.genetico.algoritmos.reemplazo.ReemplazoTorneo;
import main.java.genetico.algoritmos.reemplazo.ReemplazoTorneoPH;
import main.java.genetico.algoritmos.reemplazo.ReemplazoTorneoPHSinRepetir;
import main.java.genetico.algoritmos.seleccion.SeleccionAleatoria;
import main.java.genetico.algoritmos.seleccion.SeleccionRuleta;
import main.java.genetico.algoritmos.seleccion.SeleccionTorneo;
import main.java.util.writer.CSVWriter;
import main.java.util.writer.DatosGlobalesEjecuciones;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.java.genetico.AlgoritmoGenetico.PROBABILIDAD_CRUCE;
import static main.java.genetico.AlgoritmoGenetico.PROBABILIDAD_MUTACION;

public class Main {

    private static final int NUM_EJECUCIONES = 1; // TODO reestablecer valor a 10

    private static CSVWriter printer1 =
            new DatosGlobalesEjecuciones("files/DatosGlobalesEjecuciones.csv");

    public static void main(String args[]) {
        new File("files/ejecuciones/").mkdirs(); // crea la ruta en caso de no existir

        /*
         * Combinaciones de algoritmos, ver cada javadoc
         */
        //lanzarAlgoritmo("BL_1_1", genetico1_1());
        lanzarAlgoritmo("BL2_2_1", genetico2_1());
        //lanzarAlgoritmo("BL_3_1", genetico3_1());
        //lanzarAlgoritmo("BL_4_1", genetico4_1());
        //lanzarAlgoritmo("BL_1_2", genetico1_2());
//        lanzarAlgoritmo("BL_2_2", genetico2_2());
        //lanzarAlgoritmo("BL_3_2", genetico3_2());
        //lanzarAlgoritmo("BL_4_2", genetico4_2());

        printer1.close();
        /*printer3.close();*/

    }

    private static void lanzarAlgoritmo(String id, AlgoritmoGenetico genetico) {

        List<String> configuracion = new ArrayList<>();

        configuracion.add(id);
        configuracion.add(AlgoritmoGenetico.POPULATION_SIZE.toString());
        configuracion.add(AlgoritmoGenetico.PROBABILIDAD_CRUCE.toString());
        configuracion.add(AlgoritmoGenetico.PROBABILIDAD_MUTACION.toString());
        configuracion.add(AlgoritmoGenetico.NUMERO_GENERACIONES.toString());
        configuracion.addAll(Arrays.asList(genetico.getAlgoritmos()));

        for (int i = 1; i <= NUM_EJECUCIONES; i++) {

            System.out.println("ALGORITMO GENÉTICO - EJECUCIÓN " + id + ":");
            System.out.print("\tIteracion " + i + "...");

            genetico.iniciar(id, i);

            printer1.csvWriteData(genetico, configuracion);

            System.out.println("Hecho!");
        }
    }

    /**
     * CreacionAleatoria(),
     * SeleccionTorneo(4),
     * CrucePositionBased(PROBABILIDAD_CRUCE),
     * MutacionInversion(PROBABILIDAD_MUTACION),
     * ReemplazoGeneracional());
     */
    private static AlgoritmoGenetico genetico4_2() {
        AlgoritmoGenetico genetico4_2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionTorneo(4),
                new CrucePositionBased(PROBABILIDAD_CRUCE),
                new MutacionInversion(PROBABILIDAD_MUTACION),
                new ReemplazoGeneracional());
        return genetico4_2;
    }

    /**
     * CreacionAleatoria(),
     * SeleccionTorneo(4),
     * CruceOrderBased(PROBABILIDAD_CRUCE),
     * MutacionInversion(PROBABILIDAD_MUTACION),
     * ReemplazoGeneracional());
     */
    private static AlgoritmoGenetico genetico4_1() {
        AlgoritmoGenetico genetico4_1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionTorneo(4),
                new CruceOrderBased(PROBABILIDAD_CRUCE),
                new MutacionInversion(PROBABILIDAD_MUTACION),
                new ReemplazoGeneracional());
        return genetico4_1;
    }

    /**
     * CreacionAleatoria(),
     * SeleccionRuleta(),
     * CrucePositionBased(PROBABILIDAD_CRUCE),
     * MutacionInversion(PROBABILIDAD_MUTACION),
     * ReemplazoGeneracional());
     *
     * @return
     */
    private static AlgoritmoGenetico genetico3_2() {
        AlgoritmoGenetico genetico3_2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionRuleta(),
                new CrucePositionBased(PROBABILIDAD_CRUCE),
                new MutacionInversion(PROBABILIDAD_MUTACION),
                new ReemplazoGeneracional());
        return genetico3_2;
    }

    /**
     * CreacionAleatoria(),
     * SeleccionRuleta(),
     * CruceOrderBased(PROBABILIDAD_CRUCE),
     * MutacionInversion(PROBABILIDAD_MUTACION),
     * ReemplazoGeneracional());
     */
    private static AlgoritmoGenetico genetico3_1() {
        AlgoritmoGenetico genetico3_1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionRuleta(),
                new CruceOrderBased(PROBABILIDAD_CRUCE),
                new MutacionInversion(PROBABILIDAD_MUTACION),
                new ReemplazoGeneracional());
        return genetico3_1;
    }

    /**
     * CreacionAleatoria(),
     * SeleccionAleatoria(),
     * CrucePositionBased(PROBABILIDAD_CRUCE),
     * MutacionInversion(PROBABILIDAD_MUTACION),
     * ReemplazoTorneoPH());
     */
    private static AlgoritmoGenetico genetico2_2() {
        AlgoritmoGenetico genetico2_2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionAleatoria(),
                new CrucePositionBased(PROBABILIDAD_CRUCE),
                new MutacionInversion(PROBABILIDAD_MUTACION),
                new ReemplazoTorneoPHSinRepetir());
        return genetico2_2;
    }

    /**
     * CreacionAleatoria(),
     * SeleccionAleatoria(),
     * CruceOrderBased(PROBABILIDAD_CRUCE),
     * MutacionInversion(PROBABILIDAD_MUTACION),
     * ReemplazoTorneoPH());
     */
    private static AlgoritmoGenetico genetico2_1() {
        return new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionAleatoria(),
                new CruceOrderBased(PROBABILIDAD_CRUCE),
                new MutacionInversion(PROBABILIDAD_MUTACION),
                new ReemplazoTorneoPHSinRepetir());
    }

    /**
     * CreacionAleatoria(),
     * SeleccionAleatoria(),
     * CrucePositionBased(PROBABILIDAD_CRUCE),
     * MutacionInversion(PROBABILIDAD_MUTACION),
     * ReemplazoTorneo());
     */
    private static AlgoritmoGenetico genetico1_2() {
        AlgoritmoGenetico genetico1_2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionAleatoria(),
                new CrucePositionBased(PROBABILIDAD_CRUCE),
                new MutacionInversion(PROBABILIDAD_MUTACION),
                new ReemplazoTorneo());
        return genetico1_2;
    }

    /**
     * CreacionAleatoria(),
     * SeleccionAleatoria(),
     * CruceOrderBased(PROBABILIDAD_CRUCE),
     * MutacionInversion(PROBABILIDAD_MUTACION),
     * ReemplazoTorneo());
     */
    private static AlgoritmoGenetico genetico1_1() {
        AlgoritmoGenetico genetico1_1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionAleatoria(),
                new CruceOrderBased(PROBABILIDAD_CRUCE),
                new MutacionInversion(PROBABILIDAD_MUTACION),
                new ReemplazoTorneo());
        return genetico1_1;
    }
}

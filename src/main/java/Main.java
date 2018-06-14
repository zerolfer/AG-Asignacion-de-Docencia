package main.java;

import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.algoritmos.creacion.CreacionAleatoria;
import main.java.genetico.algoritmos.cruce.CruceOrderBased;
import main.java.genetico.algoritmos.cruce.CrucePositionBased;
import main.java.genetico.algoritmos.mutacion.MutacionIntercambio;
import main.java.genetico.algoritmos.mutacion.MutacionInversion;
import main.java.genetico.algoritmos.reemplazo.ReemplazoGeneracional;
import main.java.genetico.algoritmos.reemplazo.ReemplazoTorneo;
import main.java.genetico.algoritmos.reemplazo.ReemplazoTorneoPH;
import main.java.genetico.algoritmos.reemplazo.ReemplazoTorneoPHSinRepeticion;
import main.java.genetico.algoritmos.seleccion.SeleccionParesAleatorios;
import main.java.genetico.algoritmos.seleccion.SeleccionRuleta;
import main.java.genetico.algoritmos.seleccion.SeleccionRuletaAntiguo;
import main.java.genetico.algoritmos.seleccion.SeleccionTorneo;
import main.java.util.writer.CSVWriter;
import main.java.util.writer.DatosGlobalesEjecuciones;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static main.java.genetico.AlgoritmoGenetico.probabilidadCruce;
import static main.java.genetico.AlgoritmoGenetico.probabilidadMutacion;

public class Main {

    private static final int NUM_EJECUCIONES = 10; // TODO reestablecer valor a 10

    private static CSVWriter printer1 =
            new DatosGlobalesEjecuciones("files/DatosGlobalesEjecuciones.csv");

    private static float probabilidadCruce08=0.8f;
    private static float probabilidadCruce09=0.9f;
    private static float probabilidadCruce1=1f;

    public static void main(String args[]) {
        new File("files/ejecuciones/").mkdirs(); // crea la ruta en caso de no existir

        lanzarAlgoritmo("AP6_mutacionInversion", genetico1());
        lanzarAlgoritmo("AP6_mutacionIntercambio", genetico2());
        //lanzarAlgoritmo("AP5_probabilidadCruce1", genetico3());
        //lanzarAlgoritmo("AP5_probabilidadCruce09SinRepeticion", genetico4());

        printer1.close();
        /*printer3.close();*/

    }

    public static void lanzarAlgoritmo(String id, AlgoritmoGenetico genetico) {

        List<String> configuracion = new ArrayList<>();

        configuracion.add(id);
        configuracion.add(AlgoritmoGenetico.populationSize.toString());
        configuracion.add(AlgoritmoGenetico.probabilidadCruce.toString());
        configuracion.add(AlgoritmoGenetico.probabilidadMutacion.toString());
        configuracion.add(AlgoritmoGenetico.numeroGeneraciones.toString());
        configuracion.addAll(Arrays.asList(genetico.getAlgoritmos()));

        for (int i = 1; i <= NUM_EJECUCIONES; i++) {

            System.out.println("ALGORITMO GENÉTICO - EJECUCIÓN " + id + ":");
            System.out.print("\tIteracion " + i + "...");

            genetico.iniciar(id, i);

            printer1.csvWriteData(genetico, configuracion);

            System.out.println("Hecho!");
        }
    }

    private static AlgoritmoGenetico genetico1() {
        return new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CrucePositionBased(probabilidadCruce08),
                new MutacionIntercambio(probabilidadMutacion),
                new ReemplazoTorneoPHSinRepeticion());
    }
    private static AlgoritmoGenetico genetico2() {
        return new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CrucePositionBased(probabilidadCruce09),
                new MutacionIntercambio(probabilidadMutacion),
                new ReemplazoTorneoPHSinRepeticion());
    }

    private static AlgoritmoGenetico genetico3() {
        return new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CrucePositionBased(probabilidadCruce1),
                new MutacionIntercambio(probabilidadMutacion),
                new ReemplazoTorneoPHSinRepeticion());
    }

    private static AlgoritmoGenetico genetico4() {
        return new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CrucePositionBased(probabilidadCruce),
                new MutacionIntercambio(probabilidadMutacion),
                new ReemplazoTorneoPHSinRepeticion());
    }

}

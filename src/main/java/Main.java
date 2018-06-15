package main.java;

import main.java.busqueda.BusquedaIntercambioGrupo;
import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.algoritmos.creacion.CreacionAleatoria;
import main.java.genetico.algoritmos.cruce.CrucePositionBased;
import main.java.genetico.algoritmos.mutacion.MutacionIntercambio;
import main.java.genetico.algoritmos.reemplazo.ReemplazoTorneoPHSinRepeticion;
import main.java.genetico.algoritmos.seleccion.SeleccionParesAleatorios;
import main.java.util.writer.CSVWriter;
import main.java.util.writer.DatosGlobalesEjecuciones;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final int NUM_EJECUCIONES = 10; // TODO reestablecer valor a 10

    private static CSVWriter printer1 =
            new DatosGlobalesEjecuciones("files/DatosGlobalesEjecuciones.csv");

    //private static float probabilidadMutacion005=0.05f;
    //private static float probabilidadMutacion010=0.10f;
    //private static float probabilidadMutacion020=0.20f;

    public static void main(String args[]) {
        new File("files/ejecuciones/").mkdirs(); // crea la ruta en caso de no existi


        lanzarAlgoritmo("AP8_BusquedaLocal0", genetico1());
        lanzarAlgoritmo("AP8_BusquedaLocal025", genetico2());
        lanzarAlgoritmo("AP8_BusquedaLocal050", genetico3());
        lanzarAlgoritmo("AP8_BusquedaLocal075", genetico4());
        lanzarAlgoritmo("AP8_BusquedaLocal1", genetico5());

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
                new CrucePositionBased(),
                new MutacionIntercambio(),
                new ReemplazoTorneoPHSinRepeticion(),
                new BusquedaIntercambioGrupo(0));
    }

    private static AlgoritmoGenetico genetico2() {
        return new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CrucePositionBased(),
                new MutacionIntercambio(),
                new ReemplazoTorneoPHSinRepeticion(),
                new BusquedaIntercambioGrupo(0.25f));
    }

    private static AlgoritmoGenetico genetico3() {
        return new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CrucePositionBased(),
                new MutacionIntercambio(),
                new ReemplazoTorneoPHSinRepeticion(),
                new BusquedaIntercambioGrupo(0.50f));
    }

    private static AlgoritmoGenetico genetico4() {
        return new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CrucePositionBased(),
                new MutacionIntercambio(),
                new ReemplazoTorneoPHSinRepeticion(),
                new BusquedaIntercambioGrupo(0.75f));
    }

    private static AlgoritmoGenetico genetico5() {
        return new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CrucePositionBased(),
                new MutacionIntercambio(),
                new ReemplazoTorneoPHSinRepeticion(),
                new BusquedaIntercambioGrupo(1));
    }

}

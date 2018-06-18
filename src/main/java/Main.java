package main.java;

import main.java.busqueda.BusquedaIntercambioGrupo;
import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.algoritmos.creacion.CreacionAleatoria;
import main.java.genetico.algoritmos.cruce.CrucePositionBased;
import main.java.genetico.algoritmos.mutacion.MutacionIntercambio;
import main.java.genetico.algoritmos.reemplazo.ReemplazoTorneoPHSinRepeticion;
import main.java.genetico.algoritmos.seleccion.SeleccionParesAleatorios;

public class Main {

    private static final int NUM_EJECUCIONES = 1; // TODO reestablecer valor a 10

    private static float probabilidadMutacion005 = 0.05f;
    private static float probabilidadMutacion010 = 0.10f;
    private static float probabilidadMutacion020 = 0.20f;

    public static void main(String args[]) {

        AlgoritmoGenetico.open(NUM_EJECUCIONES);

        genetico1().lanzarAlgoritmo("MAIN_BusquedaLocal0");
        genetico2().lanzarAlgoritmo("MAIN_BusquedaLocal025");
        genetico3().lanzarAlgoritmo("MAIN_BusquedaLocal050");
        genetico4().lanzarAlgoritmo("MAIN_BusquedaLocal075");
        genetico5().lanzarAlgoritmo("MAIN_BusquedaLocal1");

        AlgoritmoGenetico.close();
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

    private static AlgoritmoGenetico configuracionOptima() {
        return new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CrucePositionBased(),
                new MutacionIntercambio(),
                new ReemplazoTorneoPHSinRepeticion(),
                new BusquedaIntercambioGrupo(1));
    }

}

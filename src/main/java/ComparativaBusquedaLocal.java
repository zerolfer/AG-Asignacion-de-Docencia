package main.java;

import main.java.busqueda.BusquedaIntercambioGrupo;
import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.algoritmos.creacion.CreacionAleatoria;
import main.java.genetico.algoritmos.cruce.CrucePositionBased;
import main.java.genetico.algoritmos.mutacion.MutacionIntercambio;
import main.java.genetico.algoritmos.reemplazo.ReemplazoTorneoPHSinRepeticion;
import main.java.genetico.algoritmos.seleccion.SeleccionParesAleatorios;

public class ComparativaBusquedaLocal {
    private static final int NUM_EJECUCIONES = 10;

    public static void main(String args[]) {

        AlgoritmoGenetico g1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CrucePositionBased(0.8f),
                new MutacionIntercambio(0.15f),
                new ReemplazoTorneoPHSinRepeticion(),
                new BusquedaIntercambioGrupo(0)
        );

        AlgoritmoGenetico g2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CrucePositionBased(0.8f),
                new MutacionIntercambio(0.15f),
                new ReemplazoTorneoPHSinRepeticion(),
                new BusquedaIntercambioGrupo(1)
        );

        AlgoritmoGenetico.open(NUM_EJECUCIONES);
        g1.lanzarAlgoritmo("Comparativa_SIN-BusquedaLocal_ViejoDecoder");
        g2.lanzarAlgoritmo("Comparativa_CON-BusquedaLocal_ViejoDecoder");
        AlgoritmoGenetico.close();
    }
}

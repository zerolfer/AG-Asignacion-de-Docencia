package main.java;

import main.java.busqueda.BusquedaIntercambioGrupo;
import main.java.busqueda.BusquedaLocal;
import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.operadores.creacion.CreacionAleatoria;
import main.java.genetico.operadores.cruce.CruceOrderBased;
import main.java.genetico.operadores.cruce.OperadorCruce;
import main.java.genetico.operadores.mutacion.MutacionIntercambio;
import main.java.genetico.operadores.mutacion.OperadorMutacion;
import main.java.genetico.operadores.reemplazo.OperadorReemplazo;
import main.java.genetico.operadores.reemplazo.ReemplazoTorneo;
import main.java.genetico.operadores.seleccion.OperadorSeleccion;
import main.java.genetico.operadores.seleccion.SeleccionTorneo;

public class ComparativaBusquedaLocal {
    private static final int NUM_EJECUCIONES = 10;

    private static final float pMutacion=0.30f;
    private static final float pCruce=0.7f;

    private static OperadorSeleccion selector = new SeleccionTorneo();
    private static OperadorReemplazo reemplazo = new ReemplazoTorneo(4);
    private static OperadorCruce cruce = new CruceOrderBased(pCruce);
    private static OperadorMutacion mutador = new MutacionIntercambio(pMutacion);
    private static BusquedaLocal busqueda = new BusquedaIntercambioGrupo(1);


    public static void main(String args[]) {

        AlgoritmoGenetico g1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                cruce,
                mutador,
                reemplazo,
                new BusquedaIntercambioGrupo(0)
        );

        AlgoritmoGenetico g2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                cruce,
                mutador,
                reemplazo,
                new BusquedaIntercambioGrupo(1)
        );

        AlgoritmoGenetico.open(NUM_EJECUCIONES);
//        g1.lanzarAlgoritmo("Comparativa_SIN-BusquedaLocal_NUEVODecoder_VERIFICACION");
        g2.lanzarAlgoritmo("Comparativa_CON-BusquedaLocal_NUEVODecoder_VERIFICACION");
        AlgoritmoGenetico.close();
    }
}

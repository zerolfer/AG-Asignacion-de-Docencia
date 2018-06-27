package main.java;

import main.java.busqueda.BusquedaIntercambioGrupo;
import main.java.busqueda.BusquedaLocal;
import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.operadores.creacion.CreacionAleatoria;
import main.java.genetico.operadores.cruce.CruceOrderBased;
import main.java.genetico.operadores.cruce.CrucePositionBased;
import main.java.genetico.operadores.cruce.OperadorCruce;
import main.java.genetico.operadores.mutacion.MutacionIntercambio;
import main.java.genetico.operadores.mutacion.MutacionInversion;
import main.java.genetico.operadores.mutacion.OperadorMutacion;
import main.java.genetico.operadores.reemplazo.*;
import main.java.genetico.operadores.seleccion.*;

public class AnalisisParametrico {
    private static final int NUM_EJECUCIONES = 10;

    private static final float pMutacion=0.30f;
    private static final float pCruce=0.7f;

    private static OperadorSeleccion selector = new SeleccionRuleta();
    private static OperadorReemplazo reemplazo = new ReemplazoTorneo(4);
    private static OperadorCruce cruce = new CruceOrderBased(pCruce);
    private static OperadorMutacion mutador = new MutacionIntercambio(pMutacion);
    private static BusquedaLocal busqueda = new BusquedaIntercambioGrupo(1);


    public static void main(String args[]) {

        AlgoritmoGenetico.open(NUM_EJECUCIONES);
        paso1();
        paso2();
//        paso3();
//        paso3yMedio();
//        paso4();
//        paso5();
//        paso6();
//        paso7();
//        paso8();
        AlgoritmoGenetico.close();
    }

    private static void paso1() {
        AlgoritmoGenetico g1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneo()
        );
        g1.lanzarAlgoritmo("AP1_ConfiguracionBase");
    }

    private static void paso2() {
        AlgoritmoGenetico g1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneo()
        );
        AlgoritmoGenetico g2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionRuletaAntiguo(),
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneo()
        );
        AlgoritmoGenetico g3 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionRuleta(),
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneo()
        );
        AlgoritmoGenetico g4 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionTorneo(),
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneo()
        );

        g1.lanzarAlgoritmo("AP2_seleccionParesAleatorios");
        g2.lanzarAlgoritmo("AP2_seleccionRuletaAntiguo");
        g3.lanzarAlgoritmo("AP2_seleccionRuleta");
        g4.lanzarAlgoritmo("AP2_seleccionTorneo");
    }

    private static void paso3() {
        AlgoritmoGenetico g1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                new ReemplazoGeneracional()
        );
        AlgoritmoGenetico g2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneo()
        );
        AlgoritmoGenetico g3 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneoPH()
        );
        AlgoritmoGenetico g4 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneoPHSinRepeticion()
        );
        g1.lanzarAlgoritmo("AP3_reemplazoGeneracional");
        g2.lanzarAlgoritmo("AP3_reemplazoTorneo");
        g3.lanzarAlgoritmo("AP3_reemplazoTorneoPH");
        g4.lanzarAlgoritmo("AP3_reemplazoTorneoPHSinRepeticion");
    }

    private static void paso3yMedio(){

        AlgoritmoGenetico g1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneo(4)
        );
        AlgoritmoGenetico g2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneo(2)
        );
        AlgoritmoGenetico g3 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneo(5)
        );

        g1.lanzarAlgoritmo("AP3.5_reemplazoTorneo_4");
        g2.lanzarAlgoritmo("AP3.5_reemplazoTorneo_2");
        g3.lanzarAlgoritmo("AP3.5_reemplazoTorneo_5");

    }

    private static void paso4() {
        AlgoritmoGenetico g1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                reemplazo
        );
        AlgoritmoGenetico g2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                new CrucePositionBased(0.7f),
                new MutacionIntercambio(0.3f),
                reemplazo
        );
        g1.lanzarAlgoritmo("AP4_cruceOBX");
        g2.lanzarAlgoritmo("AP4_crucePBX");
    }

    private static void paso5() {
        AlgoritmoGenetico g1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                new CruceOrderBased(0.8f),
                new MutacionIntercambio(0.3f),
                reemplazo
        );
        AlgoritmoGenetico g2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                new CruceOrderBased(0.9f),
                new MutacionIntercambio(0.3f),
                reemplazo
        );
        AlgoritmoGenetico g3 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                reemplazo
        );
        AlgoritmoGenetico g4 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                new CruceOrderBased(0.6f),
                new MutacionIntercambio(0.3f),
                reemplazo
        );
        g1.lanzarAlgoritmo("AP5_probabilidadCruce08");
        g2.lanzarAlgoritmo("AP5_probabilidadCruce09");
        g3.lanzarAlgoritmo("AP5_probabilidadCruce07");
        g4.lanzarAlgoritmo("AP5_probabilidadCruce06");
    }

    private static void paso6() {
        AlgoritmoGenetico g1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                cruce,
                new MutacionIntercambio(0.3f),
                reemplazo
        );
        AlgoritmoGenetico g2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                cruce,
                new MutacionInversion(0.3f),
                reemplazo
        );
        g1.lanzarAlgoritmo("AP6_mutacionIntercambio");
        g2.lanzarAlgoritmo("AP6_mutacionInversion");
    }

    private static void paso7() {
        AlgoritmoGenetico g1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                cruce,
                new MutacionIntercambio(0.05f),
                reemplazo
        );
        AlgoritmoGenetico g2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                cruce,
                new MutacionIntercambio(0.10f),
                reemplazo
        );
        AlgoritmoGenetico g3 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                cruce,
                new MutacionIntercambio(0.20f),
                reemplazo
        );
        AlgoritmoGenetico g4 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                cruce,
                new MutacionIntercambio(0.30f),
                reemplazo
        );
        AlgoritmoGenetico g5 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                cruce,
                new MutacionIntercambio(0.25f),
                reemplazo
        );
        AlgoritmoGenetico g6 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                cruce,
                new MutacionIntercambio(0.15f),
                reemplazo
        );
        AlgoritmoGenetico g7 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                cruce,
                new MutacionIntercambio(0.40f),
                reemplazo
        );
        g1.lanzarAlgoritmo("AP7_probabilidadMutacion005");
        g2.lanzarAlgoritmo("AP7_probabilidadMutacion010");
        g6.lanzarAlgoritmo("AP7_probabilidadMutacion015");
        g3.lanzarAlgoritmo("AP7_probabilidadMutacion020");
        g5.lanzarAlgoritmo("AP7_probabilidadMutacion025");
        g4.lanzarAlgoritmo("AP7_probabilidadMutacion030");
        g7.lanzarAlgoritmo("AP7_probabilidadMutacion040");
    }

    private static void paso8() {
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
                new BusquedaIntercambioGrupo(0.25f)
        );
        AlgoritmoGenetico g3 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                cruce,
                mutador,
                reemplazo,
                new BusquedaIntercambioGrupo(0.50f)
        );
        AlgoritmoGenetico g4 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                cruce,
                mutador,
                reemplazo,
                new BusquedaIntercambioGrupo(0.75f)
        );
        AlgoritmoGenetico g5 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                selector,
                cruce,
                mutador,
                reemplazo,
                new BusquedaIntercambioGrupo(1)
        );
        g1.lanzarAlgoritmo("AP8_BusquedaLocal0");
        g2.lanzarAlgoritmo("AP8_BusquedaLocal025");
        g3.lanzarAlgoritmo("AP8_BusquedaLocal050");
        g4.lanzarAlgoritmo("AP8_BusquedaLocal075");
        g5.lanzarAlgoritmo("AP8_BusquedaLocal1");
    }




























    //TODO: confirmar esto con los datos antiguos
    static AlgoritmoGenetico optimoDatos2017(){
        return new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CrucePositionBased(0.9f),
                new MutacionInversion(0.20f),
                new ReemplazoTorneoPHSinRepeticion(),
                new BusquedaIntercambioGrupo(1)
        );

    }
}

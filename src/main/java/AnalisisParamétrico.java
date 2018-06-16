package main.java;

import main.java.busqueda.BusquedaIntercambioGrupo;
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

public class AnalisisParamétrico {
    private static final int NUM_EJECUCIONES = 10; // TODO reestablecer valor a 10

    public static void main(String args[]) {

        AlgoritmoGenetico.open(NUM_EJECUCIONES);
        //paso1();
        //paso2();
        //paso3();
        //paso4();
        paso5();
        paso6();
        paso7();
        paso8();
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
                new SeleccionParesAleatorios(),
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                new ReemplazoGeneracional()
        );
        AlgoritmoGenetico g2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneo()
        );
        AlgoritmoGenetico g3 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneoPH()
        );
        AlgoritmoGenetico g4 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneoPHSinRepeticion()
        );
        g1.lanzarAlgoritmo("AP3_reemplazoGeneracional");
        g2.lanzarAlgoritmo("AP3_reemplazoTorneo");
        g3.lanzarAlgoritmo("AP3_reemplazoTorneoPH");
        g4.lanzarAlgoritmo("AP3_reemplazoTorneoPHSinRepeticion");
    }

    private static void paso4() {
        AlgoritmoGenetico g1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CruceOrderBased(0.7f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneoPHSinRepeticion()
        );
        AlgoritmoGenetico g2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CrucePositionBased(0.7f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneoPHSinRepeticion()
        );
        g1.lanzarAlgoritmo("AP4_cruceOBX");
        g2.lanzarAlgoritmo("AP4_crucePBX");
    }

    private static void paso5() {
        AlgoritmoGenetico g1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CruceOrderBased(0.8f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneoPHSinRepeticion()
        );
        AlgoritmoGenetico g2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CrucePositionBased(0.9f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneoPHSinRepeticion()
        );
        AlgoritmoGenetico g3 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CrucePositionBased(1),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneoPHSinRepeticion()
        );
        g1.lanzarAlgoritmo("AP5_probabilidadCruce08");
        g2.lanzarAlgoritmo("AP5_probabilidadCruce09");
        g3.lanzarAlgoritmo("AP5_probabilidadCruce1");
    }

    private static void paso6() {
        AlgoritmoGenetico g1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CruceOrderBased(0.8f),
                new MutacionIntercambio(0.3f),
                new ReemplazoTorneoPHSinRepeticion()
        );
        AlgoritmoGenetico g2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CrucePositionBased(0.8f),
                new MutacionInversion(0.3f),
                new ReemplazoTorneoPHSinRepeticion()
        );
        g1.lanzarAlgoritmo("AP6_mutacionIntercambio");
        g2.lanzarAlgoritmo("AP6_mutacionInversion");
    }

    private static void paso7() {
        AlgoritmoGenetico g1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CruceOrderBased(0.8f),
                new MutacionIntercambio(0.05f),
                new ReemplazoTorneoPHSinRepeticion()
        );
        AlgoritmoGenetico g2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CrucePositionBased(0.8f),
                new MutacionIntercambio(0.10f),
                new ReemplazoTorneoPHSinRepeticion()
        );
        AlgoritmoGenetico g3 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CrucePositionBased(0.8f),
                new MutacionIntercambio(0.20f),
                new ReemplazoTorneoPHSinRepeticion()
        );
        g1.lanzarAlgoritmo("AP7_probabilidadMutacion");
        g2.lanzarAlgoritmo("AP7_probabilidadMutacion");
        g3.lanzarAlgoritmo("AP7_probabilidadMutacion");
    }

    private static void paso8() {
        AlgoritmoGenetico g1 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CruceOrderBased(0.8f),
                new MutacionIntercambio(0.1f),
                new ReemplazoTorneoPHSinRepeticion(),
                new BusquedaIntercambioGrupo(0)
        );
        AlgoritmoGenetico g2 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CruceOrderBased(0.8f),
                new MutacionIntercambio(0.1f),
                new ReemplazoTorneoPHSinRepeticion(),
                new BusquedaIntercambioGrupo(0.25f)
        );
        AlgoritmoGenetico g3 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CruceOrderBased(0.8f),
                new MutacionIntercambio(0.1f),
                new ReemplazoTorneoPHSinRepeticion(),
                new BusquedaIntercambioGrupo(0.50f)
        );
        AlgoritmoGenetico g4 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CruceOrderBased(0.8f),
                new MutacionIntercambio(0.1f),
                new ReemplazoTorneoPHSinRepeticion(),
                new BusquedaIntercambioGrupo(0.75f)
        );
        AlgoritmoGenetico g5 = new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionParesAleatorios(),
                new CruceOrderBased(0.8f),
                new MutacionIntercambio(0.1f),
                new ReemplazoTorneoPHSinRepeticion(),
                new BusquedaIntercambioGrupo(1)
        );
        g1.lanzarAlgoritmo("AP8_BusquedaLocal0");
        g2.lanzarAlgoritmo("AP8_BusquedaLocal025");
        g3.lanzarAlgoritmo("AP8_BusquedaLocal050");
        g4.lanzarAlgoritmo("AP8_BusquedaLocal075");
        g5.lanzarAlgoritmo("AP8_BusquedaLocal1");
    }
}

package main.java.util.writer;

import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.Individuo;
import main.java.util.RandomManager;

import java.util.ArrayList;
import java.util.List;

public class DatosGlobalesEjecuciones extends AbstractCSVWriter {
    boolean printCabeceras = true; // NOTE: true para imprimer cabeceras de tabla

    public DatosGlobalesEjecuciones(String path) {
        super(path,true); //NOTE: poner true para no reescribir fichero
        if (printCabeceras) {
            super.csvWriteData("ejecucion"
                    + SPLITTER + "poblacion"
                    + SPLITTER + "pr. cruce"
                    + SPLITTER + "pr. mutacion"
                    + SPLITTER + "num generaciones"
                    + SPLITTER + "creacion"
                    + SPLITTER + "seleccion"
                    + SPLITTER + "cruce"
                    + SPLITTER + "mutacion"
                    + SPLITTER + "reemplazo"
                    + SPLITTER + "fitness1"
                    + SPLITTER + "fitness2"
                    + SPLITTER + "tiempo (ms)"
                    + SPLITTER + "semilla"
            );
            super.csvWriteNewLine();
            printCabeceras=false;
        }

    }

    @Override
    public void csvWriteData(AlgoritmoGenetico genetico, List<String> otros) {
        Individuo resultado = genetico.getMejorIndividuo();
        List<String> especifico = new ArrayList<>();
        especifico.add(Integer.toString(resultado.getFitnessAsigProfesor()));
        especifico.add(Float.toString(resultado.getFitnessNumHoras()));
        especifico.add(genetico.getTimer().getFinalTime().toString());
        especifico.add(Long.toString(RandomManager.seed));
        super.csvWriteDataCollection(otros, especifico);
    }
}

package main.java.util.writer;

import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.Individuo;
import main.java.util.RandomManager;

import java.util.ArrayList;
import java.util.List;

public class DatosGlobalesEjecuciones extends AbstractCSVWriter {

    public DatosGlobalesEjecuciones(String path) {
        super(path);
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
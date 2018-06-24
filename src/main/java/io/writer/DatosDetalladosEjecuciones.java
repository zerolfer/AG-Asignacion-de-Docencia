package main.java.io.writer;

import main.java.genetico.AlgoritmoGenetico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatosDetalladosEjecuciones extends AbstractCSVWriter {
    public DatosDetalladosEjecuciones(String s,String[] algoritmos) {
        super(s, false);

        //super.csvWriteDataCollection(Arrays.asList(operadores));

        List<String> cabeceras = new ArrayList<>();
        cabeceras.add("Generacion");
        cabeceras.add("tiempo (ms)");
        cabeceras.add("fitness1");
        cabeceras.add("fitness2");
        cabeceras.add("fitness1 avg");
        cabeceras.add("fitness2 avg");
        cabeceras.addAll(Arrays.asList(algoritmos));

        super.csvWriteDataCollection(cabeceras);
    }

    @Override
    public void csvWriteData(AlgoritmoGenetico genetico, List<String> otros) {
        super.csvWriteDataCollection(otros);
    }
}

package main.java.util.writer;

import main.java.genetico.AlgoritmoGenetico;

import java.util.ArrayList;
import java.util.List;

public class DatosDetalladosEjecuciones extends AbstractCSVWriter {
    public DatosDetalladosEjecuciones(String s) {
        super(s, false);

        List<String> cabeceras = new ArrayList<>();
        cabeceras.add("Generacion");
        cabeceras.add("tiempo");
        cabeceras.add("fitness1");
        cabeceras.add("fitness2");
        cabeceras.add("fitness1 avg");
        cabeceras.add("fitness2 avg");

        super.csvWriteDataCollection(cabeceras);
    }

    @Override
    public void csvWriteData(AlgoritmoGenetico genetico, List<String> otros) {
        super.csvWriteDataCollection(otros);
    }
}

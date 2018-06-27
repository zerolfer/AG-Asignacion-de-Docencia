package main.java.io.writer;

import main.java.genetico.AlgoritmoGenetico;
import main.java.io.Settings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractCSVWriter implements CSVWriter {
    final String SPLITTER = Settings.get("csv.splitter");
    final String LINE_SEP = System.getProperty("line.separator");

    private BufferedWriter br;

    AbstractCSVWriter(String path, boolean append) {
        try {
            br = new BufferedWriter(new FileWriter(path, append));
        } catch (IOException e) {
            new File(Settings.get("path.output.detalles")).mkdirs(); // crea la ruta en caso de no existir
            try {
                br = new BufferedWriter(new FileWriter(path, append));
            } catch (IOException e1) {
                e1.printStackTrace();
                System.exit(1);
            }
        }

    }

//    public AbstractCSVWriter(String path) {
//        this(path, true);
//    }

    @SafeVarargs
    final void csvWriteDataCollection(List<String>... dataColection) {
        for (List<String> data : dataColection) {
            for (String d : data) {
                csvWriteData(d);
            }
        }
        csvWriteNewLine();
        csvWriteFlush();

    }
/*
    public void csvWriteDataCollection(List<String> data) {
        for (String d : data) {
            csvWriteData(d);
        }
        csvWriteNewLine();
        csvWriteFlush();
    }
*/
    public void csvWriteData(AlgoritmoGenetico genetico, String... otros) {
        csvWriteData(genetico, Arrays.asList(otros));
    }


    void csvWriteData(String data) {
        try {
            br.append(data);
            br.append(SPLITTER);
            // br.flush(); //NOTE for debug
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void csvWriteNewLine() {
        try {
            br.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void csvWriteFlush() {
        try {
            br.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (br != null) {
                br.newLine();
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void csvWriteData(AlgoritmoGenetico genetico){
        throw new RuntimeException("Not implemented method \"csvWriteData(AlgoritmoGenetico genetico)\" at "+
        getClass().getSimpleName());
    }
}

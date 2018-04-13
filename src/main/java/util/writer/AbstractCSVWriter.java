package main.java.util.writer;

import main.java.genetico.AlgoritmoGenetico;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractCSVWriter implements CSVWriter {
    public final char SPLITTER = ';';
    BufferedWriter br;

    public AbstractCSVWriter(String path, boolean append) {
        try {
            br = new BufferedWriter(new FileWriter(path, append));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    public AbstractCSVWriter(String path) {
        this(path, true);
    }

    public void csvWriteDataCollection(List<String>... dataColection) {
        for (List<String> data : dataColection) {
            for (String d : data) {
                csvWriteData(d);
            }
        }
        csvWriteNewLine();
        csvWriteFlush();

    }

    public void csvWriteDataCollection(List<String> data) {
        for (String d : data) {
            csvWriteData(d);
        }
        csvWriteNewLine();
        csvWriteFlush();
    }

    public void csvWriteData(AlgoritmoGenetico genetico, String... otros) {
        csvWriteData(genetico, Arrays.asList(otros));
    }


    public void csvWriteData(String data) {
        try {
            br.append(data.toString());
            br.append(SPLITTER);
            //  br.flush(); //TODO for debug
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
            br.newLine();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

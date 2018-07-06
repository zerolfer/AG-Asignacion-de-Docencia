package main.java.io.writer;

import main.java.genetico.AlgoritmoGenetico;

import java.util.List;

public interface CSVWriter {

    void csvWriteData(AlgoritmoGenetico data, List<String> otros);
    void csvWriteData(AlgoritmoGenetico genetico, String... otros);

    void csvWriteNewLine();

    void csvWriterFlush();

    void close();

}

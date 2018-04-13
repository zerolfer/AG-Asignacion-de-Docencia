package main.java.util.writer;

import main.java.genetico.AlgoritmoGenetico;

import java.util.List;

public interface CSVWriter {

    void csvWriteData(AlgoritmoGenetico data, List<String> otros);

    void csvWriteNewLine();

    void csvWriteFlush();

    void close();
}

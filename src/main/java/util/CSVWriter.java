package main.java.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVWriter {
    public final char SPLITTER = ';';
    BufferedWriter br;

    public CSVWriter(String path) {
        try {
            br = new BufferedWriter(new FileWriter(path, true));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void CsvWriteDataCollection(List<String>... dataColection) {
        for (List<String> data : dataColection) {
            for (String d : data) {
                CsvWriteData(d);
            }
        }
        CsvWriteNewLine();
        CsvWriteFlush();

    }

    public void CsvWriteDataCollection(List<String> data) {
        for (String d : data) {
            CsvWriteData(d);
        }
        CsvWriteNewLine();
        CsvWriteFlush();

    }

    private void CsvWriteData(String data) {
        try {
            br.append(data.toString());
            br.append(SPLITTER);
            br.flush(); //TODO delete me
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void CsvWriteNewLine() {
        try {
            br.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void CsvWriteFlush() {
        try {
            br.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            br.newLine();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

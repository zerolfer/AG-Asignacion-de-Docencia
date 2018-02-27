import model.Profesor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private static String splitter = ";";

    public static List<Profesor> CsvLoadProfesores(String fileName) {
        BufferedReader br = null;
        String line = "";
        List<Profesor> l = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader("file/" +fileName));

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(splitter);

                System.out.println("Profesor [nombre= " + country[0] + " , apellidos=" + country[1] + "]");
                l.add(new Profesor(country[0], country[0]));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
            return l;
    }
}

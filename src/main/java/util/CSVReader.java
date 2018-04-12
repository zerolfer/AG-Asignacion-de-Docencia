package main.java.util;

import main.java.model.GrupoAsignatura;
import main.java.model.Horario;
import main.java.model.Profesor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CSVReader {
    private static final String SPLITTER = ";";
    private static final String AREA_SPLITTER = "/";

    private static final String PROFESORES_PATH = "files/Profesores.csv";
    private static final String ASIGNATURAS_PATH = "files/Asignaturas.csv";

    private static final boolean debug = false;

    public static List<Profesor> CsvLoadProfesores() {
        BufferedReader br = null;
        String line = "";
        List<Profesor> profesores = new ArrayList<>();
        try {

            br = new BufferedReader(new FileReader(PROFESORES_PATH));
            br.readLine(); // la primera linea es la cabecera, se salta

            while ((line = br.readLine()) != null) {
                try {
                    // use comma as separator
                    String[] split = line.split(SPLITTER);
                    profesores.add(
                            new Profesor(
                                    split[0],
                                    Float.parseFloat(split[1]),
                                    parseBoolean(split[2]),
                                    split[3]
                            )
                    );
                } catch (ClassCastException e) {
                    e.printStackTrace();
                }
            }
            if (debug) for (Profesor p : profesores) System.out.println(p);

            return profesores;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return profesores;
    }

    private static Boolean parseBoolean(String s) {
        switch (s) {
            case "SI":
                return true;
            case "NO":
                return false;
            default:
                throw new ClassCastException("La fila no contenía valor SI/NO");

        }
    }

    public static List<GrupoAsignatura> CsvLoadAsignaturas() {
        BufferedReader br = null;
        String line = "";
        List<GrupoAsignatura> asignaturas = new ArrayList<>();
        try {

            br = new BufferedReader(new FileReader(ASIGNATURAS_PATH));
            br.readLine(); // la primera linea es la cabecera, se salta

            while ((line = br.readLine()) != null) {

                String[] split = line.split(SPLITTER);
                try {
                    asignaturas.add(
                            new GrupoAsignatura(
                                    split[0],
                                    split[1],
                                    Integer.parseInt(split[2]),
                                    horarioProvider(split[3]), // tipo horario
                                    split[4],
                                    split[5],
                                    Float.parseFloat(split[6]),
                                    parseBoolean(split[7]),
                                    split[8].split(AREA_SPLITTER)
                            )
                    );
                } catch (ClassCastException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (debug) for (GrupoAsignatura a : asignaturas) System.out.println(a);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return asignaturas;
    }

    private static Horario horarioProvider(String string) throws ParseException {
        String[] diaIntervalo = string.split(" ");
        char dia = diaIntervalo[0].charAt(0);
        String[] horas = diaIntervalo[1].split("-");

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        Date parsedDate1 = dateFormat.parse(horas[0]);
        Date parsedDate2 = dateFormat.parse(horas[1]);
        Timestamp timestamp1 = new java.sql.Timestamp(parsedDate1.getTime());
        Timestamp timestamp2 = new java.sql.Timestamp(parsedDate2.getTime());

        return new Horario(dia, timestamp1, timestamp2);

    }
}

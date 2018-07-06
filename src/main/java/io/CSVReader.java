package main.java.io;

import main.java.model.Grupo;
import main.java.model.Horario;
import main.java.model.Profesor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CSVReader {
    private static final String SPLITTER = Settings.get("csv.splitter");
    private static final String AREA_SPLITTER = Settings.get("csv.area.spliter");
    private static final String HORARIO_SPLITTER = Settings.get("csv.horario.splitter");

    private static final String PROFESORES_PATH = Settings.get("path.profesores");
    private static final String ASIGNATURAS_PATH = Settings.get("path.asignaturas");

    private static final boolean debug = false;
    private static final String DIA_SPLITER = Settings.get("csv.dia.spliter");

    public static List<Profesor> csvLoadProfesores() {
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
                    Horario disponibilidad = DisponibilidadProvider(
                            split.length >= 5 ? split[4] : "Ilimitada"
                    );
                    profesores.add(
                            new Profesor(
                                    split[0],
                                    Float.parseFloat(split[1]),
                                    parseBoolean(split[2]),
                                    split[3],
                                    disponibilidad
                            )
                    );
                } catch (ClassCastException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
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

    private static Horario DisponibilidadProvider(String string) throws ParseException {

        String[] horas = string.split(HORARIO_SPLITTER);

        /* en caso de que el profesor no sea asociado, la columna podra ser vacia,
         * con la palabra total o la palabra ilimitada y creará un intervalo
         * de 24 horas para ese profesor a tiempo completo
         */
        if (horas.length == 0 || horas[0].equalsIgnoreCase("Total") ||
                horas[0].equalsIgnoreCase("Ilimitada")) {
            return Horario.disponibilidadTotal;
        }
        Date parsedDate1 = Horario.horaFormat.parse(horas[0]);
        Date parsedDate2 = Horario.horaFormat.parse(horas[1]);
        Timestamp timestamp1 = new java.sql.Timestamp(parsedDate1.getTime());
        Timestamp timestamp2 = new java.sql.Timestamp(parsedDate2.getTime());

//        if(timestamp1.after(timestamp2)) throw new RuntimeException("Las hora no se corresponden");
        return new Horario(timestamp1, timestamp2);


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

    public static List<Grupo> CsvLoadAsignaturas() {
        BufferedReader br = null;
        String line = "";
        List<Grupo> asignaturas = new ArrayList<>();
        try {

            br = new BufferedReader(new FileReader(ASIGNATURAS_PATH));
            br.readLine(); // la primera linea es la cabecera, se salta

            while ((line = br.readLine()) != null) {

                String[] split = line.split(SPLITTER);
                try {
                    List<Horario> horarios = crearListaHorarios(split);
                    asignaturas.add(
                            new Grupo(
                                    split[0],
                                    split[1],
                                    split[2],
                                    Integer.parseInt(split[3]),
                                    horarios,
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
            if (debug) for (Grupo a : asignaturas) System.out.println(a);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return asignaturas;
    }

    private static List<Horario> crearListaHorarios(String[] split) throws ParseException {
        List<Horario> result = new ArrayList<>();
        for (int i = 9; i < split.length; i++) {
            result.add(horarioProvider(split[i]));
        }
        return result;
    }

    private static Horario horarioProvider(String string) throws ParseException {
        String[] diaIntervalo = string.split(DIA_SPLITER);
        char dia = diaIntervalo[0].charAt(0);
        String[] horas = diaIntervalo[1].split(HORARIO_SPLITTER);

        Date parsedDate1 = Horario.horaFormat.parse(horas[0]);
        Date parsedDate2 = Horario.horaFormat.parse(horas[1]);
        Timestamp timestamp1 = new java.sql.Timestamp(parsedDate1.getTime());
        Timestamp timestamp2 = new java.sql.Timestamp(parsedDate2.getTime());

        return new Horario(dia, timestamp1, timestamp2);

    }
}
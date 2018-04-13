package main.java.model;

import main.java.util.CSVReader;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Sergio Florez on 24/03/2018.
 */
public class BD {

    public static final Comparator<Profesor> comparatorProfesores = new Comparator<Profesor>() {
        @Override
        public int compare(Profesor o1, Profesor o2) {
            if (o1.getBilingue() == true && o2.getBilingue() == false)
                return 1;
            if (o1.getBilingue() == false && o2.getBilingue() == true)
                return -1;
            else return 0;
        }
    };
    ;
    public static final Comparator<GrupoAsignatura> comparatorAsignaturas = new Comparator<GrupoAsignatura>() {
        @Override
        public int compare(GrupoAsignatura o1, GrupoAsignatura o2) {
            if (o1.getBilingue() == true && o2.getBilingue() == false)
                return -1;
            if (o1.getBilingue() == false && o2.getBilingue() == true)
                return 1;
            else return 0;
        }
    };
    private static final List<Profesor> profesores = CSVReader.CsvLoadProfesores();
    private static final List<GrupoAsignatura> asignaturas = CSVReader.CsvLoadAsignaturas();

    public static List<Profesor> getProfesores() {
        return profesores;
    }

    public static List<GrupoAsignatura> getAsignaturas() {
        return asignaturas;
    }

    public static Profesor getProfesorById(int id) {
        for (Profesor profe : profesores) {
            if (profe.getId() == id)
                return profe;
        }
        return null;
    }

    public static GrupoAsignatura getGrupoById(int id) {
        for (GrupoAsignatura grupo : asignaturas) {
            if (grupo.getId() == id)
                return grupo;
        }
        return null;
    }


}

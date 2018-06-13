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

    public static final Comparator<Grupo> comparatorGrupos = new Comparator<Grupo>() {
        @Override
        public int compare(Grupo o1, Grupo o2) {
            if (o1.getBilingue() == true && o2.getBilingue() == false)
                return -1;
            if (o1.getBilingue() == false && o2.getBilingue() == true)
                return 1;
            else return 0;
        }
    };

    private static final List<Profesor> profesores = CSVReader.CsvLoadProfesores();
    private static final List<Grupo> asignaturas = CSVReader.CsvLoadAsignaturas();

    public static List<Profesor> getProfesores() {
        return profesores;
    }

    public static List<Grupo> getAsignaturas() {
        return asignaturas;
    }

    public static Profesor getProfesorById(int id) {
        for (Profesor profe : profesores) {
            if (profe.getId() == id)
                return profe;
        }
        return null;
    }

    public static Grupo getGrupoById(int id) {
        for (Grupo grupo : asignaturas) {
            if (grupo.getId() == id)
                return grupo;
        }
        return null;
    }


}

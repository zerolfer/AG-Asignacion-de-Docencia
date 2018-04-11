package main.java.model;

import main.java.util.CSVReader;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Sergio Florez on 24/03/2018.
 */
public class BD {

    private static List<Profesor> profesores;
    private static List<GrupoAsignatura> asignaturas;

    private static void inicializar(){
        profesores = CSVReader.CsvLoadProfesores();
        asignaturas = CSVReader.CsvLoadAsignaturas();
    }

    public static List<Profesor> getProfesores(){
        if(profesores==null)
            profesores = CSVReader.CsvLoadProfesores();
        return profesores;
    }

    public static List<GrupoAsignatura> getAsignaturas(){
        if(asignaturas==null)
            asignaturas = CSVReader.CsvLoadAsignaturas();
        return asignaturas;
    }

    public static Profesor getProfesorById(int id) {
        if(profesores==null&&asignaturas==null) inicializar();
        for (Profesor profe : profesores) {
            if (profe.getId() == id)
                return profe;
        }
        return null;
    }

    public static GrupoAsignatura getGrupoById(int id) {
        if(profesores==null&&asignaturas==null) inicializar();
        for (GrupoAsignatura grupo : asignaturas) {
            if (grupo.getId() == id)
                return grupo;
        }
        return null;
    }

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


}

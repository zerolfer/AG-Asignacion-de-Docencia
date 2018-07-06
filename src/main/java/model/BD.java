package main.java.model;

import main.java.io.CSVReader;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Sergio Florez on 24/03/2018.
 */
public class BD {

    private static BD instance;

    private BD(){
        profesores = CSVReader.csvLoadProfesores();
        asignaturas = CSVReader.CsvLoadAsignaturas();

        comparatorProfesores = new Comparator<Profesor>() {
            @Override
            public int compare(Profesor o1, Profesor o2) {
                if (o1.getBilingue() == true && o2.getBilingue() == false)
                    return 1;
                if (o1.getBilingue() == false && o2.getBilingue() == true)
                    return -1;
                else return 0;
            }
        };

        comparatorGrupos = new Comparator<Grupo>() {
            @Override
            public int compare(Grupo o1, Grupo o2) {
                if (o1.getBilingue() == true && o2.getBilingue() == false)
                    return -1;
                if (o1.getBilingue() == false && o2.getBilingue() == true)
                    return 1;
                else return 0;
            }
        };
    }

    private static BD getInstance() {
        if (instance == null) {
            instance = new BD();
        }
        return instance;
    }


    private static Comparator<Profesor> comparatorProfesores;
    private static Comparator<Grupo> comparatorGrupos;

    private static List<Profesor> profesores;
    private static List<Grupo> asignaturas;

    public static Comparator<Profesor> getComparatorProfesores() {
        return getInstance().comparatorProfesores;
    }

    public static Comparator<Grupo> getComparatorGrupos() {
        return getInstance().comparatorGrupos;
    }


    public static List<Profesor> getProfesores() {
        return getInstance().profesores;
    }

    public static List<Grupo> getGrupos() {
        return getInstance().asignaturas;
    }

    public static Profesor getProfesorById(int id) {
        for (Profesor profe : getInstance().profesores) {
            if (profe.getId() == id)
                return profe;
        }
        return null;
    }

    public static Grupo getGrupoById(int id) {
        for (Grupo grupo : getInstance().asignaturas) {
            if (grupo.getId() == id)
                return grupo;
        }
        return null;
    }


}

package main.java;

import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;

import java.util.List;

public class Main {
    public static void main(String args[]){
        List<Profesor> p = CSVReader.CsvLoadProfesores();
        List<GrupoAsignatura> a = CSVReader.CsvLoadAsignaturas();
    }
}

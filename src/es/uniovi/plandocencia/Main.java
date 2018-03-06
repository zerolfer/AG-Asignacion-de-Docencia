package es.uniovi.plandocencia;

import es.uniovi.plandocencia.model.GrupoAsignatura;
import es.uniovi.plandocencia.model.Profesor;

import java.util.List;

public class Main {
    public static void main(String args[]){
        List<Profesor> p = CSVReader.CsvLoadProfesores();
        List<GrupoAsignatura> a = CSVReader.CsvLoadAsignaturas();
    }
}

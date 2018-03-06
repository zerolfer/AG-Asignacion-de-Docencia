package es.uniovi.plandocencia;

import es.uniovi.plandocencia.model.GrupoAsignatura;
import es.uniovi.plandocencia.model.Profesor;
import es.uniovi.plandocencia.util.CromosomaUtils;
import es.uniovi.plandocencia.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlgoritmoGenetico {

    // VARIABLES DE ENTRADA DEL GENÉTICO
    private static final int POPULATION_SIZE = 1;

    // INFORMACIÓN DEL PROBLEMA
    private static List<GrupoAsignatura> asignaturas = new ArrayList<>();
    private static List<Profesor> profesores = new ArrayList<>();

    public static void main(String[] args) {
        iniciacilizacion();

        for (Individuo c : codificacion()) {
            AlgoritmoGenetico.decodificar(c);
        }
    }

    private static void iniciacilizacion() {
        asignaturas = CSVReader.CsvLoadAsignaturas();
        profesores = CSVReader.CsvLoadProfesores();
    }

    private static Individuo[] codificacion() {
        return CromosomaUtils.generateRandomPopulation(POPULATION_SIZE);
    }

    private static void decodificar(Individuo cromosoma) {// O(n^2)
        System.out.println(cromosoma);
        int n=0;
        for (int idAsignatura : cromosoma.getCromosoma()) {
            // las asignaturas no tienen por que estar ordenadas por ID
            GrupoAsignatura asignatura = getAsignaturaById(idAsignatura);
            Profesor profesor = getProfesor(asignatura);
            if(profesor==null) {
                n++;
                continue;
            }
            profesor.getAsignadas().add(asignatura);
            profesor.setCapacidad(profesor.getCapacidad()-asignatura.getHoras());
        }
        System.out.println(Util.arrayToString(profesores, "\n"));
        System.out.println(n+" asignaturas no han sido sasignadas");
    }

    private static Profesor getProfesor(GrupoAsignatura a) {
        for (Profesor p : profesores) {
            if (p.getCapacidad() >= a.getHoras() && Arrays.binarySearch(a.getAreas(), p.getArea()) != -1)
                if (p.getBilingue() || !a.getBilingue())
                    return p;
        }
        return null; //FIXME ??? si no hay profesores a cubrir qué hacer?¿
    }

    private static GrupoAsignatura getAsignaturaById(int idAsignatura) {
        for (GrupoAsignatura a : asignaturas) {
            if (a.getId() == idAsignatura)
                return a;
        }
        throw new RuntimeException("No existe ID de asignatura " + idAsignatura);
    }


}

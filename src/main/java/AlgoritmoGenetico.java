package main.java;


import main.java.algoritmos.CreacionAleatoria;
import main.java.algoritmos.Decodificacion;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;

import java.util.ArrayList;
import java.util.List;

public class AlgoritmoGenetico {

    // VARIABLES DE ENTRADA DEL GENÉTICO
    private static final int POPULATION_SIZE = 2;

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

    /**
     * @return la población inicial codificada
     */
    private static Individuo[] codificacion() {
        return new CreacionAleatoria().createPopulation(POPULATION_SIZE);
    }

    public static void decodificar(Individuo individuo) {// O(n^2)
        new Decodificacion().aplicar(individuo,
                profesores.toArray(new Profesor[profesores.size()]),
                asignaturas.toArray(new GrupoAsignatura[asignaturas.size()]));
//        System.out.println(individuo);
//        int n = 0;
//        for (int idAsignatura : individuo.getCromosoma()) {
//            // las asignaturas no tienen por que estar ordenadas por ID
//            GrupoAsignatura asignatura = getAsignaturaById(idAsignatura);
//            Profesor profesor = getProfesor(asignatura);
//            if (profesor == null) {
//                n++;
//                continue;
//            }
//            profesor.getAsignadas().add(asignatura);
//            profesor.setCapacidad(profesor.getCapacidad() - asignatura.getHoras());
//        }
//        individuo.asignarFitness(profesores);
//        System.out.println(Util.arrayToString(profesores, "\n"));
//        System.out.println(n + " asignaturas no han sido sasignadas");
//        System.out.println(individuo.fitnessToString());
    }

//    private static Profesor getProfesor(GrupoAsignatura a) {
//        for (Profesor p : profesores) {
//            if (p.getCapacidad() >= a.getHoras() && Arrays.binarySearch(a.getAreas(), p.getArea()) != -1)
//                if (p.getBilingue() || !a.getBilingue())
//                    return p;
//        }
//        return null; //FIXME ??? si no hay profesores a cubrir qué hacer?¿
//    }
//
//    private static GrupoAsignatura getAsignaturaById(int idAsignatura) {
//        for (GrupoAsignatura a : asignaturas) {
//            if (a.getId() == idAsignatura)
//                return a;
//        }
//        throw new RuntimeException("No existe ID de asignatura " + idAsignatura);
//    }


}

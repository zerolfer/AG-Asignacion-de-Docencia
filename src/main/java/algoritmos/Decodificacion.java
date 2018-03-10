package main.java.algoritmos;

import main.java.Individuo;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;
import main.java.util.Util;

import java.util.Arrays;

/**
 * Created by Sergio Florez on 09/03/2018.
 */
public class Decodificacion implements AlgoritmoDecodificacion {

    Profesor[] profesores;
    GrupoAsignatura[] asignaturas;

    boolean debug = true;

    @Override
    public void aplicar(Individuo individuo, Profesor[] profesores, GrupoAsignatura[] asignaturas) {

        this.profesores = Util.copyOf(profesores);
        this.asignaturas = Util.copyOf(asignaturas);

        assert profesores != this.profesores;
        assert  profesores[0]!=this.profesores[0];
        if (debug) System.out.println(individuo);
        int n = 0;
        for (int idAsignatura : individuo.getCromosoma()) {
            // las asignaturas no tienen por que estar ordenadas por ID
            GrupoAsignatura asignatura = getAsignaturaById(idAsignatura);
            Profesor profesor = getProfesor(asignatura);
            if (profesor == null) {
                n++;
                continue;
            }
            profesor.getAsignadas().add(asignatura);
            profesor.setCapacidad(profesor.getCapacidad() - asignatura.getHoras());
        }
        individuo.asignarFitness(this.profesores);
        if (debug) {
            System.out.println(Util.arrayToString(this.profesores, "\n"));
            System.out.println(n + " asignaturas no han sido sasignadas");
            System.out.println(individuo.fitnessToString());
        }
    }

    private Profesor getProfesor(GrupoAsignatura a) {
        for (Profesor p : this.profesores) {
            if (p.getCapacidad() >= a.getHoras() && Arrays.binarySearch(a.getAreas(), p.getArea()) != -1)
                if (p.getBilingue() || !a.getBilingue())
                    return p;
        }
        return null; //FIXME ??? si no hay profesores a cubrir qué hacer?¿
    }

    private GrupoAsignatura getAsignaturaById(int idAsignatura) {
        for (GrupoAsignatura a : this.asignaturas) {
            if (a.getId() == idAsignatura)
                return a;
        }
        throw new RuntimeException("No existe ID de asignatura " + idAsignatura);
    }

}

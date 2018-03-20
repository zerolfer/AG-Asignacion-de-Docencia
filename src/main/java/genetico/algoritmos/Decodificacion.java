package main.java.genetico.algoritmos;

import main.java.genetico.Individuo;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;
import main.java.util.Util;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sergio Florez on 09/03/2018.
 */
public class Decodificacion implements AlgoritmoDecodificacion {

    Profesor[] profesores;
    GrupoAsignatura[] asignaturas;

    boolean debug = false;

    @Override
    public void aplicar(Individuo individuo, Profesor[] profesores, GrupoAsignatura[] asignaturas) {

        this.profesores = Util.copyOf(profesores);
        this.asignaturas = Util.copyOf(asignaturas);

        assert profesores != this.profesores;
        assert profesores[0] != this.profesores[0];
        if (debug) System.out.println(individuo);
        int noAsignadas = 0;
        for (int idAsignatura : individuo.getCromosoma()) {
            // las asignaturas no tienen por que estar ordenadas por ID
            GrupoAsignatura asignatura = getAsignaturaById(idAsignatura);
            Profesor profesor = getProfesor(asignatura);
            if (profesor == null) {
                noAsignadas++;
                continue;
            }
            profesor.getAsignadas().add(asignatura);
            profesor.setCapacidad(profesor.getCapacidad() - asignatura.getHoras());
        }
        individuo.asignarFitness(noAsignadas, this.profesores, this.asignaturas);
//        individuo.setFenotipo(profesores);
        if (debug) {
            System.out.println(Util.arrayToString(this.profesores, "\n"));
            System.out.println(noAsignadas + " asignaturas no han sido sasignadas");
            System.out.println(individuo.fitnessToString());
        }
    }

    private Profesor getProfesor(GrupoAsignatura a) {
        for (Profesor p : this.profesores) {
            if (checkCapacidad(a, p) && checkBilingue(a, p) && checkArea(p, a))
                if (checkSolapamiento(p, a))
                    return p;
        }
        return null; //FIXME ??? si no hay profesores a cubrir qué hacer?¿
    }

    private boolean checkArea(Profesor p, GrupoAsignatura a) {
        return Arrays.binarySearch(a.getAreas(), p.getArea()) != -1;
    }

    private boolean checkSolapamiento(Profesor p, GrupoAsignatura a) {
        for (GrupoAsignatura asignatura : p.getAsignadas()) {
            if (asignatura.getHorario().getDia() == a.getHorario().getDia()
                    && asignatura.getSemestre() == a.getSemestre()) {
                int finActualInicioNueva = asignatura.getHorario().getHoraFin().compareTo(a.getHorario().getHoraInicio());
                int inicioActualFinNueva = asignatura.getHorario().getHoraInicio().compareTo(a.getHorario().getHoraFin());
                if (finActualInicioNueva <= 0) {
                    if (!asignatura.getEscuela().equals(a.getEscuela()))
                        if (Math.abs(
                                asignatura.getHorario().getHoraInicio().getTime() - a.getHorario().getHoraFin().getTime()
                        ) < TimeUnit.HOURS.toMillis(1))
                            return false;
                } else if (inicioActualFinNueva >= 0) {
                    if (!asignatura.getEscuela().equals(a.getEscuela()))
                        if (Math.abs(
                                asignatura.getHorario().getHoraFin().getTime() - asignatura.getHorario().getHoraInicio().getTime()
                        ) < TimeUnit.HOURS.toMillis(1))
                            return false;
                } else
                    return false;

            }
        }
        return true;
    }

    private boolean checkBilingue(GrupoAsignatura a, Profesor p) {
        return p.getBilingue() || !a.getBilingue();
    }

    private boolean checkCapacidad(GrupoAsignatura a, Profesor p) {
        return p.getCapacidad() >= a.getHoras();
    }

    private GrupoAsignatura getAsignaturaById(int idAsignatura) {
        for (GrupoAsignatura a : this.asignaturas) {
            if (a.getId() == idAsignatura)
                return a;
        }
        throw new RuntimeException("No existe ID de asignatura " + idAsignatura);
    }

}

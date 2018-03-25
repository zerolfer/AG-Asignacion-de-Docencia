package main.java.genetico.algoritmos;

import main.java.genetico.Individuo;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;
import main.java.util.Util;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sergio Florez on 09/03/2018.
 */
public class Decodificacion implements AlgoritmoDecodificacion {

    List<Profesor> profesores;
    List<GrupoAsignatura> asignaturas;

    public static boolean debug = false;

    @Override
    public void aplicar(Individuo individuo, List<Profesor> profesores, List<GrupoAsignatura> asignaturas) {

        Map<Integer, Set<Integer>> fenotipo = new HashMap<>();

        this.profesores = Util.copyOfProfesor(profesores);
        this.asignaturas = Util.copyOfGrupo(asignaturas);

        assert profesores != this.profesores;
        assert profesores.get(0) != this.profesores.get(0);
        assert profesores.get(0).getId() == this.profesores.get(0).getId();

        int noAsignadas = 0;
        for (int idAsignatura : individuo.getCromosoma()) { // O(n^3)
            //FIXME las asignaturas no tienen por que estar ordenadas por ID
            GrupoAsignatura asignatura = getAsignaturaById(idAsignatura);
            Profesor profesor = getProfesor(asignatura);
            if (profesor == null) {
                noAsignadas++;
                continue;
            }
            profesor.getAsignadas().add(asignatura);
            profesor.setCapacidad(profesor.getCapacidad() - asignatura.getHoras());
            if (fenotipo.containsKey(profesor.getId())) {
                Set<Integer> asignadas = fenotipo.get(profesor.getId());
                asignadas.add(asignatura.getId());
            } else {
                Set<Integer> nuevoSet = new HashSet<>();
                nuevoSet.add(asignatura.getId());
                fenotipo.put(profesor.getId(), nuevoSet);
            }
        }
        individuo.asignarFitness(noAsignadas, this.profesores, this.asignaturas);
        individuo.setFenotipo(fenotipo);
        if (debug) {
            if (individuo.getFitnessAsigProfesor() >= Integer.MAX_VALUE)
                System.out.println(Util.arrayToString(this.profesores, "\n"));
            System.out.println(noAsignadas + " asignaturas no han sido sasignadas");
            //System.out.println(individuo.fitnessToString());
        }
    }

    private Profesor getProfesor(GrupoAsignatura a) {
        for (Profesor p : this.profesores) {
            if (checkCapacidad(a, p) && checkBilingue(a, p) && checkArea(p, a))
                if (checkSolapamiento(p, a))
                    return p;
        }
        return null; // en caso de no haber profesoresa cubrir se le asignará un fitness infinito
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

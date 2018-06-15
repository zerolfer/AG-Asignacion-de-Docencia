package main.java.genetico.algoritmos.decodificacion;

import main.java.genetico.Individuo;
import main.java.model.BD;
import main.java.model.Grupo;
import main.java.model.Horario;
import main.java.model.Profesor;
import main.java.util.Util;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sergio Florez on 09/03/2018.
 */
public class Decodificacion implements AlgoritmoDecodificacion {

    /**
     * Mínimo lapso de tiempo en minutos que se deberá dejar
     * transcurrir como margen en caso de que la docencia a
     * sea en diferentes escuelas pero sea en la misma ciudad.
     *
     * @see #checkSolapamiento(Profesor, Grupo)
     */
    private static final int minutosIntervaloMismaCiudad = 30;

    /**
     * Mínimo lapso de tiempo en minutos que se deberá dejar
     * transcurrir como margen en caso de que la docencia a
     * sea en una escuela y una ciudad diferente.
     *
     * @see #checkSolapamiento(Profesor, Grupo)
     */
    private static final int minutosIntervaloDistintaCiudad = 60;


    public static boolean debug = false;
    List<Profesor> profesores;
    List<Grupo> asignaturas;

    @Override
    public void aplicar(Individuo individuo) {

        Map<Integer, Set<Integer>> fenotipo = new HashMap<>();
        Map<Profesor, Set<Grupo>> fenotipo2 = new HashMap<>();

        this.profesores = Util.copyOfProfesor(BD.getProfesores());
        this.asignaturas = Util.copyOfGrupo(BD.getAsignaturas());

        assert BD.getProfesores() != this.profesores;
        assert BD.getProfesores().get(0) != this.profesores.get(0);
        assert BD.getProfesores().get(0).getId() == this.profesores.get(0).getId();

        int noAsignadas = 0;
        for (int idAsignatura : individuo.getCromosoma()) { // O(n^3)
            Grupo asignatura = getAsignaturaById(idAsignatura);
            Profesor profesor = getProfesor(asignatura);
            if (profesor == null) {
                noAsignadas++;
                continue;
            }
            profesor.asignarGrupo(asignatura);

            if (fenotipo.containsKey(profesor.getId())) {
                Set<Integer> asignadas = fenotipo.get(profesor.getId());
                asignadas.add(asignatura.getId());
            } else {
                Set<Integer> nuevoSet = new HashSet<>();
                nuevoSet.add(asignatura.getId());
                fenotipo.put(profesor.getId(), nuevoSet);
            }


            if (fenotipo2.containsKey(profesor)) {
                Set<Grupo> asignadas = fenotipo2.get(profesor);
                asignadas.add(asignatura);
            } else {
                Set<Grupo> nuevoSet = new HashSet<>();
                nuevoSet.add(asignatura);
                fenotipo2.put(profesor, nuevoSet);
            }
        }

        asignarFitness(individuo, noAsignadas, this.profesores, this.asignaturas);

        individuo.setFenotipo(fenotipo);
        individuo.fenotipo2 = fenotipo2;

        if (debug) {
            if (individuo.getFitnessAsigProfesor() >= Integer.MAX_VALUE)
                System.out.println(Util.arrayToString(this.profesores, "\n"));
            System.out.println(noAsignadas + " asignaturas no han sido sasignadas");
            //System.out.println(individuo.fitnessToString());
        }
    }

    void asignarFitness(Individuo i, int noAsignadas, List<Profesor> profesores, List<Grupo> asignaturas) {
        if (noAsignadas != 0) {
            // en caso de no asignarse asignaturas a un profesor
            i.setFitnessAsigProfesor(Integer.MAX_VALUE);
            i.setFitnessNumHoras(Float.MIN_VALUE);
            i.noAsignadas = noAsignadas;
        } else {
            int max = 0;
            float min = 1;

            for (Profesor profesor : profesores) {
                // FITNESS 1
                int numAsignaturas = profesor.getNumAsignaturas();
                if (numAsignaturas > max)
                    max = numAsignaturas;

                // FITNESS 2
                float horasAsignadas = profesor.getCapacidadInicial() - profesor.getCapacidad();
                float proporcion = horasAsignadas / profesor.getCapacidadInicial();
                if (proporcion < min) {
                    min = proporcion;
                }
            }
            i.setFitnessAsigProfesor(max);
            i.setFitnessNumHoras(min);
        }
    }

    Profesor getProfesor(Grupo a) {
        for (Profesor p : this.profesores) {
            if (checkCapacidad(a, p) && checkBilingue(a, p) && checkArea(p, a) && checkDisponibilidad(a, p))
                if (checkSolapamiento(p, a))
                    return p;
        }
        return null; // en caso de no haber profesoresa cubrir se le asignará un fitness infinito
    }

    boolean checkDisponibilidad(Grupo a, Profesor p) {
        for (Horario horario : a.getHorarios()) {
            // la unica opcion es que el horario sea o igual o subconjunto de la disponibilidad
            // es decir, la hora de inicio sea posterior o igual a la hora de inicio de disponibilidad
            // y que la hora de fin de la clase sea anterior o igual a la de fin de disponibilidad
            if (p.getDisponibilidad().getHoraInicio().compareTo(horario.getHoraInicio())<=0
                    && p.getDisponibilidad().getHoraFin().compareTo(horario.getHoraFin())>=0)
                continue;
            else // las demas opciones no son validas, y no es necesario seguir iterando
                return false;
        }
        return true; // si se ha llegado hasta aqui es porque todos cumplian la restriccion
    }

    boolean checkArea(Profesor p, Grupo a) {
        return Arrays.binarySearch(a.getAreas(), p.getArea()) != -1;
    }

    boolean checkSolapamiento(Profesor p, Grupo a) {
        for (Grupo asignatura : p.getAsignadas()) {
            for (Horario horariosGrupoActual : asignatura.getHorarios()) { // actual(es)
                for (Horario horariosGrupoNuevo : a.getHorarios()) { // nuevo(s)
                    if (horariosGrupoActual.getDia() == horariosGrupoNuevo.getDia()
                            && asignatura.getSemestre() == a.getSemestre()) {
                        int finActualInicioNueva =
                                horariosGrupoActual.getHoraFin().compareTo(horariosGrupoNuevo.getHoraInicio());
                        int inicioActualFinNueva =
                                horariosGrupoActual.getHoraInicio().compareTo(horariosGrupoNuevo.getHoraFin());

                        int lapso;
                        if (asignatura.getCiudad().equals(a.getCiudad())) // misma ciudad pero distinta escuela
                            lapso = minutosIntervaloMismaCiudad;
                        else lapso = minutosIntervaloDistintaCiudad;

                        // si el fin de la actual es anterior al comienzo de la nueva a asignar
                        if (finActualInicioNueva <= 0) {
                            if (!asignatura.getEscuela().equals(a.getEscuela())) {
                                if (Math.abs(
                                        horariosGrupoNuevo.getHoraInicio().getTime() -
                                                horariosGrupoActual.getHoraFin().getTime()
                                ) < TimeUnit.MINUTES.toMillis(lapso))
                                    return false;
                            }

                            // si comienzo de la actual es posterior al final de la nueva a asignar
                        } else if (inicioActualFinNueva >= 0) {
                            if (!asignatura.getEscuela().equals(a.getEscuela()))
                                if (Math.abs(
                                        horariosGrupoNuevo.getHoraFin().getTime() -
                                                horariosGrupoActual.getHoraInicio().getTime()
                                ) < TimeUnit.MINUTES.toMillis(lapso))
                                    return false;
                        } else // en los demas casos hay solapamiento y no es válido
                            return false;
                    }
                }
            }
        }
        return true;

        // posible mejora:
        /*
        if (p.getDisponibilidad().getHoraInicio().after(horario.getHoraFin()))
                continue;
            else if(p.getDisponibilidad().getHoraFin().before(horario.getHoraInicio()))
                continue;
            //otherwise
            else
                return false;
         */
    }

    boolean checkBilingue(Grupo a, Profesor p) {
        return p.getBilingue() || !a.getBilingue();
    }

    boolean checkCapacidad(Grupo a, Profesor p) {
        return p.getCapacidad() >= a.getHorasComputables(p);
    }

    Grupo getAsignaturaById(int idAsignatura) {
        for (Grupo a : this.asignaturas) {
            if (a.getId() == idAsignatura)
                return a;
        }
        throw new RuntimeException("No existe ID de asignatura " + idAsignatura);
    }

}

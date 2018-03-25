package main.java.genetico;


import main.java.model.BD;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Sergio Florez on 27/02/2018.
 */
public class Individuo {

    private int[] cromosoma; // cada elemento representa el id de una asignatura

    // los valores de fitness
    private int fitnessAsigProfesor;
    private float fitnessNumHoras;

    private Map<Integer, Set<Integer>> fenotipo; // <AsignaturaId, ProfesorId>


    public Individuo(int[] cromosoma) {
        this.cromosoma = cromosoma;

    }

    public int[] getCromosoma() {
        return cromosoma;
    }

    public void setCromosoma(int[] cromosoma) {
        this.cromosoma = cromosoma;
    }

    public int getFitnessAsigProfesor() {
        return fitnessAsigProfesor;
    }

    public float getFitnessNumHoras() {
        return fitnessNumHoras;
    }

    @Override
    public String toString() {
        return "Individuo:{ fitness: (" + fitnessAsigProfesor + ", " + fitnessNumHoras + "), "
                + "Codigo cromosoma: " + cromosomaToString() + " }";
        //super.toString();//Arrays.toString(cromosoma);
    }

    private String cromosomaToString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < cromosoma.length; i++) {
            s.append(cromosoma[i]);
            if (i < cromosoma.length - 1) s.append(",");
        }
        return s.toString();
    }

    public void asignarFitness(int noAsignadas, List<Profesor> profesores, List<GrupoAsignatura> asignaturas) {

        if (noAsignadas != 0) {
            // en caso de no asignarse asignaturas a un profesor
            fitnessAsigProfesor = Integer.MAX_VALUE; //FIXME infinito
            fitnessNumHoras = Float.MAX_VALUE;
        } else {
            int max = 0;
            float min = profesores.get(0).getCapacidadInicial();
            for (Profesor profesor : profesores) {

                if (profesor.getAsignadas().size() > max)
                    max = profesor.getAsignadas().size();
                if (profesor.getCapacidadInicial() - profesor.getCapacidad() < min)
                    min = profesor.getCapacidadInicial() - profesor.getCapacidad();
            }
            fitnessAsigProfesor = max;
            fitnessNumHoras = min;
        }
    }

    public String fitnessToString() {
        String s = "el individuo tiene un fitness de: " + getFitnessAsigProfesor()
                + " (max num asignaturas/profesor asignadas) y "
                + getFitnessNumHoras() + " (min horas/profesor asignadas)";
        return s;

    }

    public int size() {
        return this.cromosoma.length;
    }

    public void setFenotipo(Map<Integer, Set<Integer>> fenotipo) {
        this.fenotipo = fenotipo;
    }

    public Map<Integer, Set<Integer>> getFenotipo() {
        return fenotipo;
    }

    public boolean contains(int desde, int hasta, int integer) {
        for (int i = 0; i <= size(); i++)
            if (i >= desde && i <= hasta)
                if (cromosoma[i] == integer)
                    return true;
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Individuo individuo = (Individuo) o;

        return Arrays.equals(cromosoma, individuo.cromosoma);
    }

    public boolean checkHayRepetidos() {
        for (int i = 0; i < cromosoma.length; i++)
            for (int j = 0; j < cromosoma.length; j++)
                if (i != j && cromosoma[i] == cromosoma[j])
                    return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(cromosoma);
    }

    @Override
    public Individuo clone() {
        return new Individuo(this.cromosoma.clone());
    }

    public String toStringFull() {
        StringBuilder sb = new StringBuilder("Individuo:{ \n\tfitness: (" + fitnessAsigProfesor + ", " + fitnessNumHoras + "), "
                + "Codigo cromosoma: " + cromosomaToString() + "\n");
        for (Integer key : fenotipo.keySet()) { // profesores
            sb.append(BD.getProfesorById(key) + " asignadas: [ ");

            Set<Integer> asignadas = fenotipo.get(key);
            int i = 0;
            for (Integer grupo : asignadas) {
                i++;
                sb.append(BD.getGrupoById(grupo).getId());
                if (i < asignadas.size())
                    sb.append(", ");

            }
            sb.append(" ]\n");
        }
//        for (int value : fenotipo.values()) {
//            sb.append(BD.getProfesorById(value) + " asignaturas: ");
//        }

        return sb.toString();
    }


}

package main.java.genetico;


import main.java.genetico.algoritmos.AlgoritmoDecodificacion;
import main.java.genetico.algoritmos.Decodificacion;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergio Florez on 27/02/2018.
 */
public class Individuo {

    private int[] cromosoma; // cada elemento representa el id de una asignatura

    // los valores de fitness
    private int fitnessAsigProfesor;
    private float fitnessNumHoras;

    private Map<Integer, /*List<*/Integer/*>*/> fenotipo;


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
        return Arrays.toString(cromosoma);
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
            System.out.println("ESTE CROMOSOMA ES VÁLIDO: "+this);
        }
    }

    public String fitnessToString() {
        String s = "el individuo tiene un fitness de: " + getFitnessAsigProfesor()
                + " (max num asignaturas/profesor asignadas) y "
                + getFitnessNumHoras() + " (min horas/profesor asignadas)";
        return s;

    }

    public void setFenotipo(Map<Integer, /*List<*/Integer/*>*/> fenotipo) {
    this.fenotipo=fenotipo;
    }

    public Map<Integer, /*List<*/Integer/*>*/> getFenotipo() {
        return fenotipo;
    }
}

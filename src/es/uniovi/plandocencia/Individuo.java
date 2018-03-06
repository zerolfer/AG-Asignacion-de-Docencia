package es.uniovi.plandocencia;

import es.uniovi.plandocencia.model.Profesor;

import java.util.Arrays;

/**
 * Created by Sergio Florez on 27/02/2018.
 */
public class Individuo {

    private int[] cromosoma; // cada elemento representa el id de una asignatura

    // los valores de fitness
    private int fitnessAsigProfesor;
    private float fitnessNumHoras;

    // TODO: private Profesor fenotipo;

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

    public void asignarFitness(Profesor profesor){
        fitnessAsigProfesor=profesor.getAsignadas().size();
        fitnessNumHoras=profesor.getCapacidad();
    }
}

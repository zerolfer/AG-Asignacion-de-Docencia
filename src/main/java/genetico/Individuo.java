package main.java.genetico;


import main.java.genetico.algoritmos.decodificacion.AlgoritmoDecodificacion;
import main.java.genetico.algoritmos.decodificacion.DecodificacionFiltroGrupo;
import main.java.model.BD;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Created by Sergio Florez on 27/02/2018.
 */
public class Individuo implements Comparable<Individuo> {

    AlgoritmoDecodificacion decodificacion;
    private int[] cromosoma; // cada elemento representa el id de una asignatura
    // los valores de fitness
    private int fitnessAsigProfesor;
    private float fitnessNumHoras;

    private Map<Integer, Set<Integer>> fenotipo; // <ProfesorId, AsignaturaId>
    public Map<Profesor, Set<GrupoAsignatura>> fenotipo2;


    public Individuo(int[] cromosoma) {
        this.cromosoma = cromosoma;
        this.decodificacion = new DecodificacionFiltroGrupo();
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

    public void setFitnessAsigProfesor(int fitnessAsigProfesor) {
        this.fitnessAsigProfesor = fitnessAsigProfesor;
    }

    public float getFitnessNumHoras() {
        return fitnessNumHoras;
    }

    public void setFitnessNumHoras(float fitnessNumHoras) {
        this.fitnessNumHoras = fitnessNumHoras;
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

    public String fitnessToString() {
        String s = "el individuo tiene un fitness de: " + getFitnessAsigProfesor()
                + " (max num asignaturas/profesor asignadas) y "
                + getFitnessNumHoras() + " (min horas/profesor asignadas)";
        return s;

    }

    public int size() {
        return this.cromosoma.length;
    }

    public Map<Integer, Set<Integer>> getFenotipo() {
        return fenotipo;
    }

    public Map<Profesor, Set<GrupoAsignatura>> getFenotipo2() {
        return fenotipo2;
    }

    public void setFenotipo(Map<Integer, Set<Integer>> fenotipo) {
        this.fenotipo = fenotipo;
    }

    public boolean contains(int integer) {
        return contains(0, cromosoma.length - 1, integer);
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

// Se considera igual si tiene el mismo valor de fitness
        Individuo individuo = (Individuo) o;
        if (this.getFitnessNumHoras() == individuo.getFitnessNumHoras()
                && this.getFitnessAsigProfesor() == individuo.getFitnessAsigProfesor())
            return true;

        return false;
//        return Arrays.equals(cromosoma, individuo.cromosoma);
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
        Individuo result = new Individuo(this.cromosoma.clone());
        result.setFitnessAsigProfesor(getFitnessAsigProfesor());
        result.setFitnessNumHoras(getFitnessNumHoras());
        if (getFenotipo() != null)  result.setFenotipo(new HashMap<>(getFenotipo()));
        if (getFenotipo2() != null) result.fenotipo2 = new HashMap<>(getFenotipo2());
        return result;
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
        sb.append("}");

//        for (int value : fenotipo.values()) {
//            sb.append(BD.getProfesorById(value) + " asignaturas: ");
//        }

        return sb.toString();
    }

    @Override
    public int compareTo(Individuo ind) {
        if (this.getFitnessAsigProfesor() > ind.getFitnessAsigProfesor())
            return -1; // es mejor ind
        if (this.getFitnessAsigProfesor() < ind.getFitnessAsigProfesor())
            return 1; // es mejor this
        // en este punto sabemos que son iguales, empleamos el otro fitness
        if (this.getFitnessNumHoras() > ind.getFitnessNumHoras())
            return 1; // es mejor this
        if (this.getFitnessNumHoras() < ind.getFitnessNumHoras())
            return -1; // es mejor ind
            // ambos individuos son iguales
        else return 0;
    }

    public boolean esMejor(int fitness1, float fitness2) {
        if (this.getFitnessAsigProfesor() > fitness1)
            return false;
        if (this.getFitnessAsigProfesor() < fitness1)
            return true;
        // en este punto sabemos que son iguales, empleamos el otro fitness
        if (this.getFitnessNumHoras() > fitness2)
            return true;
        if (this.getFitnessNumHoras() < fitness2)
            return false;
            // ambos individuos son iguales
        else return false;
    }

    public void evaluar() {
        decodificacion.aplicar(this);
    }

    public boolean esMejor(Individuo vecino) {
        return this.compareTo(vecino) < 0 ? false : true;
    }
}

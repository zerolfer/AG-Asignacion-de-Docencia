package main.java.genetico;


import main.java.genetico.operadores.decodificacion.AlgoritmoDecodificacion;
import main.java.genetico.operadores.decodificacion.DecodificacionFiltroGrupo;
import main.java.model.Grupo;
import main.java.model.Profesor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Created by Sergio Florez on 27/02/2018.
 */
public class Individuo implements Comparable<Individuo> {

    static AlgoritmoDecodificacion decodificacion;
    private int[] cromosoma; // cada elemento representa el id de una asignatura
    // los valores de fitness
    private int fitnessAsigProfesor;
    private float fitnessNumHoras;

    //    private Map<Integer, Set<Integer>> fenotipo; // <ProfesorId, AsignaturaId>
    private Map<Integer, Profesor> fenotipo2;
    public int noAsignadas;

    private boolean yaEvaluado = false; // inicialmente no evaluado

    public Individuo(int[] cromosoma) {
        this.cromosoma = cromosoma;
        decodificacion = new DecodificacionFiltroGrupo();
        this.fenotipo2=new HashMap<>();
        noAsignadas=-1;
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
        return "Individuo:{ fitness: (" + yaEvaluado + ", " + fitnessAsigProfesor + ", " + fitnessNumHoras + "), "
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

//    public Map<Integer, Set<Integer>> getFenotipo() {
//        return fenotipo;
//    }

    public Map<Integer, Profesor> getFenotipo() {
        return fenotipo2;
    }

    public void setFenotipo(Map<Integer, Profesor> fenotipo) {
        this.fenotipo2 = fenotipo;
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

    public int getNumeroGrupos() {
        int i = 0;
        for (Profesor key : fenotipo2.values()) { // profesores
            Set<Grupo> asignadas = key.getAsignadas();
            for (Grupo grupo : asignadas) {
                i++;
            }
        }
        return i;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(cromosoma);
    }

    @Override
    public Individuo clone() {
        Individuo result = new Individuo(Arrays.copyOf(this.cromosoma, this.cromosoma.length));
        result.setFitnessAsigProfesor(getFitnessAsigProfesor());
        result.setFitnessNumHoras(getFitnessNumHoras());
        result.setYaEvaluado(yaEvaluado);
        result.noAsignadas=noAsignadas;//this.noAsignadas;
//        if (getFenotipo() != null) result.setFenotipo(new HashMap<>(getFenotipo()));
        result.fenotipo2 = clonarFenotipo2();
        return result;
    }

    private Map<Integer, Profesor> clonarFenotipo2() {
        Map<Integer,Profesor> result = new HashMap<>();
        for (Map.Entry<Integer,Profesor> e : fenotipo2.entrySet()) {
            for (Profesor p: fenotipo2.values())
                result.put(p.getId(), p.clone());
        }
        return result;
    }

    public String toStringFull() {
        StringBuilder sb = new StringBuilder("Individuo:{ \n\tfitness: (" + fitnessAsigProfesor +
                ", " + fitnessNumHoras + ", " + yaEvaluado + "), "
                + "Codigo cromosoma: " + cromosomaToString() + "\n");
        for (Profesor key : fenotipo2.values()) { // profesores
            sb.append(key + " asignadas: [ ");

            Set<Grupo> asignadas = key.getAsignadas();
            int i = 0;
            for (Grupo grupo : asignadas) {
                i++;
                sb.append(grupo.getId());
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
        else return false; // ambos individuos son iguales
    }

    public void evaluar() {
        if (!yaEvaluado) { // si ya estaba evaluado no es necesario volver a hacerlo
            decodificacion.aplicar(this);
            this.yaEvaluado = true;
        }
    }

    /**
     * Decide si un individuo es mejor que otro segun se
     * describe en el metodo
     * {@link Individuo#compareTo(Individuo)}
     * <br/><b>En caso de ser iguales, se retonará false</b>
     *
     * @param vecino
     * @return false en caso de ser peor o igual que el
     * otro individuo
     */
    public boolean esMejor(Individuo vecino) {
        return this.compareTo(vecino) <= 0 ? false : true;
    }


    public void asignarFitnessPorFenotipo(Profesor profesor1, Profesor profesor2) {

        if(noAsignadas<=-1) noAsignadas=cromosoma.length-getNumeroGrupos();
        else assert  noAsignadas==cromosoma.length-getNumeroGrupos();
        if (noAsignadas != 0) {
            // en caso de no asignarse asignaturas a un profesor
            setFitnessAsigProfesor(Integer.MAX_VALUE);
            setFitnessNumHoras(Float.MIN_VALUE);
        } else {
            int max = 0;
            float min = 1;

            for (Profesor profesor : getFenotipo().values()) {
                // FITNESS 1
                int numAsignaturas;
                if (profesor.equals(profesor1) || profesor.equals(profesor2))
                    numAsignaturas = profesor.getNumAsignaturas(true);
                else numAsignaturas = profesor.getNumAsignaturas();
                if (numAsignaturas > max)
                    max = numAsignaturas;

                // FITNESS 2
                float horasAsignadas = profesor.getCapacidadInicial() - profesor.getCapacidad();
                float proporcion = horasAsignadas / profesor.getCapacidadInicial();
                if (proporcion < min) {
                    min = proporcion;
                }
            }
            setFitnessAsigProfesor(max);
            setFitnessNumHoras(min);
        }
    }

    public void setYaEvaluado(boolean yaEvaluado) {
        this.yaEvaluado = yaEvaluado;
    }
}

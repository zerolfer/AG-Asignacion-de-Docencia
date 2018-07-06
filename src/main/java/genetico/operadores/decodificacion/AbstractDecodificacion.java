package main.java.genetico.operadores.decodificacion;

import main.java.genetico.Individuo;
import main.java.io.Settings;
import main.java.model.BD;
import main.java.model.Grupo;
import main.java.model.Profesor;
import main.java.util.Util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.java.busqueda.BusquedaIntercambioGrupoAntigua.noHayRepetidos;

public abstract class AbstractDecodificacion implements AlgoritmoDecodificacion {

    public static boolean debug = Settings.getBoolean("debug.decodificador.mensajes");
    List<Profesor> profesores;
    private List<Grupo> asignaturas;

    @Override
    public void aplicar(Individuo individuo) {

        Map<Integer, Profesor> fenotipo = new HashMap<>();

        // haciendo copia garantizamos que las referencias sean unicas
        this.profesores = Util.copyOfProfesor(BD.getProfesores());
        this.asignaturas = Util.copyOfGrupo(BD.getGrupos());

        assert BD.getProfesores() != this.profesores;
        assert BD.getProfesores().get(0) != this.profesores.get(0);
        assert BD.getProfesores().get(0).getId() == this.profesores.get(0).getId();

        int noAsignadas = 0;
        for (int idAsignatura : individuo.getCromosoma()) { // O(n^3)
            Grupo asignatura = getGrupoById(idAsignatura);
            Profesor profesor = getProfesor(asignatura); // método clave de comprobación de compatibilidades
            if (profesor == null) {
                noAsignadas++;
                continue;
            }
//            assert noHayRepetidos(profesor):individuo;
//            assert noHayRepetidos(profesor,profesores);

//            profesor.asignarGrupo(asignatura);

//            assert noHayRepetidos(profesor):individuo;
//            assert noHayRepetidos(profesor,profesores);
//

            /*
            if (fenotipo.containsKey(profesor.getId())) {
                Set<Integer> asignadas = fenotipo.get(profesor.getId());
                boolean b = asignadas.add(asignatura.getId());
                assert b;
            } else {
                Set<Integer> nuevoSet = new HashSet<>();
                nuevoSet.add(asignatura.getId());
                fenotipo.put(profesor.getId(), nuevoSet);
            }
            */

            if (fenotipo.containsKey(profesor.getId())) {
                Profesor fProfesor = fenotipo.get(profesor.getId());
                boolean b = fProfesor.asignarGrupo(asignatura); // la asignacion se hace en el profesor del fenotipo
                assert b;
            } else {
                // si no existe el profesor en el fenotipo, se introduce en el
                // fenotipo con la referencia a este profesor, garantizando
                // que la referencia sea única.
                profesor.asignarGrupo(asignatura);
                fenotipo.put(profesor.getId(), profesor);
            }
        }

        asignarFitness(individuo, noAsignadas, fenotipo.values());


        individuo.setFenotipo(fenotipo);
        assert noHayRepetidos(individuo) : "HAY REPETIDOS AL DECODIFICAR EN " + individuo;

        if (debug) {
            if (individuo.getFitnessAsigProfesor() >= Integer.MAX_VALUE)
                System.out.println(Util.arrayToString(fenotipo.values().toArray(), "\n"));
            System.out.println(noAsignadas + " asignaturas no han sido asignadas");
        }
    }

    /**
     * TEMPLATE METHOD
     * Cada clase que extienda de ésta implementará este
     * método de la forma oportuna
     *
     * @param grupoAsignatura grupo que pertenezca a la asignatura de la que se quiere buscar
     *                        un profesor que impata clase en la misma
     * @return un posible profesor que pueda impartir la asignatura
     * a la que pertenece el grupo que se indica por parametro
     */
    protected abstract Profesor getProfesor(Grupo grupoAsignatura);

    /**
     * Recorre los profesores para calcular el fitness en base a la
     * informacion contenida y asigna los resultados a las variables
     * accesibles mediante {@link Individuo#getFitnessAsigProfesor()}
     * y {@link Individuo#getFitnessNumHoras()}.
     *
     * @param individuo   valor en el cual el fitness será asignado
     * @param noAsignadas numero de asignaturas que no han sido asignadas, este valor se obtiene
     *                    durante la asignacion en {@link AlgoritmoDecodificacion#aplicar(Individuo)}
     * @param profesores  conjunto de profesores sobre los que iterar
     */
    private void asignarFitness(Individuo individuo, int noAsignadas, Collection<Profesor> profesores) {
        if (noAsignadas != 0) {
            // en caso de no asignarse asignaturas a un profesor
            individuo.setFitnessAsigProfesor(Integer.MAX_VALUE);
            individuo.setFitnessNumHoras(Float.MIN_VALUE);
            individuo.noAsignadas = noAsignadas;
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
            individuo.setFitnessAsigProfesor(max);
            individuo.setFitnessNumHoras(min);
        }
    }

    /**
     * <p>Obtiene un grupo a partir de su id.</p>
     *
     * <p>No se emplea el de la clase BD debido
     * a la necesidad de tratar con copias de
     * los datos, para no modificar los reales</p>
     *
     * @param idGrupo identificador de la asignatura
     * @return Grupo
     */
    private Grupo getGrupoById(int idGrupo) {
        for (Grupo a : this.asignaturas)
            if (a.getId() == idGrupo)
                return a;
        throw new RuntimeException("No existe ID de asignatura " + idGrupo);
    }

}

package main.java.busqueda;

import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.Individuo;
import main.java.model.Grupo;
import main.java.model.Profesor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BusquedaIntercambioGrupo extends AbstractBusquedaLocal {

    private Comparator<GrupoAux> comparatorAsignatura;

    public BusquedaIntercambioGrupo() {
        this(AlgoritmoGenetico.PROBABILIDAD_BUSQUEDA);
    }

    public BusquedaIntercambioGrupo(float probabilidad) {
        super(probabilidad);
        this.comparatorAsignatura = (o1, o2) -> {
            if (o1.grupo.getCodigoAsignatura().equals(o2.grupo.getCodigoAsignatura())) {
                return 0;
            }
            if (o1.numGrupos > o2.numGrupos) {
                return 1;
            }
            return -1;
        };
    }

    boolean hayCambio=false;
    @Override
    public Individuo buscar(Individuo original) {
        if (debug&& hayCambio) {
            System.out.println(original.toString());
            hayCambio=false;
        }

        if (original.noAsignadas > 0) return original; // no hay mejora posible si ni siquiera estamos ante una solucion

        Individuo cIndividuo = original.clone();

        Profesor profesor1 = profesorConMenosAsignaturas(original);
        for (GrupoAux grupo1 : getGruposAux(profesor1)) {
            Profesor profesor2 = profesorQueImparta(original, grupo1.grupo.getCodigoAsignatura(), profesor1);

            // si no hay profesores que impartan la asignatura, no hay mejora posible
            if (profesor2 == null) return original;

            for (GrupoAux grupo2 : getGruposAux(profesor2)) {

                // variables auxiliares para la verificacion
                Profesor profesor1Copia = cIndividuo.getFenotipo().get(profesor1.getId());
                Profesor profesor2Copia = cIndividuo.getFenotipo().get(profesor2.getId());

                /*
                 * modificamos la copia en lugar del original
                 */
                if (verificarIntercambio(profesor1Copia, grupo1.grupo, profesor2Copia, grupo2.grupo)) {

//                    int fitness1 = cIndividuo.getFitnessAsigProfesor();
//                    float fitness2 = cIndividuo.getFitnessNumHoras();

                    cIndividuo.asignarFitnessPorFenotipo(profesor1Copia, profesor2Copia);
//                    float[] nuevoFitness = obtenerNuevosFitness(individuo, profesor1Copia, profesor2Copia);

           /*
                    assert noHayRepetidos(profesor1Copia, profesor2Copia) : profesor1Copia + grupo1.toString() + grupo2;
                    assert profesor1Copia.getCapacidadInicial() - suma(profesor1Copia) == profesor1Copia.getCapacidad() : profesor1Copia + grupo1.toString() + grupo2;
                    assert profesor1Copia.getHorasClaseAsignadas2() == profesor1Copia.getHorasClaseAsignadas();
                    assert getProfesorFromFenotipo(cIndividuo, profesor1Copia).getHorasClaseAsignadas2() ==
                            getProfesorFromFenotipo(cIndividuo, profesor1Copia).getHorasClaseAsignadas();
                    assert sumaFenotipo(cIndividuo, profesor1Copia) == suma(profesor1Copia) : profesor1Copia + grupo1.toString() + grupo2;
            */
                    if (cIndividuo.esMejor(original)) { // si es mejor el individuo nuevo
                        // en este punto el individuo ha sido modificado, por lo que +"]la llamada es diferente
                        if (debug&&!huboCambio) System.out.println(original.toString());
                        if (debug)
                            System.out.println("swap:" + "[" + profesor1.getId() + "," + grupo1.grupo.getId() + "]" +
                                    "<-->" + "[" + profesor2.getId() + "," + grupo2.grupo.getId() + "]");
                        hayCambio=true;
                        this.huboCambio=true;
                        return buscar(cIndividuo);
                    } else {
//                        if (debug) {
//                            System.out.println(original.toString());
//                            System.out.println("(No Era Mejor)");
//                        }
                    }
                }
                cIndividuo = original.clone(); // En este punto, es necesario deshacer los cambios
            } // Fin bucle grupo2
        } // Fin blucle grupo1
        // En este punto ya se ha intentado hacer todo los intercambios, si no hay mejora no hay nada que hacer
        return original;
    }

    private List<GrupoAux> getGruposAux(Profesor profesor) {
        List<GrupoAux> result = new ArrayList<>();

        for (Grupo grupo : profesor.getAsignadas()) {
            result.add(new GrupoAux(profesor, grupo));
        }
        result.sort(this.comparatorAsignatura);
        return result;
    }

    /**
     * este metodo prueba con respectivas copias si es posible realizar
     * el intercambio.
     * <p>
     * Deberá ser llamado con copias de los profesores, pues éstos son
     * alterados durante el método.
     *
     * @param profesor1
     * @param grupo1
     * @param profesor2
     * @param grupo2
     * @return true si se puede realizar el intecambio
     */
    private boolean verificarIntercambio(Profesor profesor1, Grupo grupo1, Profesor profesor2, Grupo grupo2) {
        if (grupo1.equals(grupo2))
            throw new RuntimeException("situacion ilegal");
        if (grupo1.getCodigoAsignatura().equals(grupo2.getCodigoAsignatura())
                && grupo1.getHorasComputables(profesor1) == grupo2.getHorasComputables(profesor2)) {
            return false;
        }
        boolean b1 = profesor1.eliminarGrupo(grupo1);
        if (b1 == false) return false;

        boolean b2 = profesor1.asignarGrupo(grupo2);
        if (b2 == false) return false;

        boolean b3 = profesor2.eliminarGrupo(grupo2);
        if (b3 == false) return false;

        boolean b4 = profesor2.asignarGrupo(grupo1);
        if (b4 == false) return false;

        return true;

    }

    private Profesor profesorQueImparta(Individuo individuo, String codigoAsignatura, Profesor profesor1) {
        for (Profesor p : individuo.getFenotipo().values()) {
            if (!p.equals(profesor1) && p.checkImparteAsignatura(codigoAsignatura))
                return p;
        }
        return null;
    }

    private Profesor profesorConMenosAsignaturas(Individuo individuo) {
        Profesor result = null;
        for (Profesor p : individuo.getFenotipo().values()) {
            if (result != null && p.getNumAsignaturas() >= result.getNumAsignaturas())
                continue;
            result = p;
        }
        return result; // retorna una referencia al FENOTIPO
    }

    private class GrupoAux {
        Grupo grupo;
        int numGrupos;

        GrupoAux(Profesor p, Grupo grupo) {
            this.numGrupos = 0;
            for (Grupo g : p.getAsignadas()) {
                if (!grupo.getCodigoAsignatura().equals(g.getCodigoAsignatura())) continue;
                ++this.numGrupos;
            }
            this.grupo = grupo;
        }

        public String toString() {
            return "{grupo:" + this.grupo.getId() + ", asignatura:" + this.grupo.getCodigoAsignatura() + ", numGrupos:" + this.numGrupos + "}";
        }
    }

}

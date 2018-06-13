package main.java.busqueda;

import main.java.genetico.Individuo;
import main.java.model.Grupo;
import main.java.model.Profesor;

import java.util.*;

public class BusquedaIntercambioGrupo
        extends StandardBusquedaLocal {
    private Individuo original;
    private Comparator<BusquedaIntercambioGrupo.GrupoAux> comparatorAsignatura;
    private String asignaturaAnterior;
    private Profesor profesorAnterior;

    public BusquedaIntercambioGrupo(float probabilidad) {
        super(probabilidad);
        this.comparatorAsignatura = new Comparator<BusquedaIntercambioGrupo.GrupoAux>() {
            @Override
            public int compare(BusquedaIntercambioGrupo.GrupoAux o1, BusquedaIntercambioGrupo.GrupoAux o2) {
                if (o1.grupo.getCodigoAsignatura() == o2.grupo.getCodigoAsignatura()) {
                    return 0;
                }
                if (o1.numGrupos > o2.numGrupos) {
                    return 1;
                }
                return -1;
            }
        };
        this.profesorAnterior = null;
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

    public Individuo buscar(Individuo individuo) {
        if(debug) System.out.println(individuo.hashCode()+" -> "+individuo.toString());
        this.original = individuo.clone();
        Profesor profesor1 = this.profesorConMenosAsignaturas(individuo.getFenotipo2());
        List<GrupoAux> grupos = new ArrayList<GrupoAux>();
        for (Grupo ga : profesor1.getAsignadas()) {
            grupos.add(new GrupoAux(profesor1, ga));
        }
        grupos.sort(this.comparatorAsignatura);
        for (GrupoAux grupo1 : grupos) {
            Profesor profesor2 = this.buscarProfeQueImparta(profesor1, individuo.getFenotipo2(), grupo1.grupo.getCodigoAsignatura());
            if (profesor2 == null) {
                return individuo;
            }
            ArrayList<GrupoAux> grupos2 = new ArrayList<GrupoAux>();
            for (Grupo ga : profesor2.getAsignadas()) {
                grupos2.add(new GrupoAux(profesor2, ga));
            }
            grupos2.sort(this.comparatorAsignatura);
            for (GrupoAux grupo2:grupos2) {
                if (this.verificar(individuo, profesor1, grupo1, profesor2, grupo2))
                    return this.buscar(individuo);
                else individuo = original;
            }
        }
        return individuo;
    }

    private boolean verificar(Individuo individuo, Profesor profesor1, GrupoAux grupo1,
                              Profesor profesor2, GrupoAux grupo2) {
        if (grupo1.grupo.getCodigoAsignatura().equals(grupo2.grupo.getCodigoAsignatura())
                && grupo1.grupo.getHorasComputables(profesor1)==grupo2.grupo.getHorasComputables(profesor2)) {
            return false;
        }

        //Individuo original = original.clone();


        boolean b1 = profesor1.eliminarGrupo(grupo1.grupo);
        boolean b2 = profesor1.asignarGrupo(grupo2.grupo);

        boolean b3 = profesor2.eliminarGrupo(grupo2.grupo);
        boolean b4 = profesor2.asignarGrupo(grupo1.grupo);

        if (!b1 || !b2 || !b3 || !b4) {
            //individuo = this.original;// no se puede realizar el cambio
            return false;
        }

        int fitness1 = individuo.getFitnessAsigProfesor();
        float fitness2 = individuo.getFitnessNumHoras();

        individuo.asignarFitnessPorFenotipo(profesor1, profesor2);
        if (individuo.esMejor(fitness1, fitness2))
            return true;

        /*profesor1.getAsignadas().remove(grupo2.grupo);
        profesor1.getAsignadas().add(grupo1.grupo);

        profesor2.getAsignadas().remove(grupo1.grupo);
        profesor2.getAsignadas().add(grupo2.grupo);

        original.setFitnessAsigProfesor(fitness1);
        original.setFitnessNumHoras(fitness2);*/
        //individuo = this.original;
        return false;
    }

    private Profesor buscarProfeQueImparta(Profesor profesor1, Map<Profesor, Set<Grupo>> fenotipo, String codigoAsignatura) {
        if (this.asignaturaAnterior == null || this.profesorAnterior == null || !this.asignaturaAnterior.equals(codigoAsignatura)) {
            this.asignaturaAnterior = codigoAsignatura;
            for (Profesor p : fenotipo.keySet()) {
                if (!p.imparte(codigoAsignatura) || p.equals((Object) profesor1)) continue;
                this.profesorAnterior = p;
                return this.profesorAnterior;
            }
            this.profesorAnterior = null;
            this.asignaturaAnterior = null;
            return null;
        }
        return this.profesorAnterior;
    }

    private Profesor profesorConMenosAsignaturas(Map<Profesor, Set<Grupo>> fenotipo) {
        Profesor min = null;
        for (Profesor p : fenotipo.keySet()) {
            if (min != null && p.getNumAsignaturas() >= min.getNumAsignaturas()) continue;
            min = p;
        }
        return min;
    }
}

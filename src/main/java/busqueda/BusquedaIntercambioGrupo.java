package main.java.busqueda;

import main.java.genetico.Individuo;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public class BusquedaIntercambioGrupo
        extends StandardBusquedaLocal {
    private Individuo individuo;
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
        GrupoAsignatura grupo;
        int numGrupos;

        GrupoAux(Profesor p, GrupoAsignatura grupo) {
            this.numGrupos = 0;
            for (GrupoAsignatura g : p.getAsignadas()) {
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
        this.individuo = individuo.clone();
        Profesor profesor1 = this.profesorConMenosAsignaturas(this.individuo.getFenotipo2());
        ArrayList<GrupoAux> grupos = new ArrayList<GrupoAux>();
        for (GrupoAsignatura ga : profesor1.getAsignadas()) {
            grupos.add(new GrupoAux(profesor1, ga));
        }
        grupos.sort(this.comparatorAsignatura);
        for (GrupoAux grupo1 : grupos) {
            Profesor profesor2 = this.buscarProfeQueImparta(profesor1, this.individuo.getFenotipo2(), grupo1.grupo.getCodigoAsignatura());
            if (profesor2 == null) {
                return individuo;
            }
            ArrayList<GrupoAux> grupos2 = new ArrayList<GrupoAux>();
            for (GrupoAsignatura ga : profesor2.getAsignadas()) {
                grupos2.add(new GrupoAux(profesor2, ga));
            }
            grupos2.sort(this.comparatorAsignatura);
            GrupoAux grupo2 = (GrupoAux) grupos2.get(0);
            if (!this.verificar(profesor1, grupo1, profesor2, grupo2)) continue;
            return this.buscar(this.individuo);
        }
        return individuo;
    }

    private boolean verificar(Profesor profesor1, GrupoAux grupo1, Profesor profesor2, GrupoAux grupo2) {
        if (grupo1.grupo.getCodigoAsignatura().equals(grupo2.grupo.getCodigoAsignatura())) {
            return false;
        }
        int[] cromosoma = this.individuo.getCromosoma();
        int id1 = grupo1.grupo.getId();
        int id2 = grupo2.grupo.getId();
        int idx1 = -1;
        int idx2 = -1;
        for (int i = 0; i < cromosoma.length; ++i) {
            if (cromosoma[i] == id1) {
                idx1 = i;
            }
            if (cromosoma[i] != id2) continue;
            idx2 = i;
        }
        int aux = cromosoma[idx1];
        cromosoma[idx1] = cromosoma[idx2];
        cromosoma[idx2] = aux;
        int fitness1 = this.individuo.getFitnessAsigProfesor();
        float fitness2 = this.individuo.getFitnessNumHoras();
        this.individuo.evaluar();
        if (this.individuo.esMejor(fitness1, fitness2)) {
            return true;
        }
        return false;
    }

    private Profesor buscarProfeQueImparta(Profesor profesor1, Map<Profesor, Set<GrupoAsignatura>> fenotipo, String codigoAsignatura) {
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

    private Profesor profesorConMenosAsignaturas(Map<Profesor, Set<GrupoAsignatura>> fenotipo) {
        Profesor min = null;
        for (Profesor p : fenotipo.keySet()) {
            if (min != null && p.getNumAsignaturas() >= min.getNumAsignaturas()) continue;
            min = p;
        }
        return min;
    }
}

package main.java.busqueda;

import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.Individuo;
import main.java.model.Grupo;
import main.java.model.Profesor;
import org.omg.SendingContext.RunTime;

import java.util.*;

public class BusquedaIntercambioGrupo extends AbstractBusquedaLocal {

    private Individuo original;
    private Comparator<BusquedaIntercambioGrupo.GrupoAux> comparatorAsignatura;
    private String asignaturaAnterior;
    private Profesor profesorAnterior;

    public BusquedaIntercambioGrupo() {
        this(AlgoritmoGenetico.PROBABILIDAD_BUSQUEDA);
    }

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

    @Override
    public Individuo buscar(Individuo individuo) {
        if (debug) System.out.println(individuo.hashCode() + " -> " + individuo.toString());
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
            for (GrupoAux grupo2 : grupos2) {
                Profesor profesor1Copia = profesor1.clone();
                Profesor profesor2Copia = profesor2.clone();
                if (this.verificar(individuo, profesor1Copia, grupo1, profesor2Copia, grupo2)) {
                    profesor1 = profesor1Copia;
                    profesor2 = profesor2Copia;
                    return this.buscar(individuo);
                } else {

//                    assert noHayRepetidos(individuo) : individuo;
//                    assert noHayRepetidos(profesor1) : individuo;
                    assert noHayRepetidos(profesor1,profesor2) : individuo;
//                    individuo.fenotipo2.get(profesor1).remove(grupo2.grupo);
//                    individuo.fenotipo2.get(profesor1).add(grupo1.grupo);
//                    individuo.fenotipo2.get(profesor2).remove(grupo1.grupo);
//                    individuo.fenotipo2.get(profesor2).add(grupo2.grupo);

                    individuo = original.clone();
//                    assert noHayRepetidos(individuo) /: individuo;
                    assert noHayRepetidos(profesor1,profesor2) : individuo;
//                    assert noHayRepetidos(profesor2) : individuo;

                }
            }
        }
//        if (debug) System.out.println(individuo.hashCode() + " -> " + individuo.toString());
        return individuo;
    }

    private boolean verificar(Individuo individuo, Profesor profesor1, GrupoAux grupo1,
                              Profesor profesor2, GrupoAux grupo2) {
        if (grupo1.grupo.equals(grupo2.grupo))
            throw new RuntimeException("situacion ilegal");
        if (grupo1.grupo.getCodigoAsignatura().equals(grupo2.grupo.getCodigoAsignatura())
                && grupo1.grupo.getHorasComputables(profesor1) == grupo2.grupo.getHorasComputables(profesor2)) {
            return false;
        }

        //Individuo original = original.clone();

//        assert profesor1.getCapacidadInicial() - suma(profesor1) == profesor1.getCapacidad() : profesor1;
//        assert noHayRepetidos(individuo) : individuo;
//        assert noHayRepetidos(profesor1) : individuo;
//        assert noHayRepetidos(profesor2) : individuo;
        assert noHayRepetidos(profesor1, profesor2) : profesor1 + grupo1.toString() + grupo2;

        boolean b1 = profesor1.eliminarGrupo(grupo1.grupo);
        if (b1 == false) return false;
//        assert noHayRepetidos(individuo) : individuo;
//        assert noHayRepetidos(profesor1) : individuo;
//        assert noHayRepetidos(profesor2) : individuo;
        assert noHayRepetidos(profesor1, profesor2) : profesor1 + grupo1.toString() + grupo2;
//        assert profesor1.getHorasClaseAsignadas2() == profesor1.getHorasClaseAsignadas();
//        assert getProfesorFromFenotipo(individuo, profesor1).getHorasClaseAsignadas2() == getProfesorFromFenotipo(individuo, profesor1).getHorasClaseAsignadas();

        boolean b2 = profesor1.asignarGrupo(grupo2.grupo);
        if (b2 == false) return false;
//        assert noHayRepetidos(individuo) : individuo;
//        assert noHayRepetidos(profesor1) : profesor1 + grupo1.toString() + grupo2;
//        assert noHayRepetidos(profesor2) : individuo;
//        assert profesor1.getHorasClaseAsignadas2() == profesor1.getHorasClaseAsignadas();
//        assert getProfesorFromFenotipo(individuo, profesor1).getHorasClaseAsignadas2() == getProfesorFromFenotipo(individuo, profesor1).getHorasClaseAsignadas();


        boolean b3 = profesor2.eliminarGrupo(grupo2.grupo);
        if (b3 == false) return false;
//        assert noHayRepetidos(individuo) : individuo;
//        assert noHayRepetidos(profesor1) : individuo;
//        assert noHayRepetidos(profesor2) : individuo;
        assert noHayRepetidos(profesor1, profesor2) : profesor1 + grupo1.toString() + grupo2;
//        assert profesor1.getHorasClaseAsignadas2() == profesor1.getHorasClaseAsignadas();
//        assert getProfesorFromFenotipo(individuo, profesor1).getHorasClaseAsignadas2() == getProfesorFromFenotipo(individuo, profesor1).getHorasClaseAsignadas();

        boolean b4 = profesor2.asignarGrupo(grupo1.grupo);
        if (b4 == false) return false;
//        assert noHayRepetidos(individuo) : individuo;
//        assert noHayRepetidos(profesor1) : individuo;
//        assert noHayRepetidos(profesor2) : individuo;
        assert noHayRepetidos(profesor1, profesor2) : profesor1 + grupo1.toString() + grupo2;
//        assert profesor1.getHorasClaseAsignadas2() == profesor1.getHorasClaseAsignadas();
//        assert getProfesorFromFenotipo(individuo, profesor1).getHorasClaseAsignadas2() == getProfesorFromFenotipo(individuo, profesor1).getHorasClaseAsignadas();

        individuo.fenotipo2.get(profesor1).remove(grupo1.grupo);
        individuo.fenotipo2.get(profesor1).add(grupo2.grupo);
        individuo.fenotipo2.get(profesor2).remove(grupo2.grupo);
        individuo.fenotipo2.get(profesor2).add(grupo1.grupo);

//        if (!b1 || !b2 || !b3 || !b4) {
        //individuo = this.original;// no se puede realizar el cambio
//            return false;
//        }

//        assert noHayRepetidos(individuo) : individuo;
//        assert noHayRepetidos(profesor1) : individuo;
//        assert noHayRepetidos(profesor2) : individuo;
        assert noHayRepetidos(profesor1, profesor2) : profesor1 + grupo1.toString() + grupo2;
//        assert profesor1.getCapacidadInicial() - suma(profesor1) == profesor1.getCapacidad() : profesor1 + grupo1.toString() + grupo2;
//        assert profesor1.getHorasClaseAsignadas2() == profesor1.getHorasClaseAsignadas();
//        assert getProfesorFromFenotipo(individuo, profesor1).getHorasClaseAsignadas2() == getProfesorFromFenotipo(individuo, profesor1).getHorasClaseAsignadas();
//        assert sumaFenotipo(individuo, profesor1) == suma(profesor1) : profesor1 + grupo1.toString() + grupo2;

        int fitness1 = individuo.getFitnessAsigProfesor();
        float fitness2 = individuo.getFitnessNumHoras();

        individuo.asignarFitnessPorFenotipo(profesor1, profesor2);

//        assert profesor1.getCapacidadInicial() - suma(profesor1) == profesor1.getCapacidad() : profesor1;
//        assert sumaFenotipo(individuo, profesor1) == suma(profesor1) : profesor1;
//
//        assert noHayRepetidos(individuo) : individuo;
        assert noHayRepetidos(profesor1,profesor2) : profesor1;
//        assert noHayRepetidos(profesor2) : profesor2;
//
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

    public static Profesor getProfesorFromFenotipo(Individuo i, Profesor aBuscar) {
        for (Profesor p : i.getFenotipo2().keySet()) {
            if (p.equals(aBuscar))
                return p;
        }
        return null;
    }

    public static boolean noHayRepetidos(Profesor profesor1, Profesor profesor2) {
        Set<Integer> cuenta = new HashSet<>();
        for (Grupo g : profesor1.getAsignadas()) {
            boolean r = cuenta.add(g.getId());
            if (!r) return false;
        }
        for (Grupo g : profesor2.getAsignadas()) {
            boolean r = cuenta.add(g.getId());
            if (!r) return false;
        }
        return true;
    }

    public static boolean noHayRepetidos(Profesor profesor1, List<Profesor >profesores) {
        Set<Integer> cuenta = new HashSet<>();
        for (Grupo g : profesor1.getAsignadas()) {
            boolean r = cuenta.add(g.getId());
            if (!r) return false;
        }
        for (Profesor p:profesores) {
            for (Grupo g : p.getAsignadas()) {
                boolean r = cuenta.add(g.getId());
                if(!p.equals(profesor1)&&!r)
                    return false;
                if(p.equals(profesor1)&&r)
                    return false;
            }
        }
        return true;
    }

    public static boolean noHayRepetidos(Individuo individuo) {
        Set<Integer> cuenta = new HashSet<>();
        for (Profesor p : individuo.fenotipo2.keySet()) {
            for (Grupo g : p.getAsignadas()) {
                boolean r = cuenta.add(g.getId());
                if (!r) return false;
            }
        }
        return true;
    }

    public static boolean noHayRepetidos(Profesor profesor) {
        Set<Integer> cuenta = new HashSet<>();
        for (Grupo g : profesor.getAsignadas()) {
            boolean r = cuenta.add(g.getId());
            if (!r) return false;
        }
        return true;
    }


    public static float sumaFenotipo(Individuo individuo, Profesor profesor1) {
        float suma = 0;
        for (Grupo g : individuo.fenotipo2.get(profesor1)) {
            suma += g.getHorasComputables(profesor1);
        }
        return suma;
    }

    private Float suma(Profesor profesor1) {
        float suma = 0;
        for (Grupo grupo : profesor1.getAsignadas()) {
            suma += grupo.getHorasComputables(profesor1);
        }
        return suma;
    }

    private Profesor buscarProfeQueImparta(Profesor profesor1, Map<Profesor, Set<Grupo>> fenotipo, String codigoAsignatura) {
//        if (this.asignaturaAnterior == null || this.profesorAnterior == null || !this.asignaturaAnterior.equals(codigoAsignatura)
//                || this.profesorAnterior.equals(profesor1)) {
//            this.asignaturaAnterior = codigoAsignatura;
            for (Profesor p : fenotipo.keySet()) {
                if (!p.imparte(codigoAsignatura) || p.equals(profesor1)) continue;
                this.profesorAnterior = p;
                return this.profesorAnterior;
            }
            this.profesorAnterior = null;
            this.asignaturaAnterior = null;
            return null;
//        }
//        return this.profesorAnterior;
    }

    private Profesor profesorConMenosAsignaturas(Map<Profesor, Set<Grupo>> fenotipo) {
        Profesor min = null;
        for (Profesor p : fenotipo.keySet()) {
            if (min != null && p.getNumAsignaturas() >= min.getNumAsignaturas()) continue;
            min = p;
        }
        return min;
    }


    private Profesor profesorConMasAsignaturas(Map<Profesor, Set<Grupo>> fenotipo) {
        Profesor max = null;
        for (Profesor p : fenotipo.keySet()) {
            if (max != null && p.getNumAsignaturas() <= max.getNumAsignaturas()) continue;
            max = p;
        }
        return max;
    }
}

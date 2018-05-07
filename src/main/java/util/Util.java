package main.java.util;

import main.java.genetico.Individuo;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;

import java.util.ArrayList;
import java.util.List;


public class Util {

    public static String arrayToString(List<?> enume, String separador) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < enume.size(); i++) {
            sb.append(enume.get(i).toString());
            if (i != enume.size() - 1)
                sb.append(separador);
        }
        return sb.toString();
    }

    public static String arrayToString(Object[] enume, String separador) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < enume.length; i++) {
            sb.append(enume[i].toString());
            if (i != enume.length - 1)
                sb.append(separador);
        }
        return sb.toString();
    }

    public static String arrayToString(List<?> enume) {
        return arrayToString(enume, ", ");
    }

    public static String arrayToString(Object[] enume) {
        return arrayToString(enume, ", ");
    }

    public static List<Profesor> copyOfProfesor(List<Profesor> ts) {
        List<Profesor> res = new ArrayList<>();
        for (int i = 0; i < ts.size(); i++) {
            res.add(new Profesor(ts.get(i).getId(), ts.get(i).getNombre(), ts.get(i).getCapacidadInicial(),
                    ts.get(i).getBilingue(), ts.get(i).getArea()));
        }
        return res;
    }

    public static List<GrupoAsignatura> copyOfGrupo(List<GrupoAsignatura> ts) {
        List<GrupoAsignatura> res = new ArrayList<>();
        for (int i = 0; i < ts.size(); i++) {
            GrupoAsignatura t = ts.get(i);
            res.add(new GrupoAsignatura(t.getId(),t.getCodigoAsignatura(), t.getNombre(), t.getGrupo(), t.getSemestre(), t.getHorario(),
                    t.getEscuela(), t.getCiudad(), t.getHoras(), t.getBilingue(), t.getAreas()));
        }
        return res;
    }

    public static Profesor[] listToArrayProfesor(List<Profesor> profesors) {
        return profesors.toArray(new Profesor[profesors.size()]);
    }

    public static GrupoAsignatura[] listToArrayGrupo(List<GrupoAsignatura> clases) {
        return clases.toArray(new GrupoAsignatura[clases.size()]);
    }

    public static Individuo createIndividual(int... valores) {
        return new Individuo(valores);
    }

    public static void swap(int[] cromosoma, int pos1, int pos2) {
        int valor = cromosoma[pos1];
        cromosoma[pos1] = cromosoma[pos2];
        cromosoma[pos2] = valor;


    }
}
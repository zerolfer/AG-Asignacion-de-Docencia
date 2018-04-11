package main.java.util;

import main.java.genetico.Individuo;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Util {

    /**
     * Desde 0 hasta "hasta" INCLUSIVE
     *
     * @param hasta
     *
     * @return
     */
    public static int getRandomNumberInclusive(int hasta) {
        return new Random().nextInt(hasta + 1);
    }

    /**
     * Exclusivo, hasta <b>hasta</b>-1
     *
     * @param hasta
     * @return
     */
    public static int getRandomNumber(int hasta) {
        return new Random().nextInt(hasta);
    }

    public static int getRandomNumber(int desde, int hasta) {
        return new Random().nextInt(hasta-desde)+desde;
    }

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
        for (int i=0;i<ts.size();i++) {
            res.add(new Profesor(ts.get(i).getId(), ts.get(i).getNombre(),ts.get(i).getCapacidadInicial(),
                    ts.get(i).getBilingue(),ts.get(i).getArea()));
        }
        return res;
    }

    public static List<GrupoAsignatura> copyOfGrupo(List<GrupoAsignatura> ts) {
        List<GrupoAsignatura> res = new ArrayList<>();
        for (int i=0;i<ts.size();i++) {
            GrupoAsignatura t = ts.get(i);
            res.add(new GrupoAsignatura(t.getId(), t.getNombre(),t.getGrupo(),t.getSemestre(),t.getHorario(),
                    t.getEscuela(),t.getCiudad(),t.getHoras(),t.getBilingue(),t.getAreas()));
        }
        return res;
    }

    public static Profesor[] listToArrayProfesor(List<Profesor> profesors) {
        return profesors.toArray(new Profesor[profesors.size()]);
    }

    public static GrupoAsignatura[] listToArrayGrupo(List<GrupoAsignatura> clases) {
        return clases.toArray(new GrupoAsignatura[clases.size()]);
    }

    public static Individuo createIndividual(int... valores){
        return new Individuo(valores);
    }

    public static float getFoatRandomNumber(int desde, float hasta) {
        return (float) ThreadLocalRandom.current().nextDouble(desde, hasta);

    }
}
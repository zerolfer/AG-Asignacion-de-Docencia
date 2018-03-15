package main.java.util;

import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


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

    public static Profesor[] copyOf(Profesor[] ts) {
        Profesor[] res = new Profesor[ts.length];
        for (int i=0;i<res.length;i++) {
            res[i]=new Profesor(ts[i].getId(), ts[i].getNombre(),ts[i].getCapacidadInicial(),ts[i].getBilingue(),ts[i].getArea());
        }
        return res;
    }

    public static GrupoAsignatura[] copyOf(GrupoAsignatura[] ts) {
        GrupoAsignatura[] res = new GrupoAsignatura[ts.length];
        for (int i=0;i<res.length;i++) {
            GrupoAsignatura t = ts[i];
            res[i]=new GrupoAsignatura(t.getId(), t.getNombre(),t.getGrupo(),t.getSemestre(),t.getHorario(),
                    t.getEscuela(),t.getCiudad(),t.getHoras(),t.getBilingue(),t.getAreas());
        }
        return res;
    }

}
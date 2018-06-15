package main.java.genetico.algoritmos.decodificacion;

import main.java.model.Grupo;
import main.java.model.Profesor;

/**
 * Created by Sergio Florez on 09/03/2018.
 */
public class DecodificacionFiltroGrupo extends Decodificacion {

    @Override
    Profesor getProfesor(Grupo a) {
        Profesor conMenosHorasClase = null;
        for (Profesor p : this.profesores) {
            if (super.checkCapacidad(a, p) && super.checkBilingue(a, p)
                    && super.checkArea(p, a) && super.checkDisponibilidad(a,p))
                if (super.checkSolapamiento(p, a)) {
                    if (checkImparteAsignatura(p, a)) {
                        return p;
                    } else {
                        if (conMenosHorasClase == null) conMenosHorasClase = p;
                        else if (conMenosHorasClase.getHorasClaseAsignadas() > p.getHorasClaseAsignadas())
                            conMenosHorasClase = p;
                    }
                }
        }
        return conMenosHorasClase; // en caso de no haber profesores a cubrir se le asignará un fitness infinito
    }

    private boolean checkImparteAsignatura(Profesor p, Grupo a) {
        for (Grupo imparte : p.getAsignadas())
            if (imparte.getCodigoAsignatura().equals(a.getCodigoAsignatura()))
                return true;
        return false;
    }
}

package main.java.genetico.operadores.decodificacion;

import main.java.model.Grupo;
import main.java.model.Profesor;

/**
 * Created by Sergio Florez on 09/03/2018.
 */
public class DecodificacionFiltroGrupo extends AbstractDecodificacion {

    @Override
    protected Profesor getProfesor(Grupo grupoAsignatura) {
        Profesor conMenosHorasClase = null;
        for (Profesor p : this.profesores) {
            if (p.checkCapacidad(grupoAsignatura) && p.checkBilingue(grupoAsignatura)
                    && p.checkArea(grupoAsignatura) && p.checkDisponibilidad(grupoAsignatura))
                if (p.checkSolapamiento(grupoAsignatura)) {
                    if (p.checkImparteAsignatura(grupoAsignatura)) {
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
}

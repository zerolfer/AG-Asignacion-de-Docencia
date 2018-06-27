package main.java.genetico.operadores.decodificacion;

import main.java.model.Grupo;
import main.java.model.Profesor;

/**
 * Created by Sergio Florez on 09/03/2018.
 */
public class Decodificacion extends AbstractDecodificacion {


    protected Profesor getProfesor(Grupo grupoAsignatura) {
        for (Profesor p : this.profesores) {
            if (p.checkCapacidad(grupoAsignatura) && p.checkBilingue(grupoAsignatura) && p.checkArea(grupoAsignatura) && p.checkDisponibilidad(grupoAsignatura))
                if (p.checkSolapamiento(grupoAsignatura))
                    return p;
        }
        return null; // en caso de no haber profesoresa cubrir se le asignará un fitness infinito
    }


}
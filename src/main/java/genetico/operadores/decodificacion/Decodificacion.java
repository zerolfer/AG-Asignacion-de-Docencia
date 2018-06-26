package main.java.genetico.operadores.decodificacion;

import main.java.genetico.Individuo;
import main.java.io.Settings;
import main.java.model.BD;
import main.java.model.Grupo;
import main.java.model.Horario;
import main.java.model.Profesor;
import main.java.util.Util;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static main.java.busqueda.BusquedaIntercambioGrupo.getProfesorFromFenotipo;
import static main.java.busqueda.BusquedaIntercambioGrupo.noHayRepetidos;

/**
 * Created by Sergio Florez on 09/03/2018.
 */
public class Decodificacion extends AbstractDecodificacion {


    protected Profesor getProfesor(Grupo a) {
        for (Profesor p : this.profesores) {
            if (p.checkCapacidad(a) && p.checkBilingue(a) && p.checkArea(a) && p.checkDisponibilidad(a))
                if (p.checkSolapamiento(a))
                    return p;
        }
        return null; // en caso de no haber profesoresa cubrir se le asignará un fitness infinito
    }


}
package main.java.busqueda;

import main.java.genetico.Individuo;
import main.java.model.Grupo;
import main.java.model.Profesor;

public class BusquedaIntercambioGrupoV2 extends  AbstractBusquedaLocal {

    @Override
    public Individuo buscar(Individuo individuo){

        Profesor profesor1 = profesorConMenosAsignaturas(individuo);
        for (Grupo grupo1 : profesor1.getAsignadas()){
            Profesor profesor2 = profesorQueImparta(individuo, grupo1.getCodigoAsignatura());

            // si no hay profesores que impartan la asignatura, no hay mejora posible
            if(profesor2==null) return individuo;

            for (Grupo grupo2 : profesor2.getAsignadas()) {

                // variables auxiliares para la verificacion
                Profesor profesor1Copia= profesor1.clone();
                Profesor profesor2Copia= profesor2.clone();
                if (verificarIntercambio(profesor1Copia, grupo1, profesor2Copia, grupo2))
                    return null;
                else{
//                    pro
                }
            }

        }
return null;



    }

    private boolean verificarIntercambio(Profesor profesor1, Grupo grupo1, Profesor profesor2, Grupo grupo2) {
        if (grupo1.equals(grupo2))
            throw new RuntimeException("situacion ilegal");
        if (grupo1.getCodigoAsignatura().equals(grupo2.getCodigoAsignatura())
                && grupo1.getHorasComputables(profesor1) == grupo2.getHorasComputables(profesor2)) {
            return false;
        }
        boolean b1 = profesor1.eliminarGrupo(grupo1);
        if (b1 == false) return false;

        boolean b2 = profesor1.asignarGrupo(grupo2); //TODO: mirar restricciones de horarios y disponibilidad
        if (b2 == false) return false;

        boolean b3 = profesor2.eliminarGrupo(grupo2);
        if (b3 == false) return false;

        boolean b4 = profesor2.asignarGrupo(grupo1);
        if (b4 == false) return false;
return true; // TODO delete me
    }

    private Profesor profesorQueImparta(Individuo individuo, String codigoAsignatura) {
        for (Profesor p : individuo.getFenotipo().values()) {
            if (p.checkImparteAsignatura(codigoAsignatura))
                return p;
        }
        return null;
    }

    private Profesor profesorConMenosAsignaturas(Individuo individuo) {
        Profesor result=null;
        for (Profesor p : individuo.getFenotipo().values()) {
            if(result!=null && p.getNumAsignaturas()<result.getNumAsignaturas())
                result=p;
        }
        return result; // retorna una referencia al FENOTIPO
    }
}

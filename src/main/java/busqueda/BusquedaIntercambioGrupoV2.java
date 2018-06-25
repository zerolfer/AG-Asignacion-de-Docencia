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
                Profesor profesor1Copia= profesor1.clone();
                Profesor profesor2Copia= profesor2.clone();
                if (verificarIntercambio(grupo1))
                    return null;
                else{
//                    pro
                }
            }

        }
return null;



    }

    private boolean verificarIntercambio(Grupo grupo1) {
        return false;
    }

    private Profesor profesorQueImparta(Individuo individuo, String codigoAsignatura) {
        for (Profesor p : individuo.getFenotipo2().keySet()) {
            if (p.imparte(codigoAsignatura))
                return p;
        }
        return null;
    }

    private Profesor profesorConMenosAsignaturas(Individuo individuo) {
        Profesor result=null;
        for (Profesor p : individuo.getFenotipo2().keySet()) {
            if(result!=null && p.getNumAsignaturas()<result.getNumAsignaturas())
                result=p;
        }
        return result; // retorna una referencia al FENOTIPO
    }
}

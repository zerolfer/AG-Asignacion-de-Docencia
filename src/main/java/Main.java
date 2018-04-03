package main.java;

import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.algoritmos.creacion.CreacionAleatoria;
import main.java.genetico.algoritmos.cruce.CruceOrderBased;
import main.java.genetico.algoritmos.cruce.CrucePositionBased;
import main.java.genetico.algoritmos.mutacion.MutacionIntercambio;
import main.java.genetico.algoritmos.reemplazo.ReemplazoGeneracional;
import main.java.genetico.algoritmos.seleccion.SeleccionAleatoria;
import main.java.genetico.algoritmos.seleccion.SeleccionRuleta;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;

import java.util.List;

import static main.java.genetico.AlgoritmoGenetico.PROBABILIDAD_CRUCE;
import static main.java.genetico.AlgoritmoGenetico.PROBABILIDAD_MUTACION;

public class Main {
    public static void main(String args[]){

//        List<Profesor> p = CSVReader.CsvLoadProfesores();
//        List<GrupoAsignatura> a = CSVReader.CsvLoadAsignaturas();

        AlgoritmoGenetico genetico=new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionRuleta(),
                new CruceOrderBased(PROBABILIDAD_CRUCE),
                new MutacionIntercambio(PROBABILIDAD_MUTACION),
                new ReemplazoGeneracional());
        genetico.iniciar();

        genetico=new AlgoritmoGenetico(
                new CreacionAleatoria(),
                new SeleccionAleatoria(),
                new CrucePositionBased(PROBABILIDAD_CRUCE),
                new MutacionIntercambio(PROBABILIDAD_MUTACION),
                new ReemplazoGeneracional());
        genetico.iniciar();

    }
}

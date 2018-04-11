package main.java.genetico;


import main.java.genetico.algoritmos.AlgoritmoDecodificacion;
import main.java.genetico.algoritmos.Decodificacion;
import main.java.genetico.algoritmos.creacion.AlgoritmoCreacion;
import main.java.genetico.algoritmos.creacion.CreacionAleatoria;
import main.java.genetico.algoritmos.cruce.AlgoritmoCruce;
import main.java.genetico.algoritmos.cruce.CruceOrderBased;
import main.java.genetico.algoritmos.mutacion.AlgoritmoMutacion;
import main.java.genetico.algoritmos.mutacion.MutacionIntercambio;
import main.java.genetico.algoritmos.reemplazo.AlgoritmoReemplazo;
import main.java.genetico.algoritmos.reemplazo.ReemplazoGeneracional;
import main.java.genetico.algoritmos.seleccion.AlgoritmoSeleccion;
import main.java.genetico.algoritmos.seleccion.SeleccionAleatoria;
import main.java.model.BD;

import java.util.List;

public class AlgoritmoGenetico {

    // VARIABLES DE ENTRADA DEL GENÉTICO
    private static final int POPULATION_SIZE = 100;
    public static final float PROBABILIDAD_CRUCE = 0.7f;
    public static final float PROBABILIDAD_MUTACION = 0.5f;
    private static final int NUMERO_GENERACIONES = 1000;

    // INFORMACIÓN DEL PROBLEMA
    /*private static List<GrupoAsignatura> asignaturas = new ArrayList<>();
    private static List<Profesor> profesores = new ArrayList<>();*/

    // ALGORITMOS
    AlgoritmoCreacion creacion;
    AlgoritmoSeleccion seleccion;
    AlgoritmoCruce cruce;
    AlgoritmoMutacion mutacion;
    AlgoritmoReemplazo reemplazo;

    AlgoritmoDecodificacion decodificacion;


    public AlgoritmoGenetico(AlgoritmoCreacion creator, AlgoritmoSeleccion seleccion, AlgoritmoCruce cruce,
                             AlgoritmoMutacion mutacion, AlgoritmoReemplazo reemplazo) {
        this.creacion = creator;
        this.seleccion = seleccion;
        this.cruce = cruce;
        this.mutacion = mutacion;
        this.reemplazo = reemplazo;
        this.decodificacion = new Decodificacion();
    }

    public AlgoritmoGenetico() {
        this(new CreacionAleatoria(), new SeleccionAleatoria(), new CruceOrderBased(PROBABILIDAD_CRUCE),
                new MutacionIntercambio(PROBABILIDAD_MUTACION), new ReemplazoGeneracional());
    }

    public void iniciar(/*List<GrupoAsignatura> asignaturas, List<Profesor> profesores*/) {
        /*this.asignaturas = asignaturas;
        this.profesores = profesores;*/
//        ordenarAsignaturas();
        ordenarProfesores();
        genetico();
    }

    private void ordenarProfesores() {
        BD.getProfesores().sort(BD.comparatorProfesores);
    }

    private void ordenarAsignaturas() {
        BD.getAsignaturas().sort(BD.comparatorAsignaturas);
    }


    private void genetico() {

        Generacion generacion = creacion.createPopulation(POPULATION_SIZE);
        int numGeneraciones = 1;

        do {

            generacion.evaluar();

            List<Individuo[]> padres = seleccion.aplicar(generacion);
            List<Individuo[]> hijos = cruce.aplicar(padres);

            mutar(hijos);

            evaluar(hijos);

            int sizeAnterior = generacion.size();
            generacion = reemplazo.aplicar(padres, hijos);
            numGeneraciones++;

            assert sizeAnterior == generacion.size();

        } while (numGeneraciones <= NUMERO_GENERACIONES);

        System.out.println("Mejor resultado: \n" + obtenerMejor(generacion).toStringFull());
//            System.out.println("Mejor resultado: \n" + obtenerMejor(generacion).toString());
    }

    private Individuo obtenerMejor(Generacion generacion) {
        Individuo[] genotipo = generacion.getGenotipo();
        Individuo mejor = genotipo[0];
        for (Individuo indi : genotipo) {
            if (mejor.getFitnessAsigProfesor() == indi.getFitnessAsigProfesor()) {
                if (indi.getFitnessNumHoras() < mejor.getFitnessNumHoras())
                    mejor = indi;
            } else if (indi.getFitnessAsigProfesor() < mejor.getFitnessAsigProfesor())
                mejor = indi;
        }
        return mejor;
    }

    private void mutar(List<Individuo[]> individuos) {
        for (Individuo[] par : individuos)
            for (Individuo indi : par)
                mutacion.mutar(indi);
    }

    private void evaluar(List<Individuo[]> individuos) {
        for (Individuo[] par : individuos)
            for (Individuo indi : par)
                indi.evaluar();
    }


}

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
import main.java.genetico.algoritmos.reemplazo.ReemplazoTorneoPH;
import main.java.genetico.algoritmos.seleccion.AlgoritmoSeleccion;
import main.java.genetico.algoritmos.seleccion.SeleccionAleatoria;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;

import java.util.ArrayList;
import java.util.List;

public class AlgoritmoGenetico {

    // VARIABLES DE ENTRADA DEL GENÉTICO
    private static final int POPULATION_SIZE = 2;

    // INFORMACIÓN DEL PROBLEMA
    private static List<GrupoAsignatura> asignaturas = new ArrayList<>();
    private static List<Profesor> profesores = new ArrayList<>();

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
        this(new CreacionAleatoria(), new SeleccionAleatoria(), new CruceOrderBased(),
                new MutacionIntercambio(), new ReemplazoTorneoPH());
    }

    public void iniciar(List<GrupoAsignatura> asignaturas, List<Profesor> profesores) {
        this.asignaturas = asignaturas;
        this.profesores = profesores;
        genetico();
    }


    private void genetico() {

        Generacion generacion = creacion.createPopulation(POPULATION_SIZE);

        generacion.evaluar(profesores.toArray(new Profesor[profesores.size()]),
                asignaturas.toArray(new GrupoAsignatura[asignaturas.size()]));

        Individuo[] padres = seleccion.aplicar(generacion);

//        for (Individuo c : codificacion()) {
//            AlgoritmoGenetico.decodificar(c);
//        }
    }


}

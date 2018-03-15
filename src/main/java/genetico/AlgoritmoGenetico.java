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

   /* public static void decodificar(Individuo individuo) {// O(n^2)
        new Decodificacion().aplicar(individuo,
                profesores.toArray(new Profesor[profesores.size()]),
                asignaturas.toArray(new GrupoAsignatura[asignaturas.size()]));
        if (individuo.getFitnessNumHoras() < Float.MAX_VALUE)
            System.out.println(individuo);
//        System.out.println(individuo);
//        int n = 0;
//        for (int idAsignatura : individuo.getCromosoma()) {
//            // las asignaturas no tienen por que estar ordenadas por ID
//            GrupoAsignatura asignatura = getAsignaturaById(idAsignatura);
//            Profesor profesor = getProfesor(asignatura);
//            if (profesor == null) {
//                n++;
//                continue;
//            }
//            profesor.getAsignadas().add(asignatura);
//            profesor.setCapacidad(profesor.getCapacidad() - asignatura.getHoras());
//        }
//        individuo.asignarFitness(profesores);
//        System.out.println(Util.arrayToString(profesores, "\n"));
//        System.out.println(n + " asignaturas no han sido sasignadas");
//        System.out.println(individuo.fitnessToString());
    }

//    private static Profesor getProfesor(GrupoAsignatura a) {
//        for (Profesor p : profesores) {
//            if (p.getCapacidad() >= a.getHoras() && Arrays.binarySearch(a.getAreas(), p.getArea()) != -1)
//                if (p.getBilingue() || !a.getBilingue())
//                    return p;
//        }
//        return null; //FIXME ??? si no hay profesores a cubrir qué hacer?¿
//    }
//
//    private static GrupoAsignatura getAsignaturaById(int idAsignatura) {
//        for (GrupoAsignatura a : asignaturas) {
//            if (a.getId() == idAsignatura)
//                return a;
//        }
//        throw new RuntimeException("No existe ID de asignatura " + idAsignatura);
//    }
*/

}

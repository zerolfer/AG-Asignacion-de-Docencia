package main.java.genetico;

import main.java.genetico.algoritmos.AlgoritmoDecodificacion;
import main.java.genetico.algoritmos.Decodificacion;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;
import main.java.util.Util;

import java.util.List;

public class Generacion {
    Individuo[] genotipo;
    AlgoritmoDecodificacion decodificacion;

//    public Generacion(Individuo[] individuos) {
//        this.genotipo = individuos;
//        this.decodificacion = new Decodificacion();
//
//    }

    // equivalente:
    // public Generacion(Individuo[] individuos) {
    public Generacion(Individuo... individuos) {
        this.genotipo = individuos;
        this.decodificacion = new Decodificacion();

    }

    public Individuo[] getGenotipo() {
        return genotipo;
    }

    public int size() {
        return this.genotipo.length;
    }

    public Individuo getIndividuo(int posicion) {
        return genotipo[posicion];
    }

    public void evaluar(List<Profesor> profesores, List<GrupoAsignatura> asignaturas) {
        if (Decodificacion.debug) System.out.println("DECODIFICACION Y EVALUACIÓN:");
        for (Individuo individuo : genotipo) {
            decodificacion.aplicar(individuo, profesores, asignaturas);
            if (Decodificacion.debug) System.out.println(individuo);
        }

        if (Decodificacion.debug) System.out.println();

    }

    public Individuo getRandomIndividual() {
        return this.genotipo[Util.getRandomNumber(size())];
    }
}

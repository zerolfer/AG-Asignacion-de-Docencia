package main.java.genetico;

import main.java.genetico.algoritmos.Decodificacion;
import main.java.util.RandomManager;

public class Generacion {
    Individuo[] genotipo;

//    public Generacion(Individuo[] individuos) {
//        this.genotipo = individuos;
//        this.decodificacion = new Decodificacion();
//
//    }

    // equivalente:
    // public Generacion(Individuo[] individuos) {
    public Generacion(Individuo... individuos) {
        this.genotipo = individuos;
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

    public void evaluar() {
        if (Decodificacion.debug) System.out.println("DECODIFICACION Y EVALUACIÓN:");
        for (Individuo individuo : genotipo) {
            individuo.evaluar();
            if (Decodificacion.debug) System.out.println(individuo);
        }

        if (Decodificacion.debug) System.out.println();

    }

    public Individuo getRandomIndividual() {
        return this.genotipo[RandomManager.getInstance().getRandomNumber(size())];
    }
}

package main.java.genetico;

import main.java.genetico.algoritmos.decodificacion.Decodificacion;
import main.java.util.RandomManager;

import java.util.Arrays;

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

    public float[] obtenerFitnessMedio() {
        float[] result = new float[2];
        for (Individuo ind : this.genotipo) {
            result[0] += ind.getFitnessAsigProfesor();
            result[1] += ind.getFitnessNumHoras();
        }
        result[0]/=size();
        result[1]/=size();

        return result;
    }

    @Override
    public Generacion clone() {
        Individuo[] res = new Individuo[size()];
        for (int i = 0; i < size(); i++) {
            Individuo t = getIndividuo(i);
            res[i]=new Individuo(t.getCromosoma());
        }
        return new Generacion(res);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Generacion that = (Generacion) o;
        return Arrays.equals(genotipo, that.genotipo);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genotipo);
    }
}

package main.java.genetico;

import main.java.genetico.algoritmos.AlgoritmoDecodificacion;
import main.java.genetico.algoritmos.Decodificacion;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;

public class Generacion {
    Individuo[] genotipo;
    AlgoritmoDecodificacion decodificacion;

    public Generacion(Individuo[] individuos) {
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

    public void evaluar(Profesor[] profesores, GrupoAsignatura[] asignaturas) {

        for (Individuo individuo : genotipo) {
            decodificacion.aplicar(individuo, profesores, asignaturas);
        }

    }
}

package main.java.genetico.algoritmos.reemplazo;

import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.Generacion;
import main.java.genetico.Individuo;
import main.java.util.RandomManager;

import java.util.List;

/**
 * Created by Sergio Florez on 15/03/2018.
 */
public class ReemplazoTorneo implements AlgoritmoReemplazo {

    private int torneoSize;

    public ReemplazoTorneo() {
        this(4); // por defecto es 4
    }


    public ReemplazoTorneo(int torneoSize) {
        this.torneoSize = torneoSize;
    }

    @Override
    public Generacion aplicar(List<Individuo[]> padres, List<Individuo[]> hijos) {
        Individuo[] union = agrupar(padres, hijos);
        Individuo[] resultados = new Individuo[AlgoritmoGenetico.POPULATION_SIZE];
        for (int i = 0; i < AlgoritmoGenetico.POPULATION_SIZE; i++) {
            resultados[i] = torneo(union);
        }
        return new Generacion(resultados);
    }

    private Individuo[] agrupar(List<Individuo[]> padres, List<Individuo[]> hijos) {
        Individuo[] result = new Individuo[padres.size() * 2 + hijos.size() * 2];
        int i = 0;
        for (Individuo[] par : padres)
            for (Individuo padre : par)
                result[i++] = padre;
        for (Individuo[] par : hijos)
            for (Individuo hijo : par)
                result[i++] = hijo;
        return result;
    }

    private Individuo torneo(Individuo[] union) {
        Individuo elMejor = union[RandomManager.getInstance().getRandomNumber(union.length)];
        for (int i = 1; i < torneoSize; i++) {
            Individuo candidato = union[RandomManager.getInstance().getRandomNumber(union.length)];
            if (candidato.compareTo(elMejor) > 0) // si es mejor el candidato
                elMejor = candidato;
        }
        return elMejor;
    }


}

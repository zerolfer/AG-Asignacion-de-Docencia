package main.java.genetico.operadores.mutacion;

import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.Individuo;
import main.java.util.RandomManager;
import main.java.util.Util;

import java.util.List;

public abstract class AbstractMutacion implements OperadorMutacion {

    protected static final boolean debug = false;
    protected final float probabilidad;

    public AbstractMutacion(){
        this.probabilidad=AlgoritmoGenetico.PROBABILIDAD_MUTACION;
    }
    public AbstractMutacion(final float probabilidad) {
        Util.checkProbabilidadValida(probabilidad);
        this.probabilidad = probabilidad;
    }

    @Override
    public void mutar(List<Individuo[]> individuos) {
        for (Individuo[] par : individuos) {
            for (Individuo individuo : par) {
                mutar(individuo);
            }

        }
    }

    @Override
    public void mutar(Individuo individuo) {
        if (RandomManager.getInstance().getRandomProbability() <= probabilidad) {
            if (debug) System.out.println("MUTACION:\n\tAntes: \t " + individuo);
            int pos1 = RandomManager.getInstance().getRandomNumber(individuo.size());
            int pos2;
            do {
                pos2 = RandomManager.getInstance().getRandomNumber(individuo.size());
            } while ((pos1 == pos2));

            // template method
            estrategiaDeMutacion(individuo, pos1, pos2);

            individuo.setYaEvaluado(false);

            if (debug) System.out.println("\tDespues: " + individuo);
        }
    }

    /**
     * <p>Núcleo de la funcionalidad del operador de mutacion,
     * realiza la transformación pertinente según el tipo de
     * algoritmo.</p>
     *
     * <p><code>TEMPLATE METHOD</code><br/>
     *
     * Para crear un nuevo operador de mutación,
     * implementar este método en una subclase.</p>
     *
     * @param individuo
     * @param pos1
     * @param pos2
     */
    protected abstract void estrategiaDeMutacion(Individuo individuo, int pos1, int pos2);

    @Override
    public Float getProbabilidad(){
        return this.probabilidad;
    }
}

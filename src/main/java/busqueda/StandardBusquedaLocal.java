package main.java.busqueda;

import main.java.genetico.Individuo;
import main.java.util.RandomManager;

public abstract class StandardBusquedaLocal implements BusquedaLocal {

    private float probabilidad;
    public boolean debug=true; // imprime por consola el hashCode y el toString de cada iteración

    public StandardBusquedaLocal(float probabilidad) {
        if (probabilidad < 0 || probabilidad > 1)
            throw new RuntimeException("introducida probabilidad de busqueda local inválida");
        this.probabilidad = probabilidad;
    }

    public float getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(float probabilidad) {
        this.probabilidad = probabilidad;
    }

    @Override
    public Individuo aplicar(Individuo individuo) {
        if (RandomManager.getInstance().getRandomProbability() <= probabilidad)
            return buscar(individuo);
        return individuo;
    }

    protected Individuo buscar(Individuo individuo) {
        Individuo vecino;
        do {
            vecino = generarVecinos(individuo);
            if (vecino.esMejor(individuo))
                individuo = vecino; // nos quedamos con el mejor
        } while (individuo.equals(vecino));
        return vecino;
    }

    protected Individuo generarVecinos(Individuo individuo) {
        // SOBREESCRIBIR EN LAS CLASES HIJAS
        return individuo;
    }

}

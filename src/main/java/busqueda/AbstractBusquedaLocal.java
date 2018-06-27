package main.java.busqueda;

import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.Individuo;
import main.java.io.Settings;
import main.java.util.RandomManager;
import main.java.util.Util;

public abstract class AbstractBusquedaLocal implements BusquedaLocal {

    private float probabilidad;
    public static boolean debug=Settings.getBoolean("debug.busqueda.mensajes"); // imprime por consola el hashCode y el toString de cada iteración
    protected boolean huboCambio=false;

    public AbstractBusquedaLocal() {
        this.probabilidad=AlgoritmoGenetico.PROBABILIDAD_BUSQUEDA;
    }

    public AbstractBusquedaLocal(float probabilidad) {
        Util.checkProbabilidadValida(probabilidad);
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
        if (RandomManager.getInstance().getRandomProbability() <= probabilidad) {
            Individuo result = buscar(individuo);
            if (debug&&huboCambio){
                System.out.println();
                huboCambio=false;
            }
            return result;

        }
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

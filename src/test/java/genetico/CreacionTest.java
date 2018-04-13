package test.java.genetico;

import main.java.genetico.Individuo;
import main.java.genetico.algoritmos.creacion.AlgoritmoCreacion;
import main.java.genetico.algoritmos.creacion.CreacionAleatoria;
import main.java.model.BD;
import main.java.util.RandomManager;
import org.junit.Assert;
import org.junit.Test;

public class CreacionTest {

    AlgoritmoCreacion creator = new CreacionAleatoria();

    @Test
    public void semillaTest() {
        BD.getProfesores();
        for (int i = 0; i < 1; i++) {

            RandomManager.seed = i;
            Individuo individuo1 = creator.generateIndividual();
            Individuo individuo2 = creator.generateIndividual();
            RandomManager.destroyInstance();

            Individuo individuo3 = creator.generateIndividual();
            RandomManager.destroyInstance();

            Assert.assertNotEquals(individuo1, individuo2);
            Assert.assertEquals(individuo1, individuo3);
        }
    }
}

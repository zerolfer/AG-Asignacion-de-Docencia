package test.java.genetico;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CreacionTest.class,
        CruceTest.class,
        DecodificarTest.class,
        FitnessTest.class,
        GeneticoTest.class,
        MutacionTest.class,
        SeleccionTest.class
})
public class TestSuite {
}

package test.java.model;

import main.java.genetico.Individuo;
import main.java.model.Grupo;
import main.java.model.Horario;
import main.java.model.Profesor;
import main.java.util.Util;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CloneTest {

    DateFormat formatter = Horario.horaFormat;
    Profesor profe = new Profesor(1, "profe1", 360f, true, "Area1",
            Horario.disponibilidadTotal);
    List<Horario> horarios = Arrays.asList(new Horario('L', "14:00", "20:00"));
    Grupo grupo1 = new Grupo(1, "A1", "Asigna1", "PL1", 1, horarios,
            "EII", "OVD", 30f, true, new String[]{"Area1"});
    Grupo grupo2=new Grupo(2, "A1", "Asigna1", "PL2", 1, horarios,
            "EII", "OVD", 3f, true, new String[]{"Area1"});

    @Test
    public void profesorTest() throws ParseException {

        profe.asignarGrupo(grupo1);

        Profesor clon = profe.clone();

        assertTrue(profe.equals(clon));
        assertTrue(clon.equals(profe));
        assertTrue(profe.getBilingue() == clon.getBilingue());
        assertTrue(profe.getArea() == clon.getArea());
        assertTrue(profe.getNombre() == clon.getNombre());
        assertTrue(profe.getAsignadas().equals(clon.getAsignadas()));

        profe.asignarGrupo(grupo2);
//        profe.setCapacidad(profe.getCapacidad()-20);

        assertTrue(profe.getCapacidad() != clon.getCapacidad());
        assertEquals(360 - (30 * 1.5) - (3 * 1.5), profe.getCapacidad(), 0);
        assertEquals(360 - (30 * 1.5), clon.getCapacidad(), 0);
//        assertTrue(profe.==clon.);
    }

    @Test
    public void individuoTest() {

        Individuo i = Util.createIndividual(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        i.fenotipo2 = new HashMap<>();
        Set<Grupo> grupos = new HashSet<>();
        grupos.add(grupo1);
        i.fenotipo2.put(profe, grupos);

        Individuo clon = i.clone();
        grupos.add(grupo2);
        clon.fenotipo2.put(profe, grupos);
        assertTrue(clon.getFenotipo2().get(profe).contains(grupo2));
        assertFalse(i.getFenotipo2().get(profe).contains(grupo2));
    }


}

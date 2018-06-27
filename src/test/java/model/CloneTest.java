package test.java.model;

import main.java.genetico.Individuo;
import main.java.model.Grupo;
import main.java.model.Horario;
import main.java.model.Profesor;
import main.java.util.Util;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CloneTest {

    DateFormat formatter = Horario.horaFormat;
    Profesor profe = new Profesor(1, "profe1", 360f, true, "Area1",
            Horario.disponibilidadTotal);
    List<Horario> horarios1 = Arrays.asList(new Horario('L', "14:00", "20:00"));
    List<Horario> horarios2 = Arrays.asList(new Horario('M', "14:00", "20:00"));
    Grupo grupo1 = new Grupo(1, "A1", "Asigna1", "PL1", 1, horarios1,
            "EII", "OVD", 30f, true, new String[]{"Area1"});
    Grupo grupo2=new Grupo(2, "A1", "Asigna1", "PL2", 1, horarios2,
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

        assertTrue(profe.asignarGrupo(grupo2));
//        profe.setCapacidad(profe.getCapacidad()-20);

        assertTrue(profe.getCapacidad() != clon.getCapacidad());
        assertEquals(360 - (30 * 1.5) - (3 * 1.5), profe.getCapacidad(), 0);
        assertEquals(360 - (30 * 1.5), clon.getCapacidad(), 0);
//        assertTrue(profe.==clon.);
    }

    @Test
    public void individuoTest() {

        Individuo i = Util.createIndividual(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        profe.asignarGrupo(grupo1);
        i.getFenotipo().put(profe.getId(), profe);

        Individuo clon = i.clone();

//        clon.getFenotipo().put(profe.getId(), profe);

        // comparar referencias de cada Objeto
        assertFalse(clon==i);
        assertFalse(clon.getFenotipo()==i.getFenotipo());
        assertFalse(clon.getFenotipo().get(profe.getId())==i.getFenotipo().get(profe.getId()));
        assertFalse(clon.getFenotipo().get(profe.getId()).getAsignadas()==i.getFenotipo().get(profe.getId()).getAsignadas());
        assertFalse(clon.getFenotipo().get(profe.getId()).getAsignadas().iterator().next()==i.getFenotipo().get(profe.getId()).getAsignadas().iterator().next());

        clon.getFenotipo().get(profe.getId()).asignarGrupo(grupo2);

        assertTrue(i.getFenotipo().get(profe.getId()).getAsignadas().contains(grupo1));
        assertTrue(clon.getFenotipo().get(profe.getId()).getAsignadas().contains(grupo1));

        assertTrue(clon.getFenotipo().get(profe.getId()).getAsignadas().contains(grupo2));
        assertFalse(i.getFenotipo().get(profe.getId()).getAsignadas().contains(grupo2));

        profe.asignarGrupo(grupo2);
    }


}

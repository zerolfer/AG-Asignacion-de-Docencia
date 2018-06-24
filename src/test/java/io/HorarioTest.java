package test.java.io;

import main.java.model.Horario;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HorarioTest {

    DateFormat formatter = Horario.horaFormat;

    @Test
    public void franjaHorariaTest() throws ParseException {

        Horario total = Horario.disponibilidadTotal;

        Horario h1 = new Horario('-', hora("00:00"), hora("2:00"));
        Horario h2 = new Horario('-', hora("02:00"), hora("14:00"));
        Horario h3 = new Horario('-', hora("14:00"), hora("23:59"));
        Horario h4 = new Horario('-', hora("23:59"), hora("00:00"));
        Horario h5 = new Horario('-', hora("00:00"), hora("23:59"));

        assertTrue(estaEnRango(total, h1));
        assertTrue(estaEnRango(total, h2));
        assertTrue(estaEnRango(total, h3));
        assertTrue(estaEnRango(total, h4));
        assertTrue(estaEnRango(total, h5));
    }

    private Timestamp hora(String s) throws ParseException {
        return new Timestamp(formatter.parse(s).getTime());

    }

    /**
     * verifica si el horario h1 es subconjunto de h2
     *
     * @param h1
     * @param h2
     */
    private boolean estaEnRango(Horario h1, Horario h2) {
        // IMPORTANTE: NO SERVIRIA ESTO, YA QUE NO TIENE EN CUENTA SI ALGUNA HORA COINCIDE
//        h1.getHoraInicio().after(h2.getHoraInicio());
//        h1.getHoraFin().before(h2.getHoraFin());

        boolean b1 = h2.getHoraInicio().compareTo(h1.getHoraInicio()) >= 0;
        boolean b2 = h2.getHoraFin().compareTo(h1.getHoraFin()) <= 0;

        return b1 && b2;
    }


}

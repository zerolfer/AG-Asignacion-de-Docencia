package test.java.genetico;

import main.java.genetico.Individuo;
import main.java.genetico.algoritmos.decodificacion.AlgoritmoDecodificacion;
import main.java.genetico.algoritmos.decodificacion.Decodificacion;
import main.java.model.Grupo;
import main.java.model.Horario;
import main.java.model.Profesor;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class DecodificarTest {

    private AlgoritmoDecodificacion decoder = new Decodificacion();
    private List<Profesor> profesores;
    private List<Grupo> asignaturas;

/*
    @Before
    public void inicializar() {
        profesores = CSVReader.CsvLoadProfesores();
        asignaturas = CSVReader.CsvLoadAsignaturas();
    }
*/

    @Test
    public void test1() {
        Individuo individuo = new Individuo(
                new int[]{41, 45, 4, 9, 38, 21, 60, 59, 63, 27, 12, 67, 82, 58, 78, 3, 2, 11, 55, 34, 81, 64, 1,
                        37, 80, 15, 49, 30, 47, 18, 35, 24, 23, 51, 39, 0, 33, 8, 44, 70, 68, 7, 19, 6, 50, 43,
                        42, 71, 14, 66, 79, 29, 75, 72, 73, 76, 74, 56, 62, 5, 65, 54, 52, 53, 69, 28, 46, 20, 10,
                        32, 48, 36, 17, 16, 57, 26, 25, 13, 61, 77, 31, 22, 40});
        decoder.aplicar(individuo);

        assertEquals("el fitnes no era el esperado", 9, individuo.getFitnessAsigProfesor());
    }

    @Test
    public void checkSolapamientoTest() throws ParseException {
        Profesor profe1 = new Profesor(1, "Profe1", 200f, true, "A");
        Profesor profe2 = new Profesor(2, "Profe2", 200f, true, "A");

        // importante: H mayuscula para indicarla en formato 24 horas
        // en caso contrario las 12:00 las contaria por las 12 de la noche (pm)
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        Timestamp t12 = new Timestamp(dateFormat.parse("12:00").getTime());
        Timestamp t13 = new Timestamp(dateFormat.parse("13:00").getTime());
        Timestamp t14 = new Timestamp(dateFormat.parse("14:00").getTime());
        Timestamp t14_30 = new Timestamp(dateFormat.parse("14:30").getTime());
        Timestamp t15 = new Timestamp(dateFormat.parse("15:00").getTime());
        Timestamp t18 = new Timestamp(dateFormat.parse("18:00").getTime());
        Timestamp t10 = new Timestamp(dateFormat.parse("10:00").getTime());
        Timestamp t11_30 = new Timestamp(dateFormat.parse("11:30").getTime());
        Timestamp t11_50 = new Timestamp(dateFormat.parse("11:50").getTime());

        List<Horario> h1 = new ArrayList<>();
        h1.add(new Horario('L', t12, t14));

        List<Horario> h2 = new ArrayList<>();
        h2.add(new Horario('L', t13, t15));

        List<Horario> h3 = new ArrayList<>();
        h3.add(new Horario('L', t14_30, t15));

        List<Horario> h4 = new ArrayList<>();
        h4.add(new Horario('L', t10, t13));

        List<Horario> h5 = new ArrayList<>();
        h5.add(new Horario('L', t10, t11_30));

        List<Horario> h5_2 = new ArrayList<>();
        h5_2.add(new Horario('L', t10, t11_50));

        List<Horario> h6 = new ArrayList<>();
        h6.add(new Horario('L', t15, t18));


        // grupo pivote
        Grupo grupo1 = new Grupo(1, "C", "CCC", "grupo1", 1, h1,
                "EII", "Oviedo", 2f, false, new String[]{"A"});
        profe1.asignarGrupo(grupo1);

        //grupo identico
        Grupo grupo2 = new Grupo(1, "C", "CCC", "grupo2", 1, h1,
                "EII", "Oviedo", 2f, false, new String[]{"A"});

        //grupo solapado por delante
        Grupo grupo3 = new Grupo(1, "C", "CCC", "grupo3", 1, h2,
                "EII", "Oviedo", 2f, false, new String[]{"A"});

        //grupo por delante sin solapar
        Grupo grupo4 = new Grupo(1, "C", "CCC", "grupo4", 1, h3,
                "EII", "Oviedo", 2f, false, new String[]{"A"});

        //grupo solapado por detras
        Grupo grupo5 = new Grupo(1, "C", "CCC", "grupo5", 1, h4,
                "EII", "Oviedo", 2f, false, new String[]{"A"});

        //grupo por detras sin solapar
        Grupo grupo6 = new Grupo(1, "C", "CCC", "grupo6", 1, h5,
                "EII", "Oviedo", 2f, false, new String[]{"A"});

        //diferente escuela pero misma ciudad con horario de margen 30 min
        Grupo grupo7 = new Grupo(1, "C", "CCC", "grupo7", 1, h5,
                "Quimicas", "Oviedo", 2f, false, new String[]{"A"});

        //diferente escuela pero misma ciudad con horario de margen menor a 30 min
        Grupo grupo8 = new Grupo(1, "C", "CCC", "grupo8", 1, h5_2,
                "Quimicas", "Oviedo", 2f, false, new String[]{"A"});

        //diferente escuela y ciudad con horario de margen igual a 30 min (pero deberia ser 60)
        Grupo grupo9 = new Grupo(1, "C", "CCC", "grupo8", 1, h3,
                "EPI", "Gijón", 2f, false, new String[]{"A"});

        //diferente escuela y ciudad con horario de margen igual a 60 min
        Grupo grupo10 = new Grupo(1, "C", "CCC", "grupo8", 1, h6,
                "EPI", "Gijón", 2f, false, new String[]{"A"});


        assertFalse ( checkSolapamiento(profe1, grupo2) ); // no valido
        assertFalse ( checkSolapamiento(profe1, grupo3) ); // no valido
        assertTrue  ( checkSolapamiento(profe1, grupo4) ); // valido
        assertFalse ( checkSolapamiento(profe1, grupo5) ); // no valido
        assertTrue  ( checkSolapamiento(profe1, grupo6) ); // valido

        assertTrue  ( checkSolapamiento(profe1, grupo7) ); // valido
        assertFalse ( checkSolapamiento(profe1, grupo8) ); // no valido

        assertFalse  ( checkSolapamiento(profe1, grupo9) ); // valido
        assertTrue ( checkSolapamiento(profe1, grupo10) ); // no valido


    }

    /**
     * para facilitar el test se ha copiado la version actual (13/06/2018) del método
     * {@link Decodificacion#checkSolapamiento(Profesor, Grupo)}
     */
    boolean checkSolapamiento(Profesor p, Grupo a) {
        for (Grupo asignatura : p.getAsignadas()) {
            for (Horario horariosGrupoActual : asignatura.getHorarios()) { // actual(es)
                for (Horario horariosGrupoNuevo : a.getHorarios()) { // nuevo(s)
                    if (horariosGrupoActual.getDia() == horariosGrupoNuevo.getDia()
                            && asignatura.getSemestre() == a.getSemestre()) {
                        int finActualInicioNueva =
                                horariosGrupoActual.getHoraFin().compareTo(horariosGrupoNuevo.getHoraInicio());
                        int inicioActualFinNueva =
                                horariosGrupoActual.getHoraInicio().compareTo(horariosGrupoNuevo.getHoraFin());

                        int lapso;
                        if (asignatura.getCiudad().equals(a.getCiudad())) // misma ciudad pero distinta escuela
                            lapso = 30;
                        else lapso = 60;

                        // si el fin de la actual es anterior al comienzo de la nueva a asignar
                        if (finActualInicioNueva <= 0) {
                            if (!asignatura.getEscuela().equals(a.getEscuela())) {
                                if (Math.abs(
                                        horariosGrupoNuevo.getHoraInicio().getTime() -
                                                horariosGrupoActual.getHoraFin().getTime()
                                ) < TimeUnit.MINUTES.toMillis(lapso))
                                    return false;
                            }

                            // si comienzo de la actual es posterior al final de la nueva a asignar
                        } else if (inicioActualFinNueva >= 0) {
                            if (!asignatura.getEscuela().equals(a.getEscuela()))
                                if (Math.abs(
                                        horariosGrupoNuevo.getHoraFin().getTime() -
                                                horariosGrupoActual.getHoraInicio().getTime()
                                ) < TimeUnit.MINUTES.toMillis(lapso))
                                    return false;
                        } else // en los demas casos hay solapamiento y no es válido
                            return false;
                    }
                }
            }
        }
        return true;
    }


}

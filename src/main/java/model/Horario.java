package main.java.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Sergio Florez on 27/02/2018.
 */
public class Horario {

    private static final char SIN_DIA_CHAR = '-';
    private char dia;
    private Timestamp horaInicio;
    private Timestamp horaFin;

    public Horario(char dia, Timestamp horaInicio, Timestamp horaFin) {
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    /**
     * Este constructor esta pensado para utilizarse en las disponibilidades
     * de los profesores.
     * Asigna al dia de la semana un caracter que no se corresponde a ningun
     * dia de la semana
     *
     * @param horaInicio
     * @param horaFin
     */
    public Horario(Timestamp horaInicio, Timestamp horaFin) {
        this.dia = SIN_DIA_CHAR;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public char getDia() {
        return dia;
    }

    public void setDia(char dia) {
        this.dia = dia;
    }

    public Timestamp getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Timestamp horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Timestamp getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Timestamp horaFin) {
        this.horaFin = horaFin;
    }

    @Override
    public String toString() {
        return "Horario{" +
                "dia=" + dia +
                ", horaInicio=" + horaInicio.toString().substring(11, 16) +
                ", horaFin=" + horaFin.toString().substring(11, 16) +
                '}';
    }

    public String toFormatedString(){
        StringBuilder sb = new StringBuilder();
        sb.append((dia!= SIN_DIA_CHAR ?dia+" ":"")+getHoraInicio().toString().substring(11, 16));
        sb.append("-"+getHoraFin().toString().substring(11, 16));

        return sb.toString();
    }

    public static Horario disponibilidadTotal;

    // importante: H mayuscula para indicarla en formato 24 horas
    // en caso contrario las 12:00 las contaria por las 12 de la noche (pm)
    public static SimpleDateFormat horaFormat = new SimpleDateFormat("HH:mm");
    static {
        try {
            disponibilidadTotal = crearDisponibilidadTotal();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private static Horario crearDisponibilidadTotal() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            return new Horario(new Timestamp(dateFormat.parse("00:00").getTime()),
                    new Timestamp(dateFormat.parse("23:59").getTime()));

    }
}

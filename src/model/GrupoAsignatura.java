package model;

import java.util.Arrays;

public class GrupoAsignatura {

    public static final int INICIO = 0;
    private static int contador = 0;

    private final Integer id;
    private String grupo;
    private String nombre;
    private Integer semestre;
    private Horario horario;
    private String escuela;
    private String ciudad;
    private Float horas;
    private Boolean bilingue;
    private String[] areas;

    public GrupoAsignatura(String nombre, String grupo, int semestre, Horario horario,
                           String escuela, String ciudad, Float horas, Boolean bilingue, String[] areas) {
        this.id = contador++;
        this.grupo = grupo;
        this.nombre = nombre;
        this.semestre = semestre;
        this.horario = horario;
        this.escuela = escuela;
        this.ciudad = ciudad;
        this.horas = horas;
        this.bilingue = bilingue;
        this.areas = areas;
    }

    public static Integer getUltimoId() {
        return contador;
    }


    @Override
    public String toString() {
        return "GrupoAsignatura{\n" +
                "\tid=" + id +'\n'+
                "\tgrupo='" + grupo + '\'' +'\n'+
                "\tnombre='" + nombre + '\'' +'\n'+
                "\tsemestre=" + semestre +'\n'+
                "\thorario=" + horario +'\n'+
                "\tescuela='" + escuela + '\'' +'\n'+
                "\tciudad='" + ciudad + '\'' +'\n'+
                "\thoras=" + horas +'\n'+
                "\tbilingue=" + bilingue +'\n'+
                "\tareas=" + Arrays.toString(areas) +'\n'+
                '}';
    }
}

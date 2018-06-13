package main.java.model;

import java.util.Arrays;
import java.util.List;

public class GrupoAsignatura {

    public static final int INICIO = 0;
    private static int contador = 0;

    private final Integer id;
    private String codigo;
    private String grupo;
    private String nombre;
    private Integer semestre;
    private List<Horario> horarios;
    private String escuela;
    private String ciudad;
    private Float horas;
    private Boolean bilingue;
    private String[] areas;

    public GrupoAsignatura(Integer id, String codigo, String nombre, String grupo, int semestre,
                           List<Horario> horarios, String escuela, String ciudad, Float horas,
                           Boolean bilingue, String[] areas) {
        this.id = id;
        inicializar(codigo, nombre, grupo, semestre, horarios,
                escuela, ciudad, horas, bilingue, areas);
    }


    public GrupoAsignatura(String codigo, String nombre, String grupo, int semestre, List<Horario> horarios,
                           String escuela, String ciudad, Float horas, Boolean bilingue, String[] areas) {
        this.id = contador++;
        inicializar(codigo, nombre, grupo, semestre, horarios,
                escuela, ciudad, horas, bilingue, areas);
    }

    public static Integer getUltimoId() {
        return contador;
    }

    private void inicializar(String codigo, String nombre, String grupo, int semestre, List<Horario> horario,
                             String escuela, String ciudad, Float horas, Boolean bilingue, String[] areas) {
        this.codigo = codigo;
        this.grupo = grupo;
        this.nombre = nombre;
        this.semestre = semestre;
        this.horarios = horario;
        this.escuela = escuela;
        this.ciudad = ciudad;
        this.horas = horas;
        this.bilingue = bilingue;
        this.areas = areas;
    }

    public GrupoAsignatura clone() {
        return new GrupoAsignatura(id, codigo, nombre, grupo, semestre,
                horarios, escuela, ciudad, horas, bilingue, areas);
    }

    public Integer getId() {
        return id;
    }

    public String getCodigoAsignatura() {
        return codigo;
    }

    public String getGrupo() {
        return grupo;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public List<Horario> getHorarios() {
        return horarios;
    }

    public String getEscuela() {
        return escuela;
    }

    public String getCiudad() {
        return ciudad;
    }

    public Float getHoras() {
        return horas;
    }

    public Boolean getBilingue() {
        return bilingue;
    }

    public String[] getAreas() {
        return areas;
    }

    @Override
    public String toString() {
        return "GrupoAsignatura{ " +
                "id=" + id + ',' +
                "codigoAsignatura=" + codigo + ',' +
                " grupo='" + grupo + '\'' + ',' +
                " nombre='" + nombre + '\'' + ',' +
                " semestre=" + semestre + ',' +
                " horarios=" + horarios + '\n' +
                "\tescuela='" + escuela + '\'' + ',' +
                " ciudad='" + ciudad + '\'' + ',' +
                " horas=" + horas + ',' +
                " bilingue=" + bilingue + ',' +
                " areas=" + Arrays.toString(areas) + '\n' +
                '}';
    }


}

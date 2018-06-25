package main.java.model;

import main.java.io.Settings;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Grupo {

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

    public Grupo(Integer id, String codigo, String nombre, String grupo, int semestre,
                 List<Horario> horarios, String escuela, String ciudad, Float horas,
                 Boolean bilingue, String[] areas) {
        this.id = id;
        inicializar(codigo, nombre, grupo, semestre, horarios,
                escuela, ciudad, horas, bilingue, areas);
    }


    public Grupo(String codigo, String nombre, String grupo, int semestre, List<Horario> horarios,
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

        if(horario.isEmpty())
            System.err.println("el grupo" + this + "no tiene horarios asociados");
    }

    public Grupo clone() {
        return new Grupo(id, codigo, nombre, grupo, semestre,
                horarios, escuela, ciudad, horas, bilingue, areas);
    }

    /**
     * En funcion de la naturaleza del la asignatura, o bien del profesor
     * que la imparte, el peso que tienen las horas de trabajo pueden no
     * ser el mismo, por ello este metodo hace el cálculo pertinente y retorna
     * la cantidad de horas a contabilizar en funcion del grupo actual y de:
     *
     * @param profesor el profesor que imparta la asignatura
     * @return horas a contabilizar por el algoritmo
     */
    public float getHorasComputables(Profesor profesor) {
        float coeficiente = 1f; // inicialmente es el mismo
            if (this.getBilingue())
                coeficiente = 1.5f; // si ingles, contabilizan como mas horas
        return coeficiente * this.getHoras();
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

    /**
     * Ojo, este método retorna las horas indicadas en el input,
     * las horas realmente contabilidadas se calculan mediante el
     * método {@link #getHorasComputables(Profesor) getHorasComputables}
     *
     * @return
     * @see #getHorasComputables(Profesor)
     */
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grupo grupo = (Grupo) o;
        return Objects.equals(id, grupo.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Grupo{ " +
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

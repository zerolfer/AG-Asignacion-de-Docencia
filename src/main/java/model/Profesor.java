package main.java.model;

import java.util.*;
import java.util.stream.Collectors;

public class Profesor {

    private static Integer contador = 0;

    private final int id;
    private String nombre;
    private Float capacidadInicial;
    private Float capacidad;
    private Boolean bilingue;
    private String area; // solo puede tener un área

    // ASIGNACIONES
    private List<GrupoAsignatura> asignadas;

    public Profesor(Integer id, String nombre, Float capacidad, Boolean bilingue, String area) {
        this.id = id;
        inicializar(nombre, capacidad, bilingue, area);
    }

    public Profesor(String nombre, Float capacidad, Boolean bilingue, String area) {
        this.id = contador++;
        inicializar(nombre, capacidad, bilingue, area);
    }

    public static Integer getUltimoId() {
        return contador;
    }

    private void inicializar(String nombre, Float capacidad, Boolean bilingue, String area) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.capacidadInicial = capacidad;
        this.bilingue = bilingue;
        this.area = area;

        asignadas = new ArrayList<>();
    }

    public float getHorasClaseAsignadas() {
        return getCapacidadInicial() - this.getCapacidad();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Float capacidad) {
        this.capacidad = capacidad;
    }

    public Float getCapacidadInicial() {
        return capacidadInicial;
    }

    public Boolean getBilingue() {
        return bilingue;
    }

    public void setBilingue(Boolean bilingue) {
        this.bilingue = bilingue;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<GrupoAsignatura> getAsignadas() {
        return asignadas;
    }


    @Override
    public String toString() {
        String s = "Profesor{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", capacidad=" + capacidad +
                ", bilingue=" + bilingue +
                ", area='" + area + '\'';
        if (!asignadas.isEmpty())
            s += ", asignadas{ " + asignadas.stream().map(GrupoAsignatura::getId).collect(Collectors.toList());
        ;
        //Arrays.toString(asignadas.toArray(new GrupoAsignatura[asignadas.size()])) + " }";
        s += '}';
        return s;
    }

    public Integer getId() {
        return id;
    }

    public int getNumAsignaturas() {
        Set<String> asignaturas = new HashSet<>();
        int contador = 0;
        for (GrupoAsignatura grupo : asignadas) {
            String codigoAsignatura = grupo.getCodigoAsignatura();
            if (!asignaturas.contains(codigoAsignatura)) {
                asignaturas.add(codigoAsignatura);
                contador++;
            }
        }
        return contador;
    }

    public boolean imparte(String codigoAsignatura) {
        for (GrupoAsignatura grupo : asignadas)
            if (grupo.getCodigoAsignatura().equals(codigoAsignatura))
                return true;
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profesor profesor = (Profesor) o;
        return id == profesor.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}

package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Profesor {

    private static Integer contador = 0;

    private final Integer id;
    private String nombre;
    private Float capacidad;
    private Boolean bilingue;
    private String area; // solo puede tener un área

    // ASIGNACIONES
    private List<GrupoAsignatura> asignadas;


    public Profesor(String nombre, Float capacidad, Boolean bilingue, String area) {
        this.id = contador++;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.bilingue = bilingue;
        this.area = area;

        asignadas = new ArrayList<>();
    }

    public static Integer getUltimoId() {
        return contador;
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
            s += ", asignadas{ " + asignadas.stream().map(GrupoAsignatura::getId).collect(Collectors.toList());;
            //Arrays.toString(asignadas.toArray(new GrupoAsignatura[asignadas.size()])) + " }";
        s += '}';
        return s;
    }
}

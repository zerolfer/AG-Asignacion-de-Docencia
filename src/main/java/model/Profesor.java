package main.java.model;

import java.util.*;
import java.util.stream.Collectors;

public class Profesor {

    private static Integer contador = 0;

    private final int id;
    private String nombre;
    private float capacidadInicial;
    private float capacidad;
    private boolean bilingue;
    private String area; // solo puede tener un área
    private Horario disponibilidad;


    // ASIGNACIONES
    private Set<Grupo> asignadas;

    public Profesor(Integer id, String nombre, Float capacidad, Boolean bilingue, String area, Horario disponibilidad) {
        this.id = id;
        inicializar(nombre, capacidad, bilingue, area, disponibilidad);
    }

    public Profesor(String nombre, Float capacidad, Boolean bilingue, String area, Horario disponibilidad) {
        this.id = contador++;
        inicializar(nombre, capacidad, bilingue, area, disponibilidad);
    }

    public static Integer getUltimoId() {
        return contador;
    }

    private void inicializar(String nombre, Float capacidad, Boolean bilingue, String area, Horario disponibilidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.capacidadInicial = capacidad;
        this.bilingue = bilingue;
        this.area = area;
        this.disponibilidad = disponibilidad;

        asignadas = new HashSet<>();
    }

    public boolean asignarGrupo(Grupo asignatura) {
        if (this.getCapacidad() - asignatura.getHorasComputables(this) < 0)
            return false;
        if (this.getAsignadas().add(asignatura)) {
            this.setCapacidad(this.getCapacidad() - asignatura.getHorasComputables(this));
            return true;
        }
        return false;
    }

    public boolean eliminarGrupo(Grupo asignatura) {
        boolean result = this.getAsignadas().remove(asignatura);
        if (result) {
            this.setCapacidad(this.getCapacidad() + asignatura.getHorasComputables(this));
        }
        return result;
    }

    public Profesor clone() {
        Profesor result = new Profesor(id, nombre, capacidadInicial, bilingue, area, disponibilidad);
        for (Grupo g : getAsignadas())
            result.asignarGrupo(g.clone());
        return result;
    }

    public float getHorasClaseAsignadas() {
        return getCapacidadInicial() - this.getCapacidad();
    }

    public float getHorasClaseAsignadas2(){
        float suma=0;
        for (Grupo g :asignadas) {
            suma+=g.getHorasComputables(this);
        }
        return suma;
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

    public Set<Grupo> getAsignadas() {
        return asignadas;
    }

    public Horario getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Horario disponibilidad) {
        this.disponibilidad = disponibilidad;
    }


    @Override
    public String toString() {
        String s = "Profesor{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", capacidad=" + capacidad +
                ", bilingue=" + bilingue +
                ", area='" + area + '\'';
//        if (!asignadas.isEmpty())
//            s += ", asignadas{ " + asignadas.stream().map(Grupo::getId).collect(Collectors.toList());
//        ;
        //Arrays.toString(asignadas.toArray(new Grupo[asignadas.size()])) + " }";
        s += '}';
        return s;
    }

    public Integer getId() {
        return id;
    }


    private int numAsignaturas = -1;

    public int getNumAsignaturas() {
        return getNumAsignaturas(false);
    }

    public int getNumAsignaturas(boolean recalcular) {
        if (numAsignaturas != -1 && !recalcular)
            return numAsignaturas;
        Set<String> asignaturas = new HashSet<>();
        int contador = 0;
        for (Grupo grupo : asignadas) {
            String codigoAsignatura = grupo.getCodigoAsignatura();
            if (!asignaturas.contains(codigoAsignatura)) {
                asignaturas.add(codigoAsignatura);
                contador++;
            }
        }
        numAsignaturas = contador;
        return contador;

    }


    public boolean imparte(String codigoAsignatura) {
        for (Grupo grupo : asignadas)
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

package model;

public class Profesor {

    private static Integer contador = 0;

    private final Integer id;
    private String  nombre;
    private Integer capacidad;
    private Boolean bilingue;
    private String  area; // solo puede tener un área

    public Profesor(String nombre, Integer capacidad, Boolean bilingue, String area) {
        this.id=contador++;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.bilingue = bilingue;
        this.area = area;
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

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
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

    @Override
    public String toString() {
        return "Profesor{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", capacidad=" + capacidad +
                ", bilingue=" + bilingue +
                ", area='" + area + '\'' +
                '}';
    }
}

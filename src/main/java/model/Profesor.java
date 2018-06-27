package main.java.model;

import main.java.io.Settings;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    ////////////////////////////////
    // RESTRICCIONES DEL PROBLEMA //
    ////////////////////////////////

    /**
     * Mínimo lapso de tiempo en minutos que se deberá dejar
     * transcurrir como margen en caso de que la docencia a
     * sea en diferentes escuelas pero sea en la misma ciudad.
     *
     * @see #checkSolapamiento(Grupo)
     */
    private static final int minutosIntervaloMismaCiudad =
            Settings.getInteger("dominio.minutosIntervaloMismaCiudad");

    /**
     * Mínimo lapso de tiempo en minutos que se deberá dejar
     * transcurrir como margen en caso de que la docencia a
     * sea en una escuela y una ciudad diferente.
     *
     * @see #checkSolapamiento(Grupo)
     */
    private static final int minutosIntervaloDistintaCiudad =
            Settings.getInteger("dominio.minutosIntervaloDistintaCiudad");


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

    /**
     * Añade un grupo al plan docente del profesor que llama al método.
     * IMPORTANTE: <b>Este método NO modifica el fenotipo, se deberá hacer
     * a continuacio<b>
     *
     * @param grupo
     * @return
     */
    public boolean asignarGrupo(Grupo grupo/*, boolean verificar*/) {
        /*if (!checkCapacidad(grupo))
            return false;
        if (verificar) {*/
            /*boolean esPosible = */if(!checkArea(grupo) || !checkBilingue(grupo) || !checkCapacidad(grupo) ||
                    !checkDisponibilidad(grupo) || !checkSolapamiento(grupo))/*;*/
            /*if (!esPosible)*/ return false;
       /* }*/
        if (this.getAsignadas().add(grupo)) {
            this.setCapacidad(this.getCapacidad() - grupo.getHorasComputables(this));
            numAsignaturas=-1;
            return true;
        } else throw new RuntimeException("No se puede asignar al profesor " + getId() + " el grupo " + grupo);
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
        result.numAsignaturas=numAsignaturas;
//        result.setCapacidad(capacidad);

        for (Grupo g : getAsignadas())
            // no hace falta clonar las asignaturas en si porque son de solo lectura
            if (!result.asignarGrupo(g/*.clone()*/)) throw new RuntimeException("Situacion ilegal");
        assert capacidad==result.getCapacidad();
        return result;
    }

    public float getHorasClaseAsignadas() {
        return getCapacidadInicial() - this.getCapacidad();
    }

    public float getHorasClaseAsignadas2() {
        float suma = 0;
        for (Grupo g : asignadas) {
            suma += g.getHorasComputables(this);
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

    public void setAsignadas(Set<Grupo> asignadas) {
        this.asignadas = asignadas;
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

    public int getId() {
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

    public boolean checkImparteAsignatura(Grupo a) {
        return checkImparteAsignatura(a.getCodigoAsignatura());
    }

    public boolean checkImparteAsignatura(String codigoAsignatura) {
        for (Grupo grupo : asignadas)
            if (grupo.getCodigoAsignatura().equals(codigoAsignatura))
                return true;
        return false;
    }


    public boolean checkDisponibilidad(Grupo a) {
        for (Horario horario : a.getHorarios()) {
            // la unica opcion es que el horario sea o igual o subconjunto de la disponibilidad
            // es decir, la hora de inicio sea posterior o igual a la hora de inicio de disponibilidad
            // y que la hora de fin de la clase sea anterior o igual a la de fin de disponibilidad
            if (getDisponibilidad().getHoraInicio().compareTo(horario.getHoraInicio()) <= 0
                    && getDisponibilidad().getHoraFin().compareTo(horario.getHoraFin()) >= 0)
                continue;
            else // las demas opciones no son validas, y no es necesario seguir iterando
                return false;
        }
        return true; // si se ha llegado hasta aqui es porque todos cumplian la restriccion
    }

    public boolean checkArea(Grupo a) {
        for (String area : a.getAreas())
            if (area.equalsIgnoreCase(getArea()))
                return true;
        return false;
    }

    public boolean checkSolapamiento(Grupo a) {
        for (Grupo asignatura : getAsignadas()) {
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
                            lapso = minutosIntervaloMismaCiudad;
                        else lapso = minutosIntervaloDistintaCiudad;

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

    public boolean checkBilingue(Grupo a) {
        return getBilingue() || !a.getBilingue();
    }

    public boolean checkCapacidad(Grupo a) {
        return getCapacidad() >= a.getHorasComputables(this);
    }

    //////////////////////////////////////
    // Metodos para comparar Profesores //
    //////////////////////////////////////

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

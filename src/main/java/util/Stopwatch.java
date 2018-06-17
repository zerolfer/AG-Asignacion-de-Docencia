package main.java.util;

import main.java.genetico.AlgoritmoGenetico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stopwatch {

    private List<Long> timeSlot;
    private Long initTime;

    public Stopwatch() {
        this.timeSlot = new ArrayList<>();
    }

    /**
     * Retorna el tiempo en milisegundos (ms) que ha tardado
     * el algoritmo en alcanzar la generación indicada.
     *  <br/><b>Importante: el numero de generaciones comienza
     *  por el 1, no por el 0</b>
     *
     * @param generacion numero de la generacion. <b>
     *                   comenzando por el 1, no por el 0</b>
     * @return
     */
    public Long getTimeAtGeneration(Integer generacion) {
        if (timeSlot.isEmpty())
            throw new RuntimeException("Error: se está tratando de" +
                    "acceder a los datos de una generación inexistente");
        if (generacion == 0)
            return timeSlot.get(0);
        else return timeSlot.get(generacion - 1);
    }

    public Long getFinalTime() {
        return timeSlot.get(timeSlot.size()-1);
    }

    public void start() {
        this.initTime = System.currentTimeMillis();
    }

    private Long now() {
        return System.currentTimeMillis();
    }

    public void newLap(Integer generacion) {
        timeSlot.add(now() - initTime);
    }

}

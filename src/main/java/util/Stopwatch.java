package main.java.util;

import main.java.genetico.AlgoritmoGenetico;

import java.util.HashMap;
import java.util.Map;

public class Stopwatch {

    private Map<Integer, Long> timeSlot;
    private Long initTime;

    public Stopwatch() {
        this.timeSlot = new HashMap<>();
    }

    public Map<Integer, Long> getTimeSlot() {
        return timeSlot;
    }

    public Long getTimeAtGeneration(Integer generacion) {
        return timeSlot.get(generacion);
    }

    public Long getFinalTime() {
        return timeSlot.get(AlgoritmoGenetico.numeroGeneraciones);
    }

    public void start() {
        this.initTime = System.currentTimeMillis();
    }

    private Long now() {
        return System.currentTimeMillis();
    }

    public void newLap(Integer generacion) {
        timeSlot.put(generacion, now() - initTime);
    }

}

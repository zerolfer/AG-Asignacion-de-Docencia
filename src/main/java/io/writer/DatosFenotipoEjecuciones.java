package main.java.io.writer;

import main.java.genetico.AlgoritmoGenetico;
import main.java.model.Grupo;
import main.java.model.Horario;
import main.java.model.Profesor;
import main.java.util.RandomManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DatosFenotipoEjecuciones extends AbstractCSVWriter {
    private String ejecucion;

    public DatosFenotipoEjecuciones(String path, String ejecucion) {
        super(path, true);
        this.ejecucion = ejecucion;
    }

    @Override
    public void csvWriteData(AlgoritmoGenetico genetico) {
        //Map<Integer, Set<Integer>> fenotipo = genetico.getMejorIndividuo().getFenotipo();
        Map<Profesor, Set<Grupo>> fenotipo = genetico.getMejorIndividuo().fenotipo2;
        System.out.println(genetico.getMejorIndividuo().toStringFull());
        Date d = new Date();
        super.csvWriteData("ejecucion" + SPLITTER + ejecucion + SPLITTER
                + "hora" + SPLITTER +
                new SimpleDateFormat("HH:mm:ss").format(d) + SPLITTER
                + new SimpleDateFormat("dd/MM/yyyy").format(d)
                + SPLITTER + "semilla" + SPLITTER + RandomManager.seed + LINE_SEP
                + fenotipoToString(fenotipo) + LINE_SEP +
                "________________________________________________" +
                "________________________________________________" +
                "________________________________________________" +
                "________________________________________________" +
                "______________________________________________"
                + LINE_SEP);


    }

    private String fenotipoToString(Map<Profesor, Set<Grupo>> fenotipo) {
        StringBuilder sb = new StringBuilder();
        for (Profesor key : fenotipo.keySet()) { // profesores
            sb.append("profesor");
            sb.append(SPLITTER + "nombre");
            sb.append(SPLITTER + "bilingue");
            sb.append(SPLITTER + "area");
            sb.append(SPLITTER + "disponibilidad");
            sb.append(SPLITTER + "capacidad");
            sb.append(SPLITTER + "h. libres");
            sb.append(SPLITTER + "% horas");
            sb.append(SPLITTER + "n. asignaturas");
            sb.append(LINE_SEP);

            sb.append(key.getId());
            sb.append(SPLITTER);

            sb.append(key.getNombre());
            sb.append(SPLITTER);

            sb.append(key.getBilingue() ? "SI" : "NO");
            sb.append(SPLITTER);

            sb.append(key.getArea());
            sb.append(SPLITTER);

            sb.append(key.getDisponibilidad().toFormatedString());
            sb.append(SPLITTER);

            sb.append(key.getCapacidadInicial());
            sb.append(SPLITTER);

            sb.append(key.getCapacidad());
            sb.append(SPLITTER);

            sb.append((key.getCapacidadInicial() - key.getCapacidad()) / key.getCapacidadInicial() * 100);
            sb.append(SPLITTER);

            sb.append(key.getNumAsignaturas());
            sb.append(LINE_SEP);


            sb.append(/*SPLITTER + */"asignadas");
            sb.append(SPLITTER + "codigo");
            sb.append(SPLITTER + "grupo");
            sb.append(SPLITTER + "nombre");
            sb.append(SPLITTER + "semestre");
            sb.append(SPLITTER + "escuela");
            sb.append(SPLITTER + "ciudad");
            sb.append(SPLITTER + "horas");
            sb.append(SPLITTER + "h. computadas");
            sb.append(SPLITTER + "bilingue");
            sb.append(SPLITTER + "areas");
            sb.append(SPLITTER + "horarios");
            sb.append(LINE_SEP);

            for (Grupo grupo : fenotipo.get(key)) {
                //sb.append(SPLITTER);
                sb.append(grupo.getId());
                sb.append(SPLITTER);
                sb.append(grupo.getCodigoAsignatura());
                sb.append(SPLITTER);
                sb.append(grupo.getGrupo());
                sb.append(SPLITTER);
                sb.append(grupo.getNombre());
                sb.append(SPLITTER);
                sb.append(grupo.getSemestre());
                sb.append(SPLITTER);
                sb.append(grupo.getEscuela());
                sb.append(SPLITTER);
                sb.append(grupo.getCiudad());
                sb.append(SPLITTER);
                sb.append(grupo.getHoras());
                sb.append(SPLITTER);
                sb.append(grupo.getHorasComputables(key));
                sb.append(SPLITTER);
                sb.append((grupo.getBilingue() ? "SI" : "NO"));
                sb.append(SPLITTER);
                // áreas
                for (int i = 0; i < grupo.getAreas().length; i++) {
                    sb.append(grupo.getAreas()[i]);
                    if (i < grupo.getAreas().length - 1)
                        sb.append("/");
                }
                sb.append(SPLITTER);
                //horarios
                for (Horario horario : grupo.getHorarios()) {
                    sb.append(horario.toFormatedString());
                    sb.append(SPLITTER);
                }


                sb.append(LINE_SEP);

            }
            sb.append(LINE_SEP);
        }
        return sb.toString();
    }


    @Override
    public void csvWriteData(AlgoritmoGenetico data, List<String> otros) {
        csvWriteData(data);
    }
}

package main.java.util.writer;

import main.java.genetico.AlgoritmoGenetico;
import main.java.model.BD;
import main.java.model.GrupoAsignatura;
import main.java.model.Profesor;

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
        /*BufferedReader br = null;

        try {
            File file = new File(path);
            File file2 = new File("." + path);//so the
            //names don't conflict or just use different folders

            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;
            int i = 0;
            for (line = br.readLine(); line != null; line = br.readLine(), i++) {

                // Suponiendo que las asignaturas estan orrdenadas
                String addedColumn = BD.getProfesorById(
                        genetico.getMejorIndividuo().getFenotipo().get(i)).toString()
                );
                super.getBr().write(line + addedColumn + lineSep);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
//        super.csvWriteData(ejecucion+lineSep+genetico.getMejorIndividuo().getFenotipo());
        Map<Integer, Set<Integer>> fenotipo = genetico.getMejorIndividuo().getFenotipo();

        super.csvWriteData("ejecucion" + SPLITTER + ejecucion
                + LINE_SEP + fenotipoToString(fenotipo) + LINE_SEP);


    }

    private String fenotipoToString(Map<Integer, Set<Integer>> fenotipo) {
        StringBuilder sb = new StringBuilder();
        for (Integer key : fenotipo.keySet()) { // profesores
            sb.append("profesor");
            sb.append(SPLITTER + "nombre");
            sb.append(SPLITTER + "capacidad");
            sb.append(SPLITTER + "bilingue");
            sb.append(SPLITTER + "area" + LINE_SEP);

            Profesor profe = BD.getProfesorById(key);
            sb.append(SPLITTER + profe.getId());
            sb.append(SPLITTER + profe.getNombre());
            sb.append(SPLITTER);
            sb.append(profe.getCapacidad());
            sb.append(SPLITTER);
            sb.append(profe.getBilingue() ? "SI" : "NO");
            sb.append(SPLITTER + profe.getArea() + LINE_SEP);

            sb.append(/*SPLITTER + */"asignadas");
            sb.append(SPLITTER + "codigo");
            sb.append(SPLITTER + "grupo");
            sb.append(SPLITTER + "nombre");
            sb.append(SPLITTER + "semestre");
            sb.append(SPLITTER + "horario");
            sb.append(SPLITTER + "escuela");
            sb.append(SPLITTER + "ciudad");
            sb.append(SPLITTER + "horas");
            sb.append(SPLITTER + "bilingue");
            sb.append(SPLITTER + "areas");
            sb.append(LINE_SEP);

            for (Integer grupoId : fenotipo.get(key)) {
                GrupoAsignatura grupo = BD.getGrupoById(grupoId);
                //sb.append(SPLITTER);
                sb.append(grupo.getId());
                sb.append(SPLITTER);
                sb.append(grupo.getCodigo());
                sb.append(SPLITTER);
                sb.append(grupo.getGrupo());
                sb.append(SPLITTER);
                sb.append(grupo.getNombre());
                sb.append(SPLITTER);
                sb.append(grupo.getSemestre());
                sb.append(SPLITTER);
                sb.append(grupo.getHorario().toFormatedString());
                sb.append(SPLITTER);
                sb.append(grupo.getEscuela());
                sb.append(SPLITTER);
                sb.append(grupo.getCiudad());
                sb.append(SPLITTER);
                sb.append(grupo.getHoras());
                sb.append(SPLITTER);
                sb.append((grupo.getBilingue() ? "SI" : "NO"));
                sb.append(SPLITTER);

                for (int i = 0; i < grupo.getAreas().length; i++) {
                    sb.append(grupo.getAreas()[i]);
                    if (i < grupo.getAreas().length - 1)
                        sb.append("/");
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

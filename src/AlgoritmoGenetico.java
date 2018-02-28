import model.GrupoAsignatura;
import model.Profesor;

import java.util.ArrayList;
import java.util.List;

public class AlgoritmoGenetico {

    // VARIABLES DE ENTRADA DEL GENÉTICO
    private static final int POPULATION_SIZE = 100;

    // INFORMACIÓN DEL PROBLEMA
    private static List<GrupoAsignatura> asignaturas=new ArrayList<>();
    private static List<Profesor> profesores=new ArrayList<>();

    public static void main(String[] args) {
        iniciacilizacion();
        codificacion();
    }

    private static void iniciacilizacion() {
        asignaturas = CSVReader.CsvLoadAsignaturas();
        profesores = CSVReader.CsvLoadProfesores();
    }

    private static void codificacion() {
        Cromosoma[] cromosomas = Cromosoma.generateRandomPopulation(POPULATION_SIZE);

        for (Cromosoma c : cromosomas) {
            AlgoritmoGenetico.decodificar(c);
        }
    }

    private static void decodificar(Cromosoma cromosoma) {
        System.out.println(cromosoma);
    }


}

import model.Profesor;

import java.util.List;

public class GenethicAlgorithm {

    public static void main(String[] args) {
        codificacion();
    }

    private static void codificacion() {
        Cromosoma cromosoma = generateRandomIndividual();

        GenethicAlgorithm.decodificar(cromosoma);
    }

    private static Cromosoma generateRandomIndividual() {
        List<Profesor> profesores = CSVReader.CsvLoadProfesores();

    }

    private static void decodificar(Cromosoma cromosoma) {
    }


}

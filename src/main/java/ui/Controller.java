package main.java.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import main.java.genetico.AlgoritmoGenetico;

import java.io.File;

public class Controller {

    //@FXML private FlowPane fp;
    @FXML
    private GridPane gp;
    @FXML
    private TextField tfAsignaturasFile;
    @FXML
    private TextField tfProfesoresFile;
    @FXML
    private Spinner<Integer> spNumGen;
    @FXML
    private Spinner<Double> spPropCruce;
    @FXML
    private Spinner<Double> spProbMuta;
    @FXML
    private Spinner<Integer> spPopuSize;
    @FXML
    private Spinner<Double> spProbBusq;
    @FXML
    private Spinner<Integer> spNumEjecuciones;

    @FXML
    public void SeleccionFicheroAsignaturas(ActionEvent actionEvent) {
        tfAsignaturasFile.setText(lanzarBrowser().getName());
    }

    @FXML
    public void SeleccionFicheroProfesores(ActionEvent actionEvent) {
        tfProfesoresFile.setText(lanzarBrowser().getName());
    }

    private File lanzarBrowser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File("files"));
        return fileChooser.showOpenDialog(gp.getScene().getWindow());
    }

    @FXML
    private void lanzarGenetico(ActionEvent actionEvent) {
        AlgoritmoGenetico ag = new AlgoritmoGenetico();
        Integer valor1 = spPopuSize.getValue();
        Double valor2 = spPropCruce.getValue();
        Double valor3 = spProbMuta.getValue();
        Integer valor4 = spNumGen.getValue();
        Double valor5 = spProbBusq.getValue();

        ag.setParameters(valor1, valor2.floatValue(), valor3.floatValue()
                , valor4, valor5.floatValue());
        for (int i = 0; i < spNumEjecuciones.getValue(); i++) {
            ag.iniciar("Vista"+i, i);
        }
    }

}

package main.java.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller {

    //@FXML private FlowPane fp;
    @FXML private FlowPane fp;
    @FXML private TextField tfAsignaturasFile;
    @FXML private TextField tfProfesoresFile;

    public void SeleccionFicheroAsignaturas(ActionEvent actionEvent) {
        tfAsignaturasFile.setText(lanzarBrowser().getName());
    }

    public void SeleccionFicheroProfesores(ActionEvent actionEvent) {
        tfProfesoresFile.setText(lanzarBrowser().getName());
    }

    private File lanzarBrowser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File("files"));
        return fileChooser.showOpenDialog(fp.getScene().getWindow());
    }

}

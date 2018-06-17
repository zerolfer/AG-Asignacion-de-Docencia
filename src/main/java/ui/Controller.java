package main.java.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import main.java.Main;
import main.java.busqueda.BusquedaIntercambioGrupo;
import main.java.busqueda.BusquedaLocal;
import main.java.genetico.AlgoritmoGenetico;
import main.java.genetico.algoritmos.creacion.AlgoritmoCreacion;
import main.java.genetico.algoritmos.creacion.CreacionAleatoria;
import main.java.genetico.algoritmos.cruce.AlgoritmoCruce;
import main.java.genetico.algoritmos.cruce.CruceOrderBased;
import main.java.genetico.algoritmos.cruce.CrucePositionBased;
import main.java.genetico.algoritmos.mutacion.AlgoritmoMutacion;
import main.java.genetico.algoritmos.mutacion.MutacionIntercambio;
import main.java.genetico.algoritmos.mutacion.MutacionInversion;
import main.java.genetico.algoritmos.reemplazo.*;
import main.java.genetico.algoritmos.seleccion.*;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

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
    private ComboBox<String> cmbCreacion;
    @FXML
    private ComboBox<String> cmbSeleccion;
    @FXML
    private ComboBox<String> cmbCruce;
    @FXML
    private ComboBox<String> cmbMutacion;
    @FXML
    private ComboBox<String> cmbReemplazo;

    @FXML private TextField identEjecucion;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
   /*     // bind the selected fruit label to the selected fruit in the combo box.
        lblCreacion.textProperty().bind(cmbCreacion.getSelectionModel().selectedItemProperty());

        // listen for changes to the fruit combo box selection and update the displayed fruit image accordingly.

    }

    public Controller() {*/

        ObservableList<String> optionsCreacion =
                FXCollections.observableArrayList(
                        "Aleatoria"
                );
        ObservableList<String> optionsSeleccion =
                FXCollections.observableArrayList(
                        "Aleatoria",
                        "Ruleta",
                        "Ruleta Antiguo",
                        "Torneo"
                );
        ObservableList<String> optionsCruce =
                FXCollections.observableArrayList(
                        "OBX",
                        "PBX"
                );
        ObservableList<String> optionsMutacion =
                FXCollections.observableArrayList(
                        "Intercambio",
                        "Inversión"
                );

        ObservableList<String> optionsReemplazo =
                FXCollections.observableArrayList(
                        "Generacional",
                        "Torneo", "Torneo Padres-Hijos",
                        "Torneo Padres-Hijos sin repetición"
                );

        cmbCreacion.setItems(optionsCreacion);
        cmbCreacion.getSelectionModel().select("Aleatoria");

        cmbSeleccion.setItems(optionsSeleccion);
        cmbSeleccion.getSelectionModel().select("Aleatoria");

        cmbCruce.setItems(optionsCruce);
        cmbCruce.getSelectionModel().select("OBX");

        cmbMutacion.setItems(optionsMutacion);
        cmbMutacion.getSelectionModel().select("Intercambio");

        cmbReemplazo.setItems(optionsReemplazo);
        cmbReemplazo.getSelectionModel().select("Padres-Hijos sin repetición");

    }

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

        AlgoritmoGenetico ag = new AlgoritmoGenetico(
                obtenerOperadorCreacion(), obtenerOperadorSeleccion(),
                obtenerOperadorCruce(), obtenerOperadorMutacion(),
                obtenerOperadorReemplazo(), obtenerBusquedaLocal()
        );

        Integer valor1 = spPopuSize.getValue();
        Integer valor4 = spNumGen.getValue();

        ag.setParameters(valor1, valor4, 25); //TODO: añadir numMaxGeneracionesSinMejora a la UI
        lanzarVentanaEspera();
        for (int i = 0; i < spNumEjecuciones.getValue(); i++) {
            ag.lanzarAlgoritmo("Vista", 1);//ag.iniciar("Vista" + i, i);
        }
    }

    private AlgoritmoReemplazo obtenerOperadorReemplazo() {
        switch (cmbReemplazo.getValue()) {
            case "Generacional":
                return new ReemplazoGeneracional();
            case "Torneo":
                return new ReemplazoTorneo();
            case "Torneo Padres-Hijos":
                return new ReemplazoTorneoPH();
            case "Torneo Padres-Hijos sin repetición":
                return new ReemplazoTorneoPHSinRepeticion();
            default:
                return new ReemplazoTorneoPHSinRepeticion();
        }
    }

    private AlgoritmoMutacion obtenerOperadorMutacion() {
        switch (cmbMutacion.getValue()) {
            case "Inversion":
                return new MutacionInversion(spProbMuta.getValue().floatValue());
            case "Intercambio":
                return new MutacionIntercambio(spProbMuta.getValue().floatValue());
            default:
                return new MutacionInversion(spProbMuta.getValue().floatValue());
        }
    }

    private AlgoritmoCruce obtenerOperadorCruce() {
        switch (cmbCruce.getValue()) {
            case "OBX":
                return new CruceOrderBased(spPropCruce.getValue().floatValue());
            case "PBX":
                return new CrucePositionBased(spPropCruce.getValue().floatValue());
            default:
                return new CruceOrderBased(spPropCruce.getValue().floatValue());
        }

    }

    private AlgoritmoSeleccion obtenerOperadorSeleccion() {
        switch (cmbSeleccion.getValue()) {
            case "Aleatoria":
                return new SeleccionParesAleatorios();
            case "Ruleta":
                return new SeleccionRuleta();
            case "Ruleta Antiguo":
                return new SeleccionRuletaAntiguo();
            case "Torneo":
                return new SeleccionTorneo();
            default:
                return new SeleccionParesAleatorios();
        }
    }

    private AlgoritmoCreacion obtenerOperadorCreacion() {
        switch (cmbCreacion.getValue()) {
            case "Aleatoria":
                return new CreacionAleatoria();
            default:
                return new CreacionAleatoria();
        }
    }

    private BusquedaLocal obtenerBusquedaLocal() {
        return new BusquedaIntercambioGrupo(spProbBusq.getValue().floatValue());
    }

    ButtonType printButtonType = new ButtonType("Print", ButtonBar.ButtonData.OK_DONE);

    public void lanzarVentanaEspera(){
        Dialog<ButtonType> dialog
                = getCheckPrintDialog(gp.getScene().getWindow(), "Enter starting check number");
        dialog.showAndWait()
                .filter(result -> result == printButtonType)
                .ifPresent(result -> {
                    // this is for this example only, normaly you already have this value
                });
    }

    public <R extends ButtonType> Dialog getCheckPrintDialog(Window owner, String title) {
        Dialog<R> dialog = new Dialog<>();
        dialog.initOwner(owner);
        dialog.setTitle(title);
        dialog.getDialogPane().getButtonTypes().addAll(printButtonType, ButtonType.CANCEL);

        Button btOk = (Button) dialog.getDialogPane().lookupButton(printButtonType);
        btOk.addEventFilter(ActionEvent.ACTION, event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Print Checks? Are you sure?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait()
                    .filter(result -> result == ButtonType.NO)
                    .ifPresent(result -> event.consume());
        });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Text from = new Text("Starting Number:");
        grid.add(from, 0, 0);

        dialog.getDialogPane().setContent(grid);
        return dialog;
    }
}

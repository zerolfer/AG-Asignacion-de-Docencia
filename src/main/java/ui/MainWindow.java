package main.java.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindow extends Application {

    Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage=primaryStage;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../../resources/ui/mainWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Planificador de Docencia");
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }

}
package com.example.progetto;

import com.example.progetto.GestionePersonale.Control.GestoreLogin;
import com.example.progetto.Utils.MailUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        stage.setTitle("Employee | Team DDLL");
        stage.setOnCloseRequest(c -> {
            Platform.exit();
            System.exit(0);
        });
        MailUtils.inviaMail("prov","Prova1","davidebonura58@gmail.comgit");
        new GestoreLogin(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
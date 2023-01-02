package com.employee.progetto;

import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
import com.employee.progetto.Utils.DBMS;
import javafx.application.Application;
import javafx.application.Platform;
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
        new GestoreLogin(stage);
    }
    public static void main(String[] args) {
        //per simulare la proposta turni:
        //DBMS.setDataInizioTrimestre(LocalDate.now().plusMonths(-3));
        // la boundary chiede da sola la data ogni giorni o a ogni esecuzione
        new BoundarySistema(new GestoreSistema());
        launch();
    }
}
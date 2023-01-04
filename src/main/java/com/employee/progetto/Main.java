package com.employee.progetto;

import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
import com.employee.progetto.Utils.DBMS;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Main extends Application {
    public static Stage mainStage;
    @Override
    public void start(Stage stage){
        mainStage = stage;
        stage.setTitle("Employee | Team DDLL");
        stage.setOnCloseRequest(c -> {
            Platform.exit();
            System.exit(0);
        });
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
        new GestoreLogin(stage);
    }
    public static void main(String[] args) {
        System.out.println(((DBMS.getDataInizioTrimestre() + " - " + LocalDate.parse(DBMS.getDataInizioTrimestre()).plusMonths(3))));
        //per simulare la proposta turni:
        //DBMS.setDataInizioTrimestre(LocalDate.now().plusMonths(-3));
        // la boundary chiede da sola la data ogni giorni o a ogni esecuzione
        new BoundarySistema(new GestoreSistema());
        launch();
    }
}
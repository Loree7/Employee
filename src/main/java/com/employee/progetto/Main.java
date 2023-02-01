package com.employee.progetto;

import com.employee.progetto.Common.BoundarySistema;
import com.employee.progetto.Common.GestoreSistema;
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
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/logo.png")));
        stage.setOnCloseRequest(c -> {
            Platform.exit();
            System.exit(0);
        });
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
        new GestoreLogin(stage);
    }
    public static void main(String[] args) {
        System.out.println(((DBMS.getDataInizioTrimestre() + " - " + LocalDate.parse(DBMS.getDataInizioTrimestre()).plusMonths(3))));
        new BoundarySistema(new GestoreSistema());
        launch();
    }
}
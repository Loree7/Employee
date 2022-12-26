package com.employee.progetto;

import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.MailUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    public static void generaTurni(){
        //Almeno 5 impiegati per funzionare
        int numImpiegati = DBMS.getNumPersonale();
        int servizio1 = (int) Math.floor(numImpiegati/2.5);
        int servizio2 = numImpiegati/3;
        int servizio3 = numImpiegati/4;
        int servizio4 = numImpiegati - servizio1-servizio2-servizio3;
        if(servizio3 > (servizio4+4)){
            servizio3 -= 2;
            servizio4 += 2;
        }
        System.out.println("Servizio 1: " + servizio1);
        System.out.println("Servizio 2: " + servizio2);
        System.out.println("Servizio 3: " + servizio3);
        System.out.println("Servizio 4: " + servizio4);
    }
    public static void propostaTurni() {
        LocalDate dataInizioTrimestre = LocalDate.parse(DBMS.getDataInizioTrimestre());
        Instant instant = dataInizioTrimestre.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Duration duration = Duration.between(instant, Instant.now());
        if (duration.toDays() >= 90) {
            DBMS.setDataInizioTrimestre(dataInizioTrimestre.plusMonths(3));
            generaTurni();
        }
    }
    public static void main(String[] args) {
        //creare una boundary e control per chiamare un metodo che fa ste due righe ?
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        //executor.scheduleAtFixedRate(Main::propostaTurni, 0, 1, TimeUnit.DAYS);
        launch();
    }
}
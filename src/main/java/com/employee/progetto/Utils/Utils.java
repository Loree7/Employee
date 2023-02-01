package com.employee.progetto.Utils;

import com.employee.progetto.Common.PannelloConferma;
import com.employee.progetto.Common.PannelloErrore;
import com.employee.progetto.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Utils {
    public static FXMLLoader creaLoader(String path) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(path));
        return loader;
    }
    private static void creaInterfaccia(FXMLLoader loader, int w, int h, Stage stage) {
        if (Main.mainStage != null) {
            try {
                stage.initOwner(Main.mainStage);
                stage.initModality(Modality.APPLICATION_MODAL);
            } catch (Exception e) {
            }
        }
        stage.setResizable(false);
        stage.show();
        try {
            Scene s = new Scene(loader.load(), w, h);
            stage.setScene(s);
            stage.show();
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Object cambiaInterfaccia(String interfaccia,String titolo,Stage stage, Callback c) {
        FXMLLoader loader = creaLoader(interfaccia);
        loader.setControllerFactory(c);
        if (titolo.equals("Portale Impiegato")) {
            creaInterfaccia(loader, 700, 400, stage);
        } else {
            creaInterfaccia(loader, 600, 400, stage);
        }
        stage.setTitle(titolo);
        return loader.getController();
    }public static Object cambiaInterfaccia(String interfaccia, Stage stage, Callback c, int w, int h) {
        FXMLLoader loader = creaLoader(interfaccia);
        loader.setControllerFactory(c);
        creaInterfaccia(loader, w, h, stage);
        return loader.getController();
    }
    public static void creaPannelloErrore(String messaggio) {
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Errore");
        if (Main.mainStage != null) {
            try {
                stage.initOwner(Main.mainStage);
                stage.initModality(Modality.APPLICATION_MODAL);
            } catch (Exception e) {
            }
        }
        FXMLLoader loader = creaLoader("Pannelli/Errore.fxml");
        loader.setControllerFactory(c -> {
            return new PannelloErrore(messaggio, stage);
        });
        creaInterfaccia(loader, 500, 350, stage);
    }
    public static void creaPannelloConferma(String messaggio) {
        Stage stage = new Stage();
        stage.setTitle("Conferma");
        stage.setResizable(false);
        if (Main.mainStage != null) {
            try {
                stage.initOwner(Main.mainStage);
                stage.initModality(Modality.APPLICATION_MODAL);
            } catch (Exception e) {
            }
        }
        FXMLLoader loader = creaLoader("Pannelli/Conferma.fxml");
        loader.setControllerFactory(c -> {
            return new PannelloConferma(messaggio, stage);
        });
        creaInterfaccia(loader, 500, 350, stage);
    }
}

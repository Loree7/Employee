package com.example.progetto.Utils;

import com.example.progetto.Common.PannelloConferma;
import com.example.progetto.Common.PannelloErrore;
import com.example.progetto.Main;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Object cambiaInterfaccia(String interfaccia, Stage stage, Callback c) {
        FXMLLoader loader = creaLoader(interfaccia);
        loader.setControllerFactory(c);
        creaInterfaccia(loader, 600, 400, stage);
        return loader.getController();
    }
    public static Object cambiaInterfaccia(String interfaccia, Stage stage, Callback c, int w, int h) {
        FXMLLoader loader = creaLoader(interfaccia);
        loader.setControllerFactory(c);
        creaInterfaccia(loader, w, h, stage);
        return loader.getController();
    }
    public static Object cambiaInterfaccia(String interfaccia, Stage stage, int w, int h) {
        FXMLLoader loader = creaLoader(interfaccia);
        creaInterfaccia(loader, w, h, stage);
        return loader.getController();
    }
    public static void creaPannelloErrore(String messaggio) {
        creaPannelloErrore(messaggio, null);
    }

    public static void creaPannelloErrore(String messaggio, Stage daDistruggere) {
        Stage stage = new Stage();
        stage.setResizable(false);
        if (Main.mainStage != null) {
            try {
                stage.initOwner(Main.mainStage);
                stage.initModality(Modality.APPLICATION_MODAL);
            } catch (Exception e) {
            }
        }
        FXMLLoader loader = creaLoader("Pannelli/Errore.fxml");
        loader.setControllerFactory(c -> {
            return new PannelloErrore(messaggio, daDistruggere);
        });
        creaInterfaccia(loader, 400, 250, stage);
    }
    public static void creaPannelloConferma(String messaggio) {
        creaPannelloConferma(messaggio, null);
    }

    public static void creaPannelloConferma(String messaggio, Stage daDistruggere) {
        Stage stage = new Stage();
        FXMLLoader loader = creaLoader("Pannelli/Conferma.fxml");
        loader.setControllerFactory(c -> {
            return new PannelloConferma(messaggio, daDistruggere);
        });
        creaInterfaccia(loader, 400, 250, stage);
    }
}

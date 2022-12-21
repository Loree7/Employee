package com.example.progetto.Common;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class PannelloConferma extends Pannello {
    public PannelloConferma(String messaggioConferma, Stage s) {
        super(messaggioConferma, s);
    }
    @FXML
    public void initialize(){
        s.setTitle("Conferma");
    }
    @FXML
    protected void cliccaConferma() {
        if (s != null) s.close();
        ((Stage) messaggio.getScene().getWindow()).close();
    }
}
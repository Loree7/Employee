package com.example.progetto.Common;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class PannelloErrore extends Pannello{
    public PannelloErrore(String messaggioErrore, Stage s) {
        super(messaggioErrore, s);
    }
    @FXML
    protected void cliccaConferma() {
        if (s != null) s.close();
        ((Stage) messaggio.getScene().getWindow()).close();
    }
}

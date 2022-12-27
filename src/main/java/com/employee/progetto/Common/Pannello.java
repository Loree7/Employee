package com.employee.progetto.Common;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Pannello {
    @FXML
    protected Label messaggio;
    protected String messaggioText;
    protected Stage s;
    public Pannello(String messaggio, Stage s) {
        this.messaggioText = messaggio;
        this.s = s;
    }
    @FXML
    protected void initialize() {
        messaggio.setText(messaggioText);
    }
}

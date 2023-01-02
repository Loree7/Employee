package com.employee.progetto.GestionePersonale.Boundary;

import com.employee.progetto.GestionePersonale.Control.GestoreRilevazionePresenza;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModuloRilevazionePresenza {
    private GestoreRilevazionePresenza gestoreRilevazionePresenza;
    public ModuloRilevazionePresenza(GestoreRilevazionePresenza gestoreRilevazionePresenza){
        this.gestoreRilevazionePresenza = gestoreRilevazionePresenza;
    }
    @FXML
    public void initialize(){
        nome.setOnAction(event -> {
            cognome.requestFocus();
        });
        cognome.setOnAction(event -> {
            matricola.requestFocus();
        });
        matricola.setOnAction(event -> {
            cliccaConferma();
        });
    }
    @FXML
    private TextField nome,cognome,matricola;
    @FXML
    public void cliccaConferma(){
        nome.requestFocus();
        gestoreRilevazionePresenza.rilevaPresenza(nome.getText(),cognome.getText(),matricola.getText(),(Stage) nome.getScene().getWindow());
    }
}

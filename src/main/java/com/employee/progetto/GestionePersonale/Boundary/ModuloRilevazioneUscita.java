package com.employee.progetto.GestionePersonale.Boundary;

import com.employee.progetto.GestionePersonale.Control.GestoreRilevazionePresenza;
import com.employee.progetto.GestionePersonale.Control.GestoreRilevazioneUscita;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModuloRilevazioneUscita {
    private GestoreRilevazioneUscita gestoreRilevazioneUscita;
    public ModuloRilevazioneUscita(GestoreRilevazioneUscita gestoreRilevazioneUscita){
        this.gestoreRilevazioneUscita = gestoreRilevazioneUscita;
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
        gestoreRilevazioneUscita.rilevaUscita(nome.getText(),cognome.getText(),matricola.getText(),(Stage) nome.getScene().getWindow());
    }
}

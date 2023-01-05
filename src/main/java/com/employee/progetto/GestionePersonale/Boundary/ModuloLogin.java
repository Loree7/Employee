package com.employee.progetto.GestionePersonale.Boundary;

import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
import com.employee.progetto.GestionePersonale.Control.GestoreRilevazionePresenza;
import com.employee.progetto.GestionePersonale.Control.GestoreRilevazioneUscita;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModuloLogin {
    private GestoreLogin gestoreLogin;
    public ModuloLogin(GestoreLogin gestoreLogin){
        this.gestoreLogin = gestoreLogin;
    }
    @FXML
    public void initialize(){
        matricola.setOnAction(event -> {
            password.requestFocus();
        });
        password.setOnAction(event -> {
            cliccaLogin();
        });
    }
    @FXML
    private TextField matricola;
    @FXML
    private PasswordField password;
    @FXML
    private Hyperlink rilevazionePresenza,rilevazioneUscita;
    @FXML
    public void cliccaLogin(){
        matricola.requestFocus();
        gestoreLogin.verificaCredenziali(matricola.getText(),password.getText(),(Stage) matricola.getScene().getWindow());
    }
    @FXML
    public void cliccaRilevazionePresenza(){
        matricola.requestFocus();
        rilevazionePresenza.setVisited(false);
        new GestoreRilevazionePresenza();
    }
    @FXML
    public void cliccaRilevazioneUscita(){
        matricola.requestFocus();
        rilevazioneUscita.setVisited(false);
        new GestoreRilevazioneUscita();
    }
}
package com.example.progetto.GestionePersonale.Interface;

import com.example.progetto.GestionePersonale.Control.GestoreLogin;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModuloLogin {
    private GestoreLogin gestoreLogin;
    public ModuloLogin(GestoreLogin gestoreLogin){
        this.gestoreLogin = gestoreLogin;
    }
    @FXML
    private TextField matricola;
    @FXML
    private PasswordField password;
    @FXML
    public void cliccaLogin(){
        gestoreLogin.verificaCredenziali(matricola.getText(),password.getText(),(Stage) matricola.getScene().getWindow());
    }
}
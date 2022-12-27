package com.employee.progetto.GestionePersonale.Boundary;

import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
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
    public void cliccaLogin(){
        matricola.requestFocus();
        gestoreLogin.verificaCredenziali(matricola.getText(),password.getText(),(Stage) matricola.getScene().getWindow());
    }
}
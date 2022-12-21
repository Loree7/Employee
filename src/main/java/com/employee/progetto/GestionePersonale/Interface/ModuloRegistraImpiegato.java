package com.employee.progetto.GestionePersonale.Interface;

import com.employee.progetto.GestionePersonale.Control.GestoreRegistraImpiegato;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModuloRegistraImpiegato {
    private GestoreRegistraImpiegato gestoreRegistraImpiegato;
    public ModuloRegistraImpiegato(GestoreRegistraImpiegato gestoreRegistraImpiegato){
        this.gestoreRegistraImpiegato = gestoreRegistraImpiegato;
    }
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private TextField ruolo;
    @FXML
    private TextField email;
    @FXML
    public void cliccaRegistra(){
        gestoreRegistraImpiegato.registraImpiegato(nome.getText(),cognome.getText(),ruolo.getText(),email.getText(),(Stage) nome.getScene().getWindow());
    }
}

package com.employee.progetto.GestionePersonale.Interface;

import com.employee.progetto.GestionePersonale.Control.GestoreRicercaImpiegato;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModuloRicercaImpiegato {
    GestoreRicercaImpiegato gestoreRicercaImpiegato;
    public ModuloRicercaImpiegato(GestoreRicercaImpiegato gestoreRicercaImpiegato){
        this.gestoreRicercaImpiegato = gestoreRicercaImpiegato;
    }
    @FXML
    private TextField matricola;
    public void cliccaRicerca(){
        gestoreRicercaImpiegato.ricercaImpiegato(matricola.getText(),(Stage) matricola.getScene().getWindow());
    }
}

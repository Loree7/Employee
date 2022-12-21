package com.example.progetto.GestionePersonale.Interface;

import com.example.progetto.GestionePersonale.Control.GestoreRicercaImpiegato;
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

package com.employee.progetto.GestionePersonale.Boundary;

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
    public void initialize(){
        matricola.setOnAction(event -> {
            cliccaRicerca();
        });
    }
    @FXML
    private TextField matricola;
    public void cliccaRicerca(){
        gestoreRicercaImpiegato.ricercaImpiegato(matricola.getText(),(Stage) matricola.getScene().getWindow());
    }
}

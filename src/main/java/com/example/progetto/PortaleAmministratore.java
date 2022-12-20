package com.example.progetto;

import com.example.progetto.GestionePersonale.Control.GestoreRegistraImpiegato;
import com.example.progetto.GestionePersonale.Control.GestoreRicercaImpiegato;
import javafx.fxml.FXML;

public class PortaleAmministratore {
    @FXML
    public void registraImpiegato(){
        new GestoreRegistraImpiegato();
    }
    @FXML
    public void ricercaImpiegato(){
        new GestoreRicercaImpiegato();
    }
}

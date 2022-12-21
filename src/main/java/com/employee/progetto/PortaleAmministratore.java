package com.employee.progetto;

import com.employee.progetto.GestionePersonale.Control.GestoreRegistraImpiegato;
import com.employee.progetto.GestionePersonale.Control.GestoreRicercaImpiegato;
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

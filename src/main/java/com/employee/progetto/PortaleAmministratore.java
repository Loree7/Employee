package com.employee.progetto;

import com.employee.progetto.GestionePersonale.Control.GestoreRegistraImpiegato;
import com.employee.progetto.GestionePersonale.Control.GestoreRicercaImpiegato;
import com.employee.progetto.GestioneServizi.Control.GestoreVisualizzaServizi;
import com.employee.progetto.GestioneTurni.Control.GestoreVisualizzaTurni;
import javafx.fxml.FXML;

public class PortaleAmministratore {
    @FXML
    public void cliccaRegistraImpiegato(){
        new GestoreRegistraImpiegato();
    }
    @FXML
    public void cliccaRicercaImpiegato(){
        new GestoreRicercaImpiegato();
    }
    @FXML
    public void cliccaVisualizzaTurniAmministratore(){
        new GestoreVisualizzaTurni();
    }
    @FXML
    public void cliccaVisualizzaServizi(){
        new GestoreVisualizzaServizi();
    }
}

package com.employee.progetto;

import com.employee.progetto.GestionePersonale.Control.GestoreComunicaStraordinari;
import com.employee.progetto.GestionePersonale.Control.GestoreRegistraImpiegato;
import com.employee.progetto.GestionePersonale.Control.GestoreRicercaImpiegato;
import com.employee.progetto.GestioneServizi.Control.GestoreVisualizzaServizi;
import com.employee.progetto.GestioneTurni.Control.GestoreVisualizzaTurni;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.Utils;
import javafx.fxml.FXML;

import java.time.LocalDate;

public class PortaleAmministratore {
    @FXML
    public void cliccaRegistraImpiegato(){
        LocalDate now = LocalDate.now();
        LocalDate dataFineTrimestre= LocalDate.parse(DBMS.getDataInizioTrimestre()).plusMonths(3).plusDays(-1);
        //L'amministratore può registrare solo se siamo un giorno prima l'inizio del prossimo trimestre
        if(now.equals(dataFineTrimestre))
            new GestoreRegistraImpiegato();
        else{
            Utils.creaPannelloErrore("Non puoi registrare gli impiegati durante il corso del trimestre");
            return;
        }
    }
    @FXML
    public void cliccaRicercaImpiegato(){
        new GestoreRicercaImpiegato();
    }
    @FXML
    public void cliccaComunicaStraordinari(){new GestoreComunicaStraordinari();}
    @FXML
    public void cliccaVisualizzaTurniAmministratore(){
        new GestoreVisualizzaTurni();
    }
    @FXML
    public void cliccaVisualizzaServizi(){
        new GestoreVisualizzaServizi();
    }
}

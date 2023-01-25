package com.employee.progetto;

import com.employee.progetto.Entity.Impiegato;
import com.employee.progetto.GestioneAstensioni.Control.*;
import com.employee.progetto.GestioneImpiegato.Control.GestoreRichiestaPermesso;
import com.employee.progetto.GestioneImpiegato.Control.GestoreComunicaRitardo;
import com.employee.progetto.GestioneImpiegato.Control.GestoreSituazioneLavorativa;
import com.employee.progetto.GestioneImpiegato.Control.GestoreVisualizzaDati;
import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
import com.employee.progetto.GestioneServizi.Control.GestoreVisualizzaServizi;
import com.employee.progetto.GestioneTurni.Control.GestoreVisualizzaTurni;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.Utils;
import javafx.fxml.FXML;

import java.time.LocalDate;

public class PortaleImpiegato {
    @FXML
    public void cliccaVisualizzaTurni(){
        new GestoreVisualizzaTurni();
    }
    @FXML
    public void cliccaSituazioneLavorativa(){
        new GestoreSituazioneLavorativa();
    }
    @FXML
    public void cliccaComunicaRitardo(){
        new GestoreComunicaRitardo();
    }
    @FXML
    public void cliccaVisualizzaDati(){
        new GestoreVisualizzaDati();
    }
    @FXML
    public void cliccaRichiestaPermesso(){
        if(((Impiegato) GestoreLogin.getUtente()).getOrePermesso()==0){
            Utils.creaPannelloErrore("Non hai più ore di permesso rimanenti");
            return;
        }
        new GestoreRichiestaPermesso();
    }
    @FXML
    public void cliccaComunicaAssenza(){
        new GestoreComunicaAssenza();
    }
    @FXML
    public void cliccaComunicaCongedo(){
        LocalDate now = LocalDate.now();
        LocalDate dataFineTrimestre= LocalDate.parse(DBMS.getDataInizioTrimestre()).plusMonths(3).plusDays(-1);
        //simulazione
        //now=dataFineTrimestre;
        if(now.equals(dataFineTrimestre))
            new GestoreComunicaCongedo();
        else
            Utils.creaPannelloErrore("Non puoi comunicare il congedo durante il corso del trimestre");
    }
    @FXML
    public void cliccaComunicaMalattia(){
        new GestoreComunicaMalattia();
    }
    @FXML
    public void cliccaRichiestaFerie(){
        int ferieRimanenti = ((Impiegato) GestoreLogin.getUtente()).getFerie();
        if(ferieRimanenti == 0){
            Utils.creaPannelloErrore("Non hai più ferie rimanenti");
            return;
        }
        LocalDate now = LocalDate.now();
        LocalDate dataFineTrimestre= LocalDate.parse(DBMS.getDataInizioTrimestre()).plusMonths(3).plusDays(-1);
        //simulazione
        //now=dataFineTrimestre;
        if(now.equals(dataFineTrimestre))
            new GestoreRichiestaFerie();
        else
            Utils.creaPannelloErrore("Non puoi richiedere le ferie durante il corso del trimestre");
    }
    @FXML
    public void cliccaRichiestaSciopero(){
        new GestoreRichiestaSciopero();
    }
    @FXML
    public void cliccaVisualizzaServizi() { new GestoreVisualizzaServizi(); }
}

package com.employee.progetto;

import com.employee.progetto.Entity.Impiegato;
import com.employee.progetto.GestioneAstensioni.Control.*;
import com.employee.progetto.GestioneImpiegato.Control.GestoreRichiestaPermesso;
import com.employee.progetto.GestioneImpiegato.Control.GestoreComunicaRitardo;
import com.employee.progetto.GestioneImpiegato.Control.GestoreSituazioneLavorativa;
import com.employee.progetto.GestioneImpiegato.Control.GestoreVisualizzaDati;
import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
import com.employee.progetto.GestionePersonale.Control.GestoreRilevazionePresenza;
import com.employee.progetto.GestioneTurni.Control.GestoreVisualizzaTurni;
import com.employee.progetto.Utils.Utils;
import javafx.fxml.FXML;

public class PortaleImpiegato {
    @FXML
    public void cliccaRivelazionePresenza() {new GestoreRilevazionePresenza();}
    @FXML
    public void cliccaVisualizzaTurniImpiegato(){
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
        new GestoreRichiestaPermesso();
    }
    @FXML
    public void cliccaComunicaAssenza(){
        new GestoreComunicaAssenza();
    }
    @FXML
    public void cliccaComunicaCongedo(){
        new GestoreComunicaCongedo();
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
        new GestoreRichiestaFerie();
    }
    @FXML
    public void cliccaRichiestaSciopero(){
        new GestoreRichiestaSciopero();
    }
}

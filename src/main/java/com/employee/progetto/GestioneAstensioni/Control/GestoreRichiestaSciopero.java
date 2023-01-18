package com.employee.progetto.GestioneAstensioni.Control;

import com.employee.progetto.GestioneAstensioni.Boundary.ModuloRichiestaSciopero;
import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class GestoreRichiestaSciopero {
    public GestoreRichiestaSciopero(){
        Utils.cambiaInterfaccia("GestioneAstensioni/ModuloRichiestaSciopero.fxml",
                "Modulo Richiesta Sciopero",
                new Stage(), c->{
                    return new ModuloRichiestaSciopero(this);
                });
    }
    public void richiediSciopero(LocalDate data,Stage s){
        if(data == null){
            Utils.creaPannelloErrore("Inserisci la data");
            return;
        }
        if(data.compareTo(LocalDate.now())<0){
            Utils.creaPannelloErrore("La data non può essere antecedente alla data corrente");
            return;
        }
        if(data.compareTo(LocalDate.now())==0){
            Utils.creaPannelloErrore("Non puoi richiedere lo sciopero per la data corrente");
            return;
        }
        if(data.compareTo(LocalDate.now().plusDays(1))==0 && LocalTime.now().getHour()>=16){
            Utils.creaPannelloErrore("Puoi richiedere lo sciopero per il giorno dopo solo se non sono passate le 16");
            return;
        }
        String matricola = GestoreLogin.getUtente().getMatricola();
        if(!DBMS.controllaAstensione(data,data,matricola)){
            Utils.creaPannelloConferma("Richiesta di sciopero effettuata con successo");
            s.close(); //Chiudo il modulo
            DBMS.inserisciSciopero(matricola,data);
        }else
            Utils.creaPannelloErrore("Per la data inserita è già presente una astensione");
    }
}

package com.employee.progetto.GestioneAstensioni.Control;

import com.employee.progetto.GestioneAstensioni.Boundary.ModuloRichiestaSciopero;
import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;

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
        String matricola = GestoreLogin.getUtente().getMatricola();
        if(!DBMS.controllaAstensione(data,data,matricola)){
            Utils.creaPannelloConferma("Richiesta di sciopero effettuata con successo");
            s.close(); //Chiudo il modulo
            DBMS.inserisciSciopero(matricola,data);
        }else
            Utils.creaPannelloErrore("Per la data inserita è già presente una astensione");
    }
}

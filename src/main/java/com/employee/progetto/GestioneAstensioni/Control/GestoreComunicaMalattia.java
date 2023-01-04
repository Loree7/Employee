package com.employee.progetto.GestioneAstensioni.Control;

import com.employee.progetto.GestioneAstensioni.Boundary.ModuloComunicaMalattia;
import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GestoreComunicaMalattia {
    public GestoreComunicaMalattia(){
        Utils.cambiaInterfaccia("GestioneAstensioni/ModuloComunicaMalattia.fxml",
                "Modulo Comunica Malattia",
                new Stage(), c->{
                    return new ModuloComunicaMalattia(this);
                });
    }
    public void comunicaMalattia(LocalDate data_inizio, LocalDate data_fine, Stage s){
        if(data_inizio == null || data_fine == null){
            Utils.creaPannelloErrore("Completa tutti i campi");
            return;
        }
        if(data_inizio.compareTo(LocalDate.now())<0){
            Utils.creaPannelloErrore("La data d'inizio non può essere antecedente alla data corrente");
            return;
        }
        if(data_inizio.compareTo(data_fine)>0){
            Utils.creaPannelloErrore("La data d'inizio non può essere dopo quella di fine");
            return;
        }
        String matricola = GestoreLogin.getUtente().getMatricola();
        if(!DBMS.controllaAstensione(data_inizio,data_fine,matricola)){
            Utils.creaPannelloConferma("Malattia comunicata correttamente");
            s.close(); //chiudo modulo
            DBMS.inserisciCongedo(data_inizio,data_fine,matricola);
        }else
            Utils.creaPannelloErrore("La data d'inizio o di fine, o entrambe, rientrano in delle astensioni già esistenti");
    }
}

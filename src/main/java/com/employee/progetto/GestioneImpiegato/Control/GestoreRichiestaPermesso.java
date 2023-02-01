package com.employee.progetto.GestioneImpiegato.Control;

import com.employee.progetto.Entity.Impiegato;
import com.employee.progetto.GestioneImpiegato.Boundary.ModuloRichiestaPermesso;
import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class GestoreRichiestaPermesso {
    public GestoreRichiestaPermesso(){
        Utils.cambiaInterfaccia("GestioneImpiegato/ModuloRichiestaPermesso.fxml",
                "Modulo Richiesta Permesso",
                new Stage(), c->{
                    return new ModuloRichiestaPermesso(this);
                });
    }
    public void richiediPermesso(LocalDate data, LocalTime ora_inizio, LocalTime ora_fine,int ore,Stage s){
        if(data==null || ora_inizio==null || ora_fine==null){
            Utils.creaPannelloErrore("Compila tutti i campi");
            return;
        }
        if(data.compareTo(LocalDate.now())<0){
            Utils.creaPannelloErrore("La data d'inizio non può essere antecedente alla data corrente");
            return;
        }
        if(ora_inizio.isAfter(ora_fine) || ora_inizio.compareTo(ora_fine)==0){
            Utils.creaPannelloErrore("Non puoi inserire un'ora di inizio maggiore o uguale a quella di fine");
            return;
        }
        int orePermesso = ((Impiegato) GestoreLogin.getUtente()).getOrePermesso();
        int oreRichieste = ore - (ora_fine.getHour()-ora_inizio.getHour());
        if(orePermesso<(oreRichieste)){
            Utils.creaPannelloErrore("Non puoi richiedere queste ore poichè le tue ore rimanenti equivalgono a: "+orePermesso);
            return;
        }
        Utils.creaPannelloConferma("Richiesta di permesso effettuata con successo");
        s.close(); //chiudo il modulo
        DBMS.aggiornaTurno(GestoreLogin.getUtente().getMatricola(),data,ora_inizio,ora_fine);
        orePermesso -= oreRichieste;
        DBMS.aggiornaOrePermesso(GestoreLogin.getUtente().getMatricola(),orePermesso);
        ((Impiegato) GestoreLogin.getUtente()).setOrePermesso(orePermesso);
    }
}

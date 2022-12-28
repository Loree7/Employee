package com.employee.progetto.GestioneAstensioni.Control;

import com.employee.progetto.Entity.Impiegato;
import com.employee.progetto.GestioneAstensioni.Boundary.ModuloRichiestaFerie;
import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class GestoreRichiestaFerie {
    public GestoreRichiestaFerie(){
        Utils.cambiaInterfaccia("GestioneAstensioni/ModuloRichiestaFerie.fxml",
                "Modulo Richiesta Ferie",
                new Stage(), c->{
                    return new ModuloRichiestaFerie(this);
                });
    }
    public void richiedi(LocalDate data_inizio, LocalDate data_fine,Stage s){
        int ferieRimanenti = ((Impiegato) GestoreLogin.getUtente()).getFerie();
        if(data_inizio == null || data_fine == null){
            Utils.creaPannelloErrore("Completa tutti i campi");
            return;
        }
        if(data_inizio.compareTo(LocalDate.now())<0){
            Utils.creaPannelloErrore("La data d'inizio non può essere minore\ndella data corrente");
            return;
        }
        if(data_inizio.compareTo(data_fine)>0){
            Utils.creaPannelloErrore("La data d'inizio non può essere dopo quella di fine");
            return;
        }
        long giorni = ChronoUnit.DAYS.between(data_inizio, data_fine);
        if(giorni > ferieRimanenti){ //Se l'impiegato richiede più ferie di quanto gli rimangono
            Utils.creaPannelloErrore("Non puoi richiedere più di 4 settimane di ferie");
            return;
        }
        //Mettere periodi dove non si può andare in ferie nel db tipo? e fare una query per prenderle e controllare
        if(DBMS.controllaPeriodo(data_inizio,data_fine)){
            if(!DBMS.controllaFerie(data_inizio,data_fine, ((Impiegato) GestoreLogin.getUtente()).getMatricola())) {
                Utils.creaPannelloConferma("Richiesta di ferie effettuata con successo");
                s.close(); // chiudo Modulo
                ferieRimanenti -= giorni;
                ((Impiegato) GestoreLogin.getUtente()).setFerie(ferieRimanenti);
                DBMS.inserisciFerie(data_inizio, data_fine, GestoreLogin.getUtente().getMatricola(), ferieRimanenti);
                return;
            }else{
                Utils.creaPannelloErrore("Hai già richiesto queste ferie");
                return;
            }
        }else{
            LocalDate inizio_periodo = LocalDate.of(2022, 6, 1);
            LocalDate fine_periodo = LocalDate.of(2022, 7, 1);
            Utils.creaPannelloErrore("Una o entrambe le date non rientrano nel seguente periodo\n" +
                    inizio_periodo + " - " + fine_periodo);
            return;
        }
    }
}

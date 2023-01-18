package com.employee.progetto.GestioneAstensioni.Control;

import com.employee.progetto.Entity.Impiegato;
import com.employee.progetto.GestioneAstensioni.Boundary.ModuloComunicaAssenza;
import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.MailUtils;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GestoreComunicaAssenza {
    public GestoreComunicaAssenza(){
        Utils.cambiaInterfaccia("GestioneAstensioni/ModuloComunicaAssenza.fxml",
                "Modulo Comunica Assenza",
                new Stage(), c->{
                    return new ModuloComunicaAssenza(this);
                });
    }
    public void comunicaAssenza(LocalDate data,Stage s){
        if(data == null){
            Utils.creaPannelloErrore("Inserisci la data");
            return;
        }
        if(data.compareTo(LocalDate.now())<0){
            Utils.creaPannelloErrore("La data non può essere antecedente alla data corrente");
            return;
        }
        if(data.compareTo(LocalDate.now())==0){
            Utils.creaPannelloErrore("Non puoi comunicare l'assenza per la data corrente");
            return;
        }
        int id_turno = DBMS.controllaInTurno(GestoreLogin.getUtente().getMatricola(),data);
        if(id_turno != 0){
            Utils.creaPannelloConferma("Assenza comunicata correttamente");
            Impiegato impiegato = DBMS.scambiaTurno(data.plusDays(1),id_turno,GestoreLogin.getUtente().getMatricola());
            if(impiegato != null) //scambio effettuato correttamente
                MailUtils.inviaMail("testo","oggetto",impiegato.getEmail());
            else { //se non esiste un impiegato con cui scambiare il turno comunico straordinari
                Impiegato sostituto = DBMS.sostituisciTurno(data,id_turno);
                if (sostituto != null)
                    MailUtils.inviaMail(sostituto.getNome() + " " + sostituto.getCognome() +
                    " sei stato scelto per svolgere gli straordinari giorno: " + data
                    , "Straordinari", sostituto.getEmail());
                else
                    DBMS.eliminaTurno(GestoreLogin.getUtente().getMatricola(), data);
            }
            s.close();
        }else
            Utils.creaPannelloErrore("Non sei in turno nella data inserita");
    }
}
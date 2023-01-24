package com.employee.progetto.GestioneAstensioni.Control;

import com.employee.progetto.Entity.Impiegato;
import com.employee.progetto.GestioneAstensioni.Boundary.ModuloComunicaMalattia;
import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.MailUtils;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Period;

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
            DBMS.inserisciMalattia(data_inizio,data_fine,matricola);
            int durataMalattia = Period.between(data_inizio,data_fine).getDays()+1;
            while(data_inizio.isBefore(data_fine) || data_inizio.isEqual(data_fine)){
                int id_turno = DBMS.controllaInTurno(GestoreLogin.getUtente().getMatricola(),data_inizio);
                if(id_turno != 0){
                    Impiegato impiegato = DBMS.scambiaTurno(data_inizio.plusDays(durataMalattia),id_turno,GestoreLogin.getUtente().getMatricola());
                    if(impiegato != null) {//scambio effettuato correttamente
                        String info = DBMS.getInfoTurno(id_turno);
                        MailUtils.inviaMail("Gentile impiegato (matricola: " + impiegato.getMatricola() + ") la informiamo che a causa dell'assenza comunicata da un altro impiegato, il suo turno e' stato spostato a " + info, "Cambio turno", impiegato.getEmail());
                        MailUtils.inviaMail("Gentile impiegato (matricola: " + GestoreLogin.getUtente().getMatricola() + ") la informiamo che e' stato trovato un impiegato con cui scambiare il suo turno" ,"Cambio turno per comunicazione malattia", GestoreLogin.getUtente().getEmail());
                    }else { //se non esiste un impiegato con cui scambiare il turno comunico straordinari
                        Impiegato sostituto = DBMS.sostituisciTurno(data_inizio,id_turno);
                        if (sostituto != null)
                            MailUtils.inviaMail(sostituto.getNome() + " " + sostituto.getCognome() +
                                            " sei stato scelto per svolgere gli straordinari giorno: " + data_inizio
                                    , "Straordinari", sostituto.getEmail());
                        else
                            DBMS.eliminaTurno(GestoreLogin.getUtente().getMatricola(), data_inizio);
                    }
                }
                data_inizio = data_inizio.plusDays(1);
            }
        }else
            Utils.creaPannelloErrore("La data d'inizio o di fine, o entrambe, rientrano in delle astensioni già esistenti");
    }
}

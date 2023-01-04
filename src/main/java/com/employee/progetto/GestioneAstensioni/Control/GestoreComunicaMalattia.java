package com.employee.progetto.GestioneAstensioni.Control;

import com.employee.progetto.Entity.Impiegato;
import com.employee.progetto.GestioneAstensioni.Boundary.ModuloComunicaMalattia;
import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.MailUtils;
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
            DBMS.inserisciMalattia(data_inizio,data_fine,matricola);
            while(data_inizio.isBefore(data_fine) || data_inizio.isEqual(data_fine)){
                int id_turno = DBMS.controllaInTurno(GestoreLogin.getUtente().getMatricola(),data_inizio);
                if(id_turno != 0){
                    Impiegato impiegato = DBMS.getImpiegatoMenoOre(data_inizio);
                    if(impiegato!=null) {
                        Utils.creaPannelloConferma("Straordinari comunicati all'impiegato:\n" +
                                impiegato.getNome() + " " + impiegato.getCognome());
                        MailUtils.inviaMail(impiegato.getNome() + " " + impiegato.getCognome() + " sei stato scelto per" +
                                        "svolgere gli straordinari giorno: " + data_inizio
                                , "Straordinari", impiegato.getEmail());
                        DBMS.sostituisciTurno(id_turno,impiegato.getMatricola());
                    }
                }
                data_inizio = data_inizio.plusDays(1);
            }
        }else
            Utils.creaPannelloErrore("La data d'inizio o di fine, o entrambe, rientrano in delle astensioni già esistenti");
    }
}

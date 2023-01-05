package com.employee.progetto.GestionePersonale.Control;

import com.employee.progetto.GestionePersonale.Boundary.ModuloRilevazionePresenza;
import com.employee.progetto.GestionePersonale.Boundary.ModuloRilevazioneUscita;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

public class GestoreRilevazioneUscita {
    public GestoreRilevazioneUscita() {
        Utils.cambiaInterfaccia("GestionePersonale/ModuloRilevazioneUscita.fxml",
                "Modulo Rilevazione Uscita",new Stage(), c -> {
                    return new ModuloRilevazioneUscita(this);
                });
    }
    public void rilevaUscita(String nome,String cognome,String matricola,Stage s){
        if(nome.isBlank() || cognome.isBlank() || matricola.isBlank()){
            Utils.creaPannelloErrore("Compila tutti i campi");
            return;
        }
        int id_turno = DBMS.controllaUscita(nome,cognome,matricola);
        if(id_turno==0){
            Utils.creaPannelloErrore("Non esiste un turno per la data corrente associato ai dati inseriti");
            return;
        }
        if(id_turno==-1){
            Utils.creaPannelloErrore("Non puoi rilevare l'uscita prima che finisca il turno");
            return;
        }
        if(id_turno==-2){
            Utils.creaPannelloErrore("Non esiste un impiegato con i dati inseriti");
            return;
        }
        if(id_turno==-3){
            Utils.creaPannelloErrore("Devi prima rilevare la presenza");
            return;
        }
        Utils.creaPannelloConferma("Rilevazione uscita effettuata");
        s.close();
        DBMS.eliminaTurno(Integer.parseInt(matricola));
    }
}

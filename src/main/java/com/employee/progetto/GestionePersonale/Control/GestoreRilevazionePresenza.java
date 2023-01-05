package com.employee.progetto.GestionePersonale.Control;

import com.employee.progetto.GestionePersonale.Boundary.ModuloRilevazionePresenza;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

public class GestoreRilevazionePresenza {
    public GestoreRilevazionePresenza() {
        Utils.cambiaInterfaccia("GestionePersonale/ModuloRilevazionePresenza.fxml",
                "Modulo Rilevazione Presenza",new Stage(), c -> {
            return new ModuloRilevazionePresenza(this);
        });
    }
    public void rilevaPresenza(String nome,String cognome,String matricola,Stage s){
        if(nome.isBlank() || cognome.isBlank() || matricola.isBlank()){
            Utils.creaPannelloErrore("Compila tutti i campi");
            return;
        }
        int id_turno = DBMS.controllaTurno(nome,cognome,matricola,false);
        if(id_turno==0){
            Utils.creaPannelloErrore("Non esiste un turno per la data corrente associato ai dati inseriti");
            return;
        }
        if(id_turno==-1){
            Utils.creaPannelloErrore("Non puoi rilevare la presenza dopo 10 minuti dall'inizio del turno");
            return;
        }
        if(id_turno==-2){
            Utils.creaPannelloErrore("Rilevazione presenza per i dati inseriti già effettuata");
            return;
        }
        if(id_turno==-3){
            Utils.creaPannelloErrore("Non esiste un impiegato con i dati inseriti");
            return;
        }
        Utils.creaPannelloConferma("Presenza rilevata correttamente");
        s.close();
        DBMS.rilevaPresenza(id_turno);
    }
}

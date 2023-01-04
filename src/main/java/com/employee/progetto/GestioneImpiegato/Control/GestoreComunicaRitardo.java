package com.employee.progetto.GestioneImpiegato.Control;

import com.employee.progetto.GestioneImpiegato.Boundary.ModuloComunicaRitardo;
import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

public class GestoreComunicaRitardo {
    public GestoreComunicaRitardo(){
        Utils.cambiaInterfaccia("GestioneImpiegato/ModuloComunicaRitardo.fxml",
                "Modulo Comunica Ritardo",
                new Stage(), c->{
                    return new ModuloComunicaRitardo(this);
                });
    }
    public void comunica(String motivazione,Stage s){
        if(motivazione.isBlank()){
            Utils.creaPannelloErrore("Inserisci una motivazione");
            return;
        }
        int id_turno = DBMS.controllaTurno(GestoreLogin.getUtente().getNome(),GestoreLogin.getUtente().getCognome(),GestoreLogin.getUtente().getMatricola(),true);
        if(id_turno==0){
            Utils.creaPannelloErrore("Non esiste un turno per la data corrente associato ai dati inseriti");
            return;
        }
        if(id_turno==-4){
            Utils.creaPannelloErrore("Non puoi comunica il ritardo dopo 1 ora dall'inizio del turno");
            s.close(); //chiudo il modulo
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
        if(id_turno==-5){
            Utils.creaPannelloErrore("Non puoi comunicare il ritardo se non sono passati 10m dall'inizio del turno");
            return;
        }
        Utils.creaPannelloConferma("Ritardo comunicato correttamente");
        s.close();
        DBMS.rilevaPresenza(id_turno);
    }
}

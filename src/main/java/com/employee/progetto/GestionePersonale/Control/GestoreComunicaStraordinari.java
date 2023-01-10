package com.employee.progetto.GestionePersonale.Control;

import com.employee.progetto.Entity.Impiegato;
import com.employee.progetto.GestionePersonale.Boundary.ModuloComunicaStraordinari;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.MailUtils;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class GestoreComunicaStraordinari {
    public GestoreComunicaStraordinari(){
        Utils.cambiaInterfaccia("GestionePersonale/ModuloComunicaStraordinari.fxml",
                "Modulo Comunica Straordinari",
                new Stage(), c->{
                    return new ModuloComunicaStraordinari(this);
                });
    }
    public void comunicaStraordinari(String servizio, LocalDate giorno,LocalTime ora_inizio,LocalTime ora_fine,Stage s){
        if(servizio.isBlank() || giorno == null){
            Utils.creaPannelloErrore("Completa tutti i campi");
            return;
        }
        if(giorno.compareTo(LocalDate.now())<0){
            Utils.creaPannelloErrore("Il giorno non può essere minore\ndella data corrente");
            return;
        }
        if(giorno.compareTo(LocalDate.now())==0){
            Utils.creaPannelloErrore("Non puoi richiedere straordinari per la data corrente");
            return;
        }
        LocalDate dataFineTrimestre = (LocalDate.parse(DBMS.getDataInizioTrimestre())).plusMonths(3).plusDays(-1);
        if(giorno.compareTo(dataFineTrimestre)>0){
            Utils.creaPannelloErrore("Non puoi richiedere straordinari che vanno oltre il trimestre");
        }
        List<String> servizi = DBMS.getServizi();
        for(String str : servizi){
            if(servizio.toLowerCase().equals(str)){
                Impiegato impiegato = DBMS.getImpiegatoMenoOre(giorno);
                if(impiegato==null){
                    Utils.creaPannelloErrore("Non esiste un impiegato libero per questa data");
                    return;
                }
                Utils.creaPannelloConferma("Straordinari comunicati all'impiegato:\n" + impiegato.getMatricola() + " " +
                        impiegato.getNome() + " " + impiegato.getCognome());
                MailUtils.inviaMail(impiegato.getNome() + " " + impiegato.getCognome() + " sei stato scelto per" +
                                "svolgere gli straordinari giorno: " + giorno
                        ,"Straordinari",impiegato.getEmail());
                DBMS.inserisciTurno(ora_inizio,ora_fine,giorno,servizi.indexOf(str)+1,impiegato.getMatricola());
                s.close(); //chiudo il modulo
                return;
            }
        }
        Utils.creaPannelloErrore("Servizio inserito non esistente, prova inserendo:\n" +
        servizi);
    }
}

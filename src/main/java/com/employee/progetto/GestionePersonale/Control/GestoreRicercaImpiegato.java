package com.example.progetto.GestionePersonale.Control;

import com.example.progetto.Entity.Impiegato;
import com.example.progetto.GestionePersonale.Interface.ModuloRicercaImpiegato;
import com.example.progetto.GestionePersonale.Interface.VistaImpiegato;
import com.example.progetto.Utils.DBMS;
import com.example.progetto.Utils.Utils;
import javafx.stage.Stage;

public class GestoreRicercaImpiegato {
    private Impiegato impiegato;
    public GestoreRicercaImpiegato(){
        Utils.cambiaInterfaccia("GestionePersonale/ModuloRicercaImpiegato.fxml",new Stage(), c->{
            return new ModuloRicercaImpiegato(this);
        });
    }
    public void ricercaImpiegato(String matricola,Stage s){
        if(matricola.isBlank()){
            Utils.creaPannelloErrore("Inserisci la matricola");
            return;
        }
        impiegato = DBMS.ricercaImpiegato(matricola);
        if(impiegato!=null){
            Utils.cambiaInterfaccia("GestionePersonale/VistaImpiegato.fxml",s,c->{
                return new VistaImpiegato(impiegato,this);
            });
        }else{
            Utils.creaPannelloErrore("Matricola non esistente");
            return;
        }
    }
    public void elimina(String matricola,String ruolo,Stage s){
        if(ruolo.equals("Amministratore"))
            Utils.creaPannelloErrore("Non puoi eliminare l'amministratore");
        else {
            DBMS.elimina(matricola);
            Utils.creaPannelloConferma("Impiegato eliminato correttamente", s);
        }
    }
    public void modifica(String nome,String cognome,String ruolo,String email,String password){
        if(nome.isBlank() || cognome.isBlank() || ruolo.isBlank() || email.isBlank() || password.isBlank()){
            Utils.creaPannelloErrore("Completa tutti i campi");
            return;
        }
        String[] ruoli = {"alto","medio","basso"};
        for(String s : ruoli)
            if(s.equals(ruolo.toLowerCase()))
                if(!nome.equals(impiegato.getNome()) || !cognome.equals(impiegato.getCognome()))

    }
}

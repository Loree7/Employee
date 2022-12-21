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
            Utils.creaPannelloConferma("Impiegato eliminato correttamente");
            s.close(); //chiude vista impiegato
        }
    }
    public void modifica(String nome,String cognome,String ruolo,String email,String password,Stage s){
        if(nome.isBlank() || cognome.isBlank() || ruolo.isBlank() || email.isBlank() || password.isBlank()){
            Utils.creaPannelloErrore("Completa tutti i campi");
            return;
        }
        String[] ruoli = {"alto","medio","basso","amministratore"};
        for(String str : ruoli) {
            if (str.equals(ruolo.toLowerCase())) {
                if (!nome.equals(impiegato.getNome()) || !cognome.equals(impiegato.getCognome())
                        || !password.equals(impiegato.getPassword()) || !email.equals(impiegato.getEmail())){ // se ha cambiato uno dei campi
                    if(!email.equals(impiegato.getEmail())){ //se cambia email
                        if(DBMS.controllaEmail(email)) { // controllo se già è presente
                            Utils.creaPannelloErrore("Email già presente");
                            return;
                        }
                    }
                    DBMS.modifica(impiegato.getMatricola(), nome, cognome, ruolo, email, password);
                    Utils.creaPannelloConferma("Impiegato modificato correttamente");
                    s.close(); //chiude vista impiegato
                    return;
                }
                Utils.creaPannelloErrore("Non hai modificato nulla");
                return;
            }
        }
        Utils.creaPannelloErrore("Ruolo non esistente");
    }
}

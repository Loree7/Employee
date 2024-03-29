package com.employee.progetto.GestionePersonale.Control;

import com.employee.progetto.Entity.Impiegato;
import com.employee.progetto.GestionePersonale.Boundary.ModuloRicercaImpiegato;
import com.employee.progetto.GestionePersonale.Boundary.VistaImpiegato;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.MailUtils;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

public class GestoreRicercaImpiegato {
    private Impiegato impiegato;
    public GestoreRicercaImpiegato(){
        Utils.cambiaInterfaccia("GestionePersonale/ModuloRicercaImpiegato.fxml","Modulo Ricerca Impiegato",
                new Stage(), c->{
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
            Utils.cambiaInterfaccia("GestionePersonale/VistaImpiegato.fxml","Vista Impiegato",s,c->{
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
        if(nome.isBlank() || cognome.isBlank() || ruolo.isBlank() || email.isBlank()){
            Utils.creaPannelloErrore("Completa tutti i campi");
            return;
        }
        String[] ruoli = {"alto","intermedio","medio","basso","amministratore"};
        for(String str : ruoli) {
            if (str.equals(ruolo.toLowerCase())) {
                if (!nome.equals(impiegato.getNome()) || !cognome.equals(impiegato.getCognome()) || !ruolo.equals(impiegato.getRuolo())
                        || !password.equals(impiegato.getPassword())  || !email.equals(impiegato.getEmail())){ // se ha cambiato uno dei campi
                    if(!email.equals(impiegato.getEmail())){ //se cambia email
                        if(!MailUtils.verificaMail(email)){
                            Utils.creaPannelloErrore("Email non esistente");
                            return;
                        }
                        if(DBMS.controllaEmail(email)) { // controllo se già è presente
                            Utils.creaPannelloErrore("Email già presente");
                            return;
                        }
                    }
                    Utils.creaPannelloConferma("Impiegato modificato correttamente");
                    DBMS.modifica(impiegato.getMatricola(), nome, cognome, ruolo, email, password);
                    if(password.equals("")) {
                        MailUtils.inviaMail("Ecco a te i tuoi dati:\nNome: " + nome + ", Cognome: " + cognome + ", Ruolo: " + ruolo
                                        + ", Email: " + email + ", Password: Non modificata" + ", Matricola: " + DBMS.getMatricola(email)
                                , "Invio Credenziali", email);
                    }else MailUtils.inviaMail("Ecco a te i tuoi dati:\nNome: " + nome + ", Cognome: " + cognome + ", Ruolo: " + ruolo
                                    + ", Email: " + email + ", Password: " + password + ", Matricola: " + DBMS.getMatricola(email)
                            , "Invio Credenziali", email);
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

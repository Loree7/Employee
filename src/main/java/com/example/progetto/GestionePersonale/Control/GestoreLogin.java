package com.example.progetto.GestionePersonale.Control;

import com.example.progetto.Entity.Utente;
import com.example.progetto.GestionePersonale.Interface.ModuloLogin;
import com.example.progetto.PortaleAmministratore;
import com.example.progetto.PortaleImpiegato;
import com.example.progetto.Utils.DBMS;
import com.example.progetto.Utils.Utils;
import javafx.stage.Stage;

public class GestoreLogin {
    private static Utente utente;
    public GestoreLogin(Stage stage){
        Utils.cambiaInterfaccia("GestionePersonale/ModuloLogin.fxml",stage, c->{
            return new ModuloLogin(this);
        });
    }
    public void verificaCredenziali(String matricola, String password, Stage s) {
        if (matricola.isBlank() || password.isBlank()) {
            Utils.creaPannelloErrore("Completa tutti i campi");
            return;
        }
        utente = DBMS.verificaCredenziali(matricola,password);
        if(utente == null) {
            Utils.creaPannelloErrore("Credenziali errate");
            return;
        }
        if(utente.getRuolo().equals("Amministratore"))
            Utils.cambiaInterfaccia("PortaleAmministratore.fxml",s,c->{
            return new PortaleAmministratore(/*utente.getNome(),utente.getCognome()*/);});
        else Utils.cambiaInterfaccia("PortaleImpiegato.fxml",s,c->{
            return new PortaleImpiegato();});
    }

    public static Utente getUtente(){
        return utente;
    }
}

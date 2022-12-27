package com.employee.progetto.GestionePersonale.Control;

import com.employee.progetto.Entity.Utente;
import com.employee.progetto.GestionePersonale.Boundary.ModuloLogin;
import com.employee.progetto.PortaleAmministratore;
import com.employee.progetto.PortaleImpiegato;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

public class GestoreLogin {
    private static Utente utente;
    public GestoreLogin(Stage stage){
        Utils.cambiaInterfaccia("GestionePersonale/ModuloLogin.fxml","Modulo Login",stage, c->{
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
            Utils.cambiaInterfaccia("PortaleAmministratore.fxml","Portale Amministratore",s,c->{
            return new PortaleAmministratore();});
        else{
            Utils.cambiaInterfaccia("PortaleImpiegato.fxml","Portale Impiegato",s,c->{
                return new PortaleImpiegato();});
        }
    }

    public static Utente getUtente(){
        return utente;
    }
}

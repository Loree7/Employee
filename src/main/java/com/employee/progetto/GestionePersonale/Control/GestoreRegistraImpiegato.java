package com.employee.progetto.GestionePersonale.Control;

import com.employee.progetto.GestionePersonale.Boundary.ModuloRegistraImpiegato;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.MailUtils;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

import java.security.SecureRandom;

public class GestoreRegistraImpiegato{
    public GestoreRegistraImpiegato() {
        Utils.cambiaInterfaccia("GestionePersonale/ModuloRegistraImpiegato.fxml","Modulo Registra Impiegato",new Stage(), c -> {
            return new ModuloRegistraImpiegato(this);
        });
    }
    public void registraImpiegato(String nome,String cognome,String ruolo,String email,Stage s){
        String[] ruoli = {"alto","medio","basso"};
        if(nome.isBlank() || cognome.isBlank() || ruolo.isBlank() || email.isBlank()){
            Utils.creaPannelloErrore("Completa tutti i campi");
            return;
        }
        if(DBMS.controllaEmail(email)){
            Utils.creaPannelloErrore("Email già esistente");
            return;
        }
        for(String str : ruoli) {
            if (ruolo.toLowerCase().equals(str)) {
                String password = generaPassword(12);
                DBMS.registraImpiegato(nome, cognome, ruolo, email,password);
                Utils.creaPannelloConferma("Impiegato registrato correttamente");
                s.close(); //chiude registra
                MailUtils.inviaMail("Ecco a te i tuoi dati:\nNome: "+nome+", Cognome: "+cognome+", Ruolo: "+ruolo
                        +", Email: "+email+", Password: "+password+ ", Matricola: "+DBMS.getMatricola(email)
                        ,"Invio Credenziali",email);
                return;
            }
        }
        Utils.creaPannelloErrore("Ruolo non esistente, scegli tra: Alto, Medio, Basso");
    }
    public String generaPassword(int len){
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(random.nextInt(chars.length())));
        return sb.toString();
    }
}

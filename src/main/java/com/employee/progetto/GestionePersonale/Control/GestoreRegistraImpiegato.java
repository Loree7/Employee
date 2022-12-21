package com.example.progetto.GestionePersonale.Control;

import com.example.progetto.GestionePersonale.Interface.ModuloRegistraImpiegato;
import com.example.progetto.Utils.DBMS;
import com.example.progetto.Utils.MailUtils;
import com.example.progetto.Utils.Utils;
import javafx.stage.Stage;

import java.security.SecureRandom;

public class GestoreRegistraImpiegato{
    public GestoreRegistraImpiegato() {
        Utils.cambiaInterfaccia("GestionePersonale/ModuloRegistraImpiegato.fxml",new Stage(), c -> {
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
                DBMS.registraImpiegato(nome, cognome, ruolo, email, generaPassword(12));
                Utils.creaPannelloConferma("Impiegato registrato correttamente");
                s.close(); //chiude registra
                MailUtils.inviaMail("prova","Prova 1",email);
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

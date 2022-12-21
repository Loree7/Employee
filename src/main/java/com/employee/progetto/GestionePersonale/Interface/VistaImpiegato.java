package com.example.progetto.GestionePersonale.Interface;

import com.example.progetto.Entity.Impiegato;
import com.example.progetto.GestionePersonale.Control.GestoreRicercaImpiegato;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class VistaImpiegato {
    GestoreRicercaImpiegato gestoreRicercaImpiegato;
    private Impiegato impiegato;
    public VistaImpiegato(Impiegato impiegato,GestoreRicercaImpiegato gestoreRicercaImpiegato){
        this.gestoreRicercaImpiegato = gestoreRicercaImpiegato;
        this.impiegato = impiegato;
        //email.setText(impiegato.getE);
    }
    @FXML
    private Label messaggio;
    @FXML
    private TextField nome;
    @FXML
    private TextField cognome;
    @FXML
    private TextField ruolo;
    @FXML
    private TextField email;
    @FXML
    private TextField password;


    @FXML
    protected void initialize() {
        messaggio.setText(impiegato.getMatricola());
        nome.setText(impiegato.getNome());
        cognome.setText(impiegato.getCognome());
        ruolo.setText(impiegato.getRuolo());
        email.setText(impiegato.getEmail());
        password.setText(impiegato.getPassword());
    }
    @FXML
    public void cliccaElimina(){
        gestoreRicercaImpiegato.elimina(impiegato.getMatricola(),impiegato.getRuolo(),(Stage) nome.getScene().getWindow());
    }
    @FXML
    public void cliccaModifica(){

    }
}

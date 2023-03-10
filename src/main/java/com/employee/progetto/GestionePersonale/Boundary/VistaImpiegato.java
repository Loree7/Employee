package com.employee.progetto.GestionePersonale.Boundary;

import com.employee.progetto.Entity.Impiegato;
import com.employee.progetto.GestionePersonale.Control.GestoreRicercaImpiegato;
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
    public void initialize() {
        messaggio.setText(impiegato.getMatricola());
        nome.setText(impiegato.getNome());
        cognome.setText(impiegato.getCognome());
        ruolo.setText(impiegato.getRuolo());
        email.setText(impiegato.getEmail());

        nome.setOnAction(event -> {
            cognome.requestFocus();
        });
        cognome.setOnAction(event -> {
            ruolo.requestFocus();
        });
        ruolo.setOnAction(event -> {
            email.requestFocus();
        });
        email.setOnAction(event -> {
            password.requestFocus();
        });
        password.setOnAction(event -> {
            cliccaModifica();
        });
    }
    @FXML
    public void cliccaElimina(){
        gestoreRicercaImpiegato.elimina(impiegato.getMatricola(),impiegato.getRuolo(),(Stage) nome.getScene().getWindow());
    }
    @FXML
    public void cliccaModifica(){
        gestoreRicercaImpiegato.modifica(nome.getText(),cognome.getText(),ruolo.getText(),email.getText(),password.getText(),(Stage) nome.getScene().getWindow());
    }
}

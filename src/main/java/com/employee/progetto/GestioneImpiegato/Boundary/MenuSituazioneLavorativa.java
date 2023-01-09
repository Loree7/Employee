package com.employee.progetto.GestioneImpiegato.Boundary;

import com.employee.progetto.Entity.Impiegato;
import com.employee.progetto.Entity.Servizio;
import com.employee.progetto.GestioneAstensioni.Control.GestoreComunicaAssenza;
import com.employee.progetto.GestioneImpiegato.Control.GestoreSituazioneLavorativa;
import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
import com.employee.progetto.GestoreSistema;
import com.employee.progetto.Utils.DBMS;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class MenuSituazioneLavorativa {
    private GestoreSituazioneLavorativa gestoreSituazioneLavorativa;
    public MenuSituazioneLavorativa(GestoreSituazioneLavorativa gestoreSituazioneLavorativa) {
        this.gestoreSituazioneLavorativa = gestoreSituazioneLavorativa;
    }

    @FXML
    private Label oreServizio1;
    @FXML
    private Label oreServizio2;
    @FXML
    private Label oreServizio3;
    @FXML
    private Label oreServizio4;
    @FXML
    private Label ruolo;
    @FXML
    private Label stipendio;
    @FXML
    private Label oreDiPermesso;
    @FXML
    private Label giorniDiFerie;
    @FXML
    public void initialize (){
        List<Integer> ore = DBMS.getOre(GestoreLogin.getUtente().getMatricola());

        ruolo.setText(GestoreLogin.getUtente().getRuolo());
        oreServizio1.setText(ore.get(0).toString());
        oreServizio2.setText(ore.get(1).toString());
        oreServizio3.setText(ore.get(2).toString());
        oreServizio4.setText(ore.get(3).toString());
        stipendio.setText(Integer.toString(DBMS.getStipendio(GestoreLogin.getUtente().getMatricola())));
        oreDiPermesso.setText((Integer.toString((((Impiegato) GestoreLogin.getUtente()).getOrePermesso()))));
        giorniDiFerie.setText((Integer.toString(((Impiegato) GestoreLogin.getUtente()).getFerie())));
    }
}

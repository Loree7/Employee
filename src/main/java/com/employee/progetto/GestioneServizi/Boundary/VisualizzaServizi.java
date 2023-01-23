package com.employee.progetto.GestioneServizi.Boundary;

import com.employee.progetto.Entity.Servizio;
import com.employee.progetto.Entity.Turno;
import com.employee.progetto.GestioneServizi.Control.GestoreVisualizzaServizi;
import com.employee.progetto.Utils.DBMS;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalTime;

public class VisualizzaServizi {
    private GestoreVisualizzaServizi gestoreVisualizzaServizi;
    public VisualizzaServizi(GestoreVisualizzaServizi gestoreVisualizzaServizi){
        this.gestoreVisualizzaServizi = gestoreVisualizzaServizi;
    }

    @FXML
    private TableView<Servizio> tabellaServizi;
    @FXML
    private TableColumn<Servizio, String> servizioColumn;
    @FXML
    private TableColumn<Servizio, String> statoColumn;
    @FXML
    private TableColumn<Servizio,Integer> numDipendentiColumn;
    @FXML
    public void initialize() {
        ObservableList<Servizio> servizi = DBMS.prendiServizi();
        for(Servizio s : servizi)
            s.setNumDipendenti(DBMS.getNumDipendentiInTurno(s.getNome()));
        servizioColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        statoColumn.setCellValueFactory(new PropertyValueFactory<>("stato"));
        numDipendentiColumn.setCellValueFactory(new PropertyValueFactory<>("numDipendenti"));
        tabellaServizi.setItems(servizi);
    }
}

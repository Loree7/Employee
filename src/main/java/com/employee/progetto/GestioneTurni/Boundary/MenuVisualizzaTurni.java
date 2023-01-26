package com.employee.progetto.GestioneTurni.Boundary;

import com.employee.progetto.Entity.Turno;
import com.employee.progetto.GestioneTurni.Control.GestoreVisualizzaTurni;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalTime;

public class MenuVisualizzaTurni {
    private LocalDate data;
    public MenuVisualizzaTurni(ObservableList<Turno> turni,LocalDate data) {
        this.turni = turni;
        this.data = data;
    }
    @FXML
    private Label messaggio;
    @FXML
    private TableView<Turno> tabellaTurni;
    @FXML
    private TableColumn<Turno, LocalTime> oraInizioColumn;
    @FXML
    private TableColumn<Turno, LocalTime> oraFineColumn;
    @FXML
    private TableColumn<Turno, LocalDate> dataColumn;
    @FXML
    private TableColumn<Turno, Integer> numServizioColumn;
    @FXML
    private TableColumn<Turno, String> matricolaColumn;

    private ObservableList<Turno> turni;

    @FXML
    public void initialize() {
        oraInizioColumn.setCellValueFactory(new PropertyValueFactory<>("oraInizio"));
        oraFineColumn.setCellValueFactory(new PropertyValueFactory<>("oraFine"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        numServizioColumn.setCellValueFactory(new PropertyValueFactory<>("idServizio"));
        matricolaColumn.setCellValueFactory(new PropertyValueFactory<>("matricola"));
        tabellaTurni.setItems(turni);
        messaggio.setText("Turni del: " + data.toString());
    }
}

package com.employee.progetto.GestionePersonale.Boundary;

import com.employee.progetto.GestionePersonale.Control.GestoreComunicaStraordinari;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.time.LocalTime;

public class ModuloComunicaStraordinari {
    private GestoreComunicaStraordinari gestoreComunicaStraordinari;
    public ModuloComunicaStraordinari(GestoreComunicaStraordinari gestoreComunicaStraordinari){
        this.gestoreComunicaStraordinari = gestoreComunicaStraordinari;
    }
    @FXML
    public void initialize() {
        group = new ToggleGroup();
        fascia6_14.setSelected(true);
        fascia6_14.setToggleGroup(group);
        fascia14_22.setToggleGroup(group);
        servizio.setOnAction(event -> {
            giorno.requestFocus();
        });
    }
    private ToggleGroup group;
    @FXML
    private TextField servizio;
    @FXML
    private DatePicker giorno;
    @FXML
    private RadioButton fascia6_14;
    @FXML
    private RadioButton fascia14_22;
    @FXML
    public void cliccaComunica(){
        servizio.requestFocus();
        if(group.getSelectedToggle()==fascia6_14)
            gestoreComunicaStraordinari.comunicaStraordinari(servizio.getText(),giorno.getValue()
                    ,LocalTime.parse("06:00:00"),LocalTime.parse("14:00:00"),(Stage) servizio.getScene().getWindow());
        else
            gestoreComunicaStraordinari.comunicaStraordinari(servizio.getText(),giorno.getValue()
                    , LocalTime.parse("14:00:00"),LocalTime.parse("22:00:00"),(Stage) servizio.getScene().getWindow());
    }
}

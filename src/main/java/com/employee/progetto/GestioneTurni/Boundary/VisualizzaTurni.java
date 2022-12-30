package com.employee.progetto.GestioneTurni.Boundary;

import com.employee.progetto.GestioneTurni.Control.GestoreVisualizzaTurni;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

import java.io.IOException;

public class VisualizzaTurni {

    private GestoreVisualizzaTurni gestoreVisualizzaTurni;

    public VisualizzaTurni(GestoreVisualizzaTurni gestoreVisualizzaTurni) {
        this.gestoreVisualizzaTurni = gestoreVisualizzaTurni;
    }
    @FXML
    private DatePicker dPicker;

    @FXML
    private Button visualizTurno;


    public void visualizzaTurni() throws IOException {
        gestoreVisualizzaTurni.mostraTurni(dPicker.getValue());
    }


}

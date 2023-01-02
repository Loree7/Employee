package com.employee.progetto.GestioneTurni.Boundary;

import com.employee.progetto.GestioneTurni.Control.GestoreVisualizzaTurni;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;


public class ModuloVisualizzaTurni {
    private GestoreVisualizzaTurni gestoreVisualizzaTurni;
    public ModuloVisualizzaTurni(GestoreVisualizzaTurni gestoreVisualizzaTurni) {
        this.gestoreVisualizzaTurni = gestoreVisualizzaTurni;
    }
    @FXML
    private DatePicker data;
    public void cliccaVisualizzaTurni() {
        data.requestFocus();
        gestoreVisualizzaTurni.mostraTurni(data.getValue());
    }
}

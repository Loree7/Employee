package com.employee.progetto.GestioneAstensioni.Boundary;

import com.employee.progetto.GestioneAstensioni.Control.GestoreRichiestaSciopero;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class ModuloRichiestaSciopero {
    private GestoreRichiestaSciopero gestoreRichiestaSciopero;
    public ModuloRichiestaSciopero(GestoreRichiestaSciopero gestoreRichiestaSciopero){
        this.gestoreRichiestaSciopero = gestoreRichiestaSciopero;
    }
    @FXML
    public void initialize() {
        data.setShowWeekNumbers(true);
    }
    @FXML
    private DatePicker data;
    @FXML
    public void cliccaRichiedi(){
        data.requestFocus();
        gestoreRichiestaSciopero.richiediSciopero(data.getValue(),(Stage) data.getScene().getWindow());
    }
}

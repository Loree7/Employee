package com.employee.progetto.GestioneAstensioni.Boundary;

import com.employee.progetto.GestioneAstensioni.Control.GestoreComunicaAssenza;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class ModuloComunicaAssenza {
    GestoreComunicaAssenza gestoreComunicaAssenza;
    public ModuloComunicaAssenza(GestoreComunicaAssenza gestoreComunicaAssenza){
        this.gestoreComunicaAssenza = gestoreComunicaAssenza;
    }
    @FXML
    public void initialize() {
        data.setShowWeekNumbers(true);
    }
    @FXML
    private DatePicker data;
    @FXML
    public void cliccaComunica(){
        data.requestFocus();
        gestoreComunicaAssenza.comunicaAssenza(data.getValue(),(Stage) data.getScene().getWindow());
    }
}

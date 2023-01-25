package com.employee.progetto.GestioneAstensioni.Boundary;

import com.employee.progetto.GestioneAstensioni.Control.GestoreComunicaMalattia;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class ModuloComunicaMalattia {
    GestoreComunicaMalattia gestoreComunicaMalattia;
    public ModuloComunicaMalattia(GestoreComunicaMalattia gestoreComunicaMalattia){
        this.gestoreComunicaMalattia = gestoreComunicaMalattia;
    }
    @FXML
    public void initialize() {
        data_inizio.setShowWeekNumbers(true);
        data_fine.setShowWeekNumbers(true);
    }
    @FXML
    private DatePicker data_inizio,data_fine;
    @FXML
    public void cliccaComunica(){
        data_inizio.requestFocus();
        gestoreComunicaMalattia.comunicaMalattia(data_inizio.getValue(),data_fine.getValue(),(Stage) data_fine.getScene().getWindow());
    }
}

package com.employee.progetto.GestioneAstensioni.Boundary;

import com.employee.progetto.GestioneAstensioni.Control.GestoreComunicaCongedo;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class ModuloComunicaCongedo {
    GestoreComunicaCongedo gestoreComunicaCongedo;
    public ModuloComunicaCongedo(GestoreComunicaCongedo gestoreComunicaCongedo){
        this.gestoreComunicaCongedo = gestoreComunicaCongedo;
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
        gestoreComunicaCongedo.comunicaCongedo(data_inizio.getValue(),data_fine.getValue(),(Stage) data_fine.getScene().getWindow());
    }
}

package com.employee.progetto.GestioneAstensioni.Boundary;

import com.employee.progetto.GestioneAstensioni.Control.GestoreRichiestaFerie;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class ModuloRichiestaFerie {
    private GestoreRichiestaFerie gestoreRichiestaFerie;
    public ModuloRichiestaFerie(GestoreRichiestaFerie gestoreRichiestaFerie){
        this.gestoreRichiestaFerie = gestoreRichiestaFerie;
    }
    @FXML
    public void initialize() {
        data_inizio.setShowWeekNumbers(true);
        data_fine.setShowWeekNumbers(true);
    }
    @FXML
    private DatePicker data_inizio,data_fine;
    @FXML
    public void cliccaRichiedi(){
        data_inizio.requestFocus();
        gestoreRichiestaFerie.richiediFerie(data_inizio.getValue(),data_fine.getValue(),(Stage) data_fine.getScene().getWindow());
    }
}

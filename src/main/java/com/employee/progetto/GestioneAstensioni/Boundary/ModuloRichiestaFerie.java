package com.employee.progetto.GestioneAstensioni.Boundary;

import com.employee.progetto.GestioneAstensioni.Control.GestoreRichiestaFerie;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Date;

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
    private DatePicker data_inizio;
    @FXML
    private DatePicker data_fine;
    @FXML
    public void richiedi(){
        gestoreRichiestaFerie.richiedi(data_inizio.getValue(),data_fine.getValue(),(Stage) data_fine.getScene().getWindow());
    }
}

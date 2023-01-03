package com.employee.progetto.GestioneImpiegato.Boundary;

import com.employee.progetto.GestioneImpiegato.Control.GestoreRichiestaPermesso;
import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.time.LocalTime;

public class ModuloRichiestaPermesso {
    private GestoreRichiestaPermesso gestoreRichiestaPermesso;
    private int oreTurno;
    public ModuloRichiestaPermesso(GestoreRichiestaPermesso gestoreRichiestaPermesso){
        this.gestoreRichiestaPermesso = gestoreRichiestaPermesso;;
    }
    @FXML
    private void initialize() {
        data.setOnAction(event -> {
            String fasciaOraria =  DBMS.getFasciaOraria(GestoreLogin.getUtente().getMatricola(),data.getValue());
            if(fasciaOraria==null) {
                Utils.creaPannelloErrore("Turno non esistente per la data inserita");
                return;
            }
            String[] ore = fasciaOraria.split("-");
            oreTurno = Integer.parseInt(ore[1]) - Integer.parseInt(ore[0]);
            ora_inizio.setValue(LocalTime.of(Integer.parseInt(ore[0]),0,0));
            ora_fine.setValue(LocalTime.of(Integer.parseInt(ore[1]),0,0));
            for(int i=0;i<8;i++) {
                ora_inizio.getItems().add(LocalTime.of(Integer.parseInt(ore[0]) + i, 0, 0));
                ora_fine.getItems().add(LocalTime.of(Integer.parseInt(ore[1]) - i, 0, 0));
            }
        });
    }
    @FXML
    private DatePicker data;
    @FXML
    private ChoiceBox<LocalTime> ora_inizio;
    @FXML
    private ChoiceBox<LocalTime> ora_fine;
    @FXML
    public void cliccaRichiedi(){
        gestoreRichiestaPermesso.richiediPermesso(data.getValue(),ora_inizio.getValue(),ora_fine.getValue(),oreTurno
                ,(Stage) data.getScene().getWindow());
    }
}

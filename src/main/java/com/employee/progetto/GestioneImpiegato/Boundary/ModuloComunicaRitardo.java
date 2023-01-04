package com.employee.progetto.GestioneImpiegato.Boundary;

import com.employee.progetto.GestioneImpiegato.Control.GestoreComunicaRitardo;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModuloComunicaRitardo {
    private GestoreComunicaRitardo gestoreComunicaRitardo;
    public ModuloComunicaRitardo(GestoreComunicaRitardo gestoreComunicaRitardo){
        this.gestoreComunicaRitardo = gestoreComunicaRitardo;;
    }
    @FXML
    private TextField motivazione;
    @FXML
    public void cliccaComunica(){
        gestoreComunicaRitardo.comunica(motivazione.getText(),(Stage) motivazione.getScene().getWindow());
    }
}

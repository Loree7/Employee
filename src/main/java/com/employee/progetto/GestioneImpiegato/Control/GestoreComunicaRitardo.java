package com.employee.progetto.GestioneImpiegato.Control;

import com.employee.progetto.GestioneImpiegato.Boundary.ModuloComunicaRitardo;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

public class GestoreComunicaRitardo {
    public GestoreComunicaRitardo(){
        Utils.cambiaInterfaccia("GestioneImpiegato/ModuloComunicaRitardo.fxml",
                "Modulo Comunica Ritardo",
                new Stage(), c->{
                    return new ModuloComunicaRitardo(this);
                });
    }
    public void comunica(String motivazione){

    }
}

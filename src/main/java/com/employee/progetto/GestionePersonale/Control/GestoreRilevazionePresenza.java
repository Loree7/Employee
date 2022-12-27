package com.employee.progetto.GestionePersonale.Control;

import com.employee.progetto.GestionePersonale.Boundary.ModuloRilevazionePresenza;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

public class GestoreRilevazionePresenza {
    public GestoreRilevazionePresenza() {
        Utils.cambiaInterfaccia("GestionePersonale/ModuloRilevazionePresenza.fxml",
                "Modulo Rilevazione Presenza",new Stage(), c -> {
            return new ModuloRilevazionePresenza(this);
        });
    }
}

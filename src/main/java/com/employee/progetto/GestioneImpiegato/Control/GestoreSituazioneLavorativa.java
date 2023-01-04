package com.employee.progetto.GestioneImpiegato.Control;

import com.employee.progetto.GestioneImpiegato.Boundary.MenuSituazioneLavorativa;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

public class GestoreSituazioneLavorativa {
    public GestoreSituazioneLavorativa() {
        Utils.cambiaInterfaccia("GestioneImpiegato/MenuSituazioneLavorativa.fxml",
                "Situazione Lavorativa",
                new Stage(), c->{
                    return new MenuSituazioneLavorativa(this);
                });
    }
}

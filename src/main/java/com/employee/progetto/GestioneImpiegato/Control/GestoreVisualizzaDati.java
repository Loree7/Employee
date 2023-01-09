package com.employee.progetto.GestioneImpiegato.Control;

import com.employee.progetto.GestioneImpiegato.Boundary.MenuVisualizzaDati;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

public class GestoreVisualizzaDati {

    public GestoreVisualizzaDati(){
        Utils.cambiaInterfaccia("GestioneImpiegato/MenuVisualizzaDati.fxml",
                "Menu Visualizza Dati",
                new Stage(), c->{
                    return new MenuVisualizzaDati(this);
                });
    }

}

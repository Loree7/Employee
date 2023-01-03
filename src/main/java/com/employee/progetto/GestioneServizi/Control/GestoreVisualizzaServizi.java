package com.employee.progetto.GestioneServizi.Control;


import com.employee.progetto.GestioneServizi.Boundary.VisualizzaServizi;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

public class GestoreVisualizzaServizi {
    public GestoreVisualizzaServizi(){
        Utils.cambiaInterfaccia("GestioneServizi/VisualizzaServizi.fxml",
                "Visualizza Servizi",
                new Stage(), c->{
                    return new VisualizzaServizi(this);
                });
    }
}

package com.employee.progetto.GestioneTurni.Control;

import com.employee.progetto.GestioneTurni.Boundary.MenuVisualizzaTurni;
import com.employee.progetto.GestioneTurni.Boundary.VisualizzaTurni;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GestoreVisualizzaTurni{

    public GestoreVisualizzaTurni() {
        Utils.cambiaInterfaccia("GestioneTurni/ModuloVisualizzaTurni.fxml",
                "Modulo Visualizza Turni",
                new Stage(), c->{
                    return new VisualizzaTurni(this);
                });
    }
    public void mostraTurni(LocalDate data) {
        Utils.cambiaInterfaccia("GestioneTurni/MenuVisualizzaTurni.fxml", "Menu Visualizza Turni", new Stage(), c->{
            return new MenuVisualizzaTurni(this, DBMS.mostraTurni(data));
        });
    }
}

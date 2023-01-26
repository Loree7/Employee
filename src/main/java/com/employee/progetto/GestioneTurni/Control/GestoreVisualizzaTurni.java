package com.employee.progetto.GestioneTurni.Control;

import com.employee.progetto.Entity.Turno;
import com.employee.progetto.GestioneTurni.Boundary.MenuVisualizzaTurni;
import com.employee.progetto.GestioneTurni.Boundary.ModuloVisualizzaTurni;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.Utils;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GestoreVisualizzaTurni{
    public GestoreVisualizzaTurni() {
        Utils.cambiaInterfaccia("GestioneTurni/ModuloVisualizzaTurni.fxml",
                "Modulo Visualizza Turni",
                new Stage(), c->{
                    return new ModuloVisualizzaTurni(this);
                });
    }
    public void mostraTurni(LocalDate data) {
        if(data==null){
            Utils.creaPannelloErrore("Inserisci una data");
            return;
        }
        ObservableList<Turno> turni = DBMS.getTurni(data);
        if(turni.isEmpty()) {
            Utils.creaPannelloErrore("Non ci sono turni per la data inserita");
            return;
        }
        Utils.cambiaInterfaccia("GestioneTurni/MenuVisualizzaTurni.fxml", "Menù Visualizza Turni", new Stage(), c->{
            return new MenuVisualizzaTurni( turni,data);
        });
    }
}

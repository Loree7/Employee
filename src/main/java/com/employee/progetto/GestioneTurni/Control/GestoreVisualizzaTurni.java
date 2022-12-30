package com.employee.progetto.GestioneTurni.Control;

import com.employee.progetto.GestioneTurni.Boundary.MenuVisualizzaTurno;
import com.employee.progetto.GestioneTurni.Boundary.VisualizzaTurni;
import com.employee.progetto.Utils.DBMS;
import com.employee.progetto.Utils.Utils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
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
        Utils.cambiaInterfaccia("GestioneTurni/MenuVisualizzaTurno.fxml", "Menu Visualizza Turno", new Stage(), c->{
            TableView tableView = new TableView();
            tableView.setItems(DBMS.mostraTurni(data));
            return new MenuVisualizzaTurno(this, tableView);
        });
    }
}

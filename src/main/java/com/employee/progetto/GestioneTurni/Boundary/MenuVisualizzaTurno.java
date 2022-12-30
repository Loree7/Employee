package com.employee.progetto.GestioneTurni.Boundary;

import com.employee.progetto.GestioneTurni.Control.GestoreVisualizzaTurni;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class MenuVisualizzaTurno {

    private GestoreVisualizzaTurni gestoreVisualizzaTurni;

    public MenuVisualizzaTurno(GestoreVisualizzaTurni gestoreVisualizzaTurni, TableView tabellaTurni) {
        this.gestoreVisualizzaTurni = gestoreVisualizzaTurni;
        this.tabellaTurni = tabellaTurni;
    }

    @FXML
    private TableView tabellaTurni;

    public TableView getTabellaTurni() {return tabellaTurni; }


}

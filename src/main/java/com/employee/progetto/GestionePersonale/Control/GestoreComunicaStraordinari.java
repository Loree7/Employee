package com.employee.progetto.GestionePersonale.Control;

import com.employee.progetto.GestionePersonale.Boundary.ModuloComunicaStraordinari;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

public class GestoreComunicaStraordinari {
    public GestoreComunicaStraordinari(){
        Utils.cambiaInterfaccia("GestionePersonale/ModuloComunicaStraordinari.fxml",
                "Modulo Comunica Straordinari",
                new Stage(), c->{
                    return new ModuloComunicaStraordinari(this);
                });
    }
}

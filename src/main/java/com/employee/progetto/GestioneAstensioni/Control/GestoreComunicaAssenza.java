package com.employee.progetto.GestioneAstensioni.Control;

import com.employee.progetto.GestioneAstensioni.Boundary.ModuloComunicaAssenza;
import com.employee.progetto.Utils.Utils;
import javafx.stage.Stage;

import java.time.LocalDate;

public class GestoreComunicaAssenza {
    public GestoreComunicaAssenza(){
        Utils.cambiaInterfaccia("GestioneAstensioni/ModuloComunicaAssenza.fxml",
                "Modulo Comunica Assenza",
                new Stage(), c->{
                    return new ModuloComunicaAssenza(this);
                });
    }
    public void comunicaAssenza(LocalDate data,Stage s){

    }
}

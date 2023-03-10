module com.employee.progetto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;
    requires mailboxvalidator;

    exports com.employee.progetto;
    opens com.employee.progetto to javafx.fxml;
    //apri fxml
    opens com.employee.progetto.GestionePersonale to javafx.fxml;
    opens com.employee.progetto.GestioneAstensioni to javafx.fxml;
    opens com.employee.progetto.GestioneTurni to javafx.fxml;
    opens com.employee.progetto.GestioneImpiegato to javafx.fxml;
    opens com.employee.progetto.Pannelli to javafx.fxml;
    opens com.employee.progetto.GestioneServizi to javafx.fxml;
    //exporta e apri package
    exports com.employee.progetto.Entity;
    opens com.employee.progetto.Entity to javafx.fxml;
    exports com.employee.progetto.Utils;
    opens com.employee.progetto.Utils to javafx.fxml;
    exports com.employee.progetto.GestionePersonale.Control;
    opens com.employee.progetto.GestionePersonale.Control to javafx.fxml;
    exports com.employee.progetto.GestionePersonale.Boundary;
    opens com.employee.progetto.GestionePersonale.Boundary to javafx.fxml;
    exports com.employee.progetto.GestioneAstensioni.Control;
    opens com.employee.progetto.GestioneAstensioni.Control to javafx.fxml;
    exports com.employee.progetto.GestioneAstensioni.Boundary;
    opens com.employee.progetto.GestioneAstensioni.Boundary to javafx.fxml;
    exports com.employee.progetto.Common;
    opens com.employee.progetto.Common to javafx.fxml;
    exports com.employee.progetto.GestioneTurni.Control;
    opens com.employee.progetto.GestioneTurni.Control to javafx.fxml;
    exports com.employee.progetto.GestioneTurni.Boundary;
    opens com.employee.progetto.GestioneTurni.Boundary to javafx.fxml;
    exports com.employee.progetto.GestioneImpiegato.Control;
    opens com.employee.progetto.GestioneImpiegato.Control to javafx.fxml;
    exports com.employee.progetto.GestioneImpiegato.Boundary;
    opens com.employee.progetto.GestioneImpiegato.Boundary to javafx.fxml;
    exports com.employee.progetto.GestioneServizi.Control;
    opens com.employee.progetto.GestioneServizi.Control to javafx.fxml;
    exports com.employee.progetto.GestioneServizi.Boundary;
    opens com.employee.progetto.GestioneServizi.Boundary to javafx.fxml;
}
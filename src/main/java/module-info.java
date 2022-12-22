module com.employee.progetto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;

    exports com.employee.progetto;
    opens com.employee.progetto to javafx.fxml;
    //apri fxml
    opens com.employee.progetto.GestionePersonale to javafx.fxml;
    opens com.employee.progetto.Pannelli to javafx.fxml;
    //exporta e apri package
    exports com.employee.progetto.Utils;
    opens com.employee.progetto.Utils to javafx.fxml;
    exports com.employee.progetto.GestionePersonale.Control;
    opens com.employee.progetto.GestionePersonale.Control to javafx.fxml;
    exports com.employee.progetto.GestionePersonale.Boundary;
    opens com.employee.progetto.GestionePersonale.Boundary to javafx.fxml;
    exports com.employee.progetto.Common;
    opens com.employee.progetto.Common to javafx.fxml;
}
module com.example.progetto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.example.progetto;
    opens com.example.progetto to javafx.fxml;
    //apri fxml
    opens com.example.progetto.GestionePersonale to javafx.fxml;
    opens com.example.progetto.Pannelli to javafx.fxml;
    //exporta e apri package
    exports com.example.progetto.Utils;
    opens com.example.progetto.Utils to javafx.fxml;
    exports com.example.progetto.GestionePersonale.Control;
    opens com.example.progetto.GestionePersonale.Control to javafx.fxml;
    exports com.example.progetto.GestionePersonale.Interface;
    opens com.example.progetto.GestionePersonale.Interface to javafx.fxml;
    exports com.example.progetto.Common;
    opens com.example.progetto.Common to javafx.fxml;
}
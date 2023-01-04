package com.employee.progetto.GestioneImpiegato.Boundary;

import com.employee.progetto.GestioneImpiegato.Control.GestoreVisualizzaDati;
import com.employee.progetto.GestionePersonale.Control.GestoreLogin;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MenuVisualizzaDati {

    private GestoreVisualizzaDati gestoreVisualizzaDati;

    public MenuVisualizzaDati(GestoreVisualizzaDati gestoreVisualizzaDati) {
        this.gestoreVisualizzaDati = gestoreVisualizzaDati;
    }
    @FXML
    private Label matricolaLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label cognomeLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private CheckBox checkBoxPass;

    @FXML
    private ImageView imageOcchio;


    @FXML
    public void initialize() {
        matricolaLabel.setText(GestoreLogin.getUtente().getMatricola());
        passwordLabel.setText(convertiPassword());
        nomeLabel.setText(GestoreLogin.getUtente().getNome());
        cognomeLabel.setText(GestoreLogin.getUtente().getCognome());
        emailLabel.setText(GestoreLogin.getUtente().getEmail());
    }

    @FXML
    public void cliccaMostraPassword() {
        if (checkBoxPass.isSelected()) {
            imageOcchio.setImage(new Image(getClass().getResourceAsStream("/images/icons8-visibile-48.png")));
            passwordLabel.setText(GestoreLogin.getUtente().getPassword());
        } else {
            imageOcchio.setImage(new Image(getClass().getResourceAsStream("/images/icons8-occhio-chiuso-48.png")));
            passwordLabel.setText(convertiPassword());
        }
    }

    public String convertiPassword() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < GestoreLogin.getUtente().getPassword().length(); i++) {
            sb.append('•');
        }
        return sb.toString();
    }
}

package it.unicas.clinic.address.view;

import it.unicas.clinic.address.model.Client;
import it.unicas.clinic.address.model.dao.mysql.DAOMySQLSettings;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddClientController {
    public ClientOverviewController clientOverviewController;

    public void SetClientOverviewController(ClientOverviewController clientOverviewController) {
        this.clientOverviewController = clientOverviewController;
    }

    @FXML
    private TextField nameText;

    @FXML
    private TextField surnameText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField phoneNumberText;

    @FXML
    private void OnClickAddClient() throws SQLException {

        String name = nameText.getText();
        String surname = surnameText.getText();
        String email = emailText.getText();
        String phoneNumber = phoneNumberText.getText();

        DAOMySQLSettings.insert(name, surname, email, phoneNumber);
        ArrayList<Client> list = DAOMySQLSettings.getClientsList();

        clientOverviewController.updateTable(list);

    }
}

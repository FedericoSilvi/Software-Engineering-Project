package it.unicas.clinic.address.view;

import it.unicas.clinic.address.model.Client;
import it.unicas.clinic.address.model.dao.mysql.DAOMySQLSettings;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import java.sql.SQLException;
import java.util.ArrayList;

public class SearchClientController {

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
    private Button searchButton;

    @FXML
    private void OnClickSearchButton(ActionEvent event) throws SQLException {
        ArrayList<Client> list;
        String name = nameText.getText();
        String surname = surnameText.getText();
        String email = emailText.getText();

        list = DAOMySQLSettings.filterClient(name, surname, email);

        clientOverviewController.updateTable(list);

        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
        }
    }


}

package it.unicas.clinic.address.view;

import it.unicas.clinic.address.model.Client;
import it.unicas.clinic.address.model.dao.mysql.DAOMySQLSettings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientOverviewController {

    // Table
    @FXML
    private TableView<Client> table;

    @FXML
    private TableColumn<Client, String> name;

    @FXML
    private TableColumn<Client, String> surname;

    @FXML
    private TableColumn<Client, String> email;

    @FXML
    private TableColumn<Client, String> number;


    //Buttons
    @FXML
    private Button button;

    @FXML
    private Button searchButton;

    @FXML
    private void initialize() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
    }

    private ObservableList<Client> clientData = FXCollections.observableArrayList();

    @FXML
    private void OnClickShowAllClients(ActionEvent event) throws SQLException {
        ArrayList<Client> clients = DAOMySQLSettings.getClientsList();

        clientData.clear();
        clientData.addAll(clients);

        table.setItems(clientData);

    }

    @FXML
    private void OnClickSearchClient(ActionEvent event) throws IOException {
        Stage searchWindow = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicas/clinic/address/view/SearchClient.fxml"));
        AnchorPane layout = loader.load();

        SearchClientController controller = loader.getController();
        controller.SetClientOverviewController(this);



        searchWindow.setScene(new Scene(layout));
        searchWindow.show();
    }

    @FXML
    private void OnClickAddClient(ActionEvent event) throws IOException {
        Stage addClientWindow = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicas/clinic/address/view/AddClient.fxml"));
        AnchorPane layout = loader.load();

        AddClientController controller = loader.getController();
        controller.SetClientOverviewController(this);

        addClientWindow.setScene(new Scene(layout));
        addClientWindow.show();

    }

  //  @FXML non so se serve
    public void updateTable(ArrayList<Client> clients) {
        clientData.clear();
        clientData.addAll(clients);
        table.setItems(clientData);
    }
}

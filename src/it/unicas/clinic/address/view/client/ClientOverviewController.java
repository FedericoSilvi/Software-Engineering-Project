package it.unicas.clinic.address.view.client;

import it.unicas.clinic.address.Main;
import it.unicas.clinic.address.model.Appointment;
import it.unicas.clinic.address.model.Client;
import it.unicas.clinic.address.model.dao.AppointmentDAO;
import it.unicas.clinic.address.model.dao.mysql.AppointmentDAOMySQLImpl;
import it.unicas.clinic.address.model.dao.mysql.DAOClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @FXML
    private TextField clientName;
    @FXML
    private TextField clientSurname;

    //Buttons
    @FXML
    private Button updateButton;

    @FXML
    private Button searchButton;



    private AppointmentDAO appDao = AppointmentDAOMySQLImpl.getInstance();

    @FXML
    private void initialize() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
    }

    private ObservableList<Client> clientData = FXCollections.observableArrayList();

    private Main mainApp;
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void OnClickShowAllClients(ActionEvent event) throws SQLException {
        ArrayList<Client> clients = DAOClient.getClientsList();

        clientData.clear();
        clientData.addAll(clients);

        table.setItems(clientData);

    }

    @FXML
    private void OnClickSearchClient(ActionEvent event) throws IOException {
        mainApp.searchClientLayout(this);
    }

    @FXML
    private void OnClickAddClient(ActionEvent event) throws IOException {
        mainApp.addClientLayout(this);
    }

    @FXML
    private void OnClickAddClientV2(MouseEvent event) throws IOException {
        mainApp.addClientLayout(this);
    }

    @FXML
    private void OnClickUpdateClient(ActionEvent event) throws IOException {
        if(isAvailable()){
            Client client = table.getSelectionModel().getSelectedItem();

            mainApp.updateClientLayout(this, client);
        }

    }

    @FXML
    private void OnClickDeleteClient(ActionEvent event) throws SQLException {
        if(isAvailable()){
            //Genero un aller per chiedere conferma

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Client");
            alert.setContentText("Are you sure you want to delete this client?");
            alert.setHeaderText("Are you sure you want to delete this client?");

            ButtonType buttonYes = new ButtonType("Yes");
            ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonYes, buttonCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == buttonYes){

                Client client = table.getSelectionModel().getSelectedItem();

                //Inserire finestra con conferma sull'eliminazione
                DAOClient.delete(client.getId());
            }




        }

        ArrayList<Client> list= DAOClient.getClientsList();
        updateTable(list);

    }

    @FXML
    private void OnClickDeleteClientV2(MouseEvent event) throws SQLException {
        if(isAvailable()){
            //Genero un aller per chiedere conferma

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Client");
            alert.setContentText("Are you sure you want to delete this client?");
            alert.setHeaderText("Are you sure you want to delete this client?");

            ButtonType buttonYes = new ButtonType("Yes");
            ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonYes, buttonCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == buttonYes){

                Client client = table.getSelectionModel().getSelectedItem();

                //Inserire finestra con conferma sull'eliminazione
                DAOClient.delete(client.getId());
            }

        }

        ArrayList<Client> list= DAOClient.getClientsList();
        updateTable(list);
    }

    @FXML
    private void handleExit(){
        if(mainApp.getIsManager())
            mainApp.initStaffManager();
        else
            mainApp.initStaff();
    }

    @FXML
    private void onClickShowHistory() throws SQLException, IOException {
        if(isAvailable()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Client");
            alert.setContentText("You want to view this client's history?");
            alert.setHeaderText("Yes: view history"+"\n"+"No: go back");

            ButtonType buttonYes = new ButtonType("Yes");
            ButtonType buttonCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonYes, buttonCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == buttonYes){
                Client c = table.getSelectionModel().getSelectedItem();
                List<Appointment> list = appDao.getHistoryApp(c.getId());
                if(list==null){
                    mainApp.errorAlert("Attention",
                            "Selected client has no past appointments",
                            "");
                }
                else {
                    if (mainApp.getIsManager())
                        mainApp.getAppointmentData().addAll(list);
                    else
                        mainApp.getAppointmentData().addAll(list);
                    mainApp.showClientHistory();
                }

            }
        }
    }

    @FXML
    private void searchClient() throws SQLException {
        String name = clientName.getText();
        String surname = clientSurname.getText();
        ArrayList<Client> list = DAOClient.select(name, surname);
        System.out.println(list);
        updateTable(list);
    }

    public void updateTable(ArrayList<Client> clients) {
        if(clients!=null) {
            clientData.clear();
            clientData.addAll(clients);
            table.setItems(clientData);
        }
    }

    public void ShowAllClients() throws SQLException {
        ArrayList<Client> clients = DAOClient.getClientsList();

        clientData.clear();
        clientData.addAll(clients);

        table.setItems(clientData);
    }

    private boolean isAvailable() {
        String errorMessage = "";
        if(table.getSelectionModel().getSelectedItem() == null) {
            errorMessage = "Select a client first!";
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
        //    alert.initOwner();
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
        return true;
    }


}

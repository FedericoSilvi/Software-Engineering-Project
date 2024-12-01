package it.unicas.clinic.address.view.appointment;

import it.unicas.clinic.address.Main;
import it.unicas.clinic.address.model.Appointment;
import it.unicas.clinic.address.model.dao.AppointmentDAO;
import it.unicas.clinic.address.model.dao.StaffException;
import it.unicas.clinic.address.model.dao.mysql.AppointmentDAOMySQLImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class AppointmentViewController {
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, String> serviceColumn;
    @FXML
    private TableColumn<Appointment, LocalDate> dateColumn;
    @FXML
    private TableColumn<Appointment, LocalTime> timeColumn;
    @FXML
    private TableColumn<Appointment, LocalTime> durationColumn;
    @FXML
    private TableColumn<Appointment, Integer> idColumn;
    @FXML
    private TableColumn<Appointment, Integer> staffIdColumn;
    @FXML
    private TableColumn<Appointment, Integer> clientIdColumn;

    // Reference to the main application.
    private Main mainApp;
    private AppointmentDAO dao= AppointmentDAOMySQLImpl.getInstance();

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        // Add observable list data to the table
        appointmentTable.setItems(mainApp.getAppointmentData());
        mainApp.getAppointmentData().addAll(dao.select(new Appointment(0,null, null, null,null,0,0)));

    }
    @FXML
    public void initialize() {
        // Column binding with Appointment's properties
        serviceColumn.setCellValueFactory(cellData -> cellData.getValue().serviceProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        durationColumn.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject()); // IntegerProperty richiede asObject()
        staffIdColumn.setCellValueFactory(cellData -> cellData.getValue().staffIdProperty().asObject());
        clientIdColumn.setCellValueFactory(cellData -> cellData.getValue().clientIdProperty().asObject());
    }


    @FXML
    private void handleInsertApp() {
        mainApp.showAppInsertDialog();
    }

    @FXML
    private void handleUpdateApp(){
        Appointment selectedApp = appointmentTable.getSelectionModel().getSelectedItem();
        if(selectedApp != null){
            mainApp.showAppUpdateDialog(selectedApp);
            //mainApp.getAppointmentData().remove(selectedApp);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Staff Selected");
            alert.setContentText("Please select a Staff into the table.");

            alert.showAndWait();
        }
    }
    @FXML
    private void handleDeleteApp() {

        int selectedIndex = appointmentTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            Appointment selectedApp = appointmentTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Delete an Appointment");
            alert.setHeaderText("Do you want to delete this appointment?");
            alert.setContentText("Do you want to delete this appointment?");
            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne){
                try{
                    //mainApp.getStaffData().forEach(System.out::println);
                    dao.delete(selectedApp.getId());
                    mainApp.getAppointmentData().remove(selectedApp);
                    //mainApp.getStaffData().forEach(System.out::println);

                }catch (StaffException e){

                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Database Error");
                    errorAlert.setHeaderText("Could not delete appointment");
                    errorAlert.setContentText("An error occurred while trying to delete the appointment.");
                    errorAlert.showAndWait();

                }

            }
        }

        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Appointment Selected");
            alert.setContentText("Please select an Appointment into the table.");

            alert.showAndWait();
        }
    }
    public void handleHome(){
       if(mainApp.getIsManager())
           mainApp.initStaffManager();
       else
           mainApp.initStaff();
    }
}

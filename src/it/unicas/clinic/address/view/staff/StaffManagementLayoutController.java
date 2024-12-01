package it.unicas.clinic.address.view.staff;

import it.unicas.clinic.address.Main;
import it.unicas.clinic.address.model.Staff;
import it.unicas.clinic.address.model.dao.StaffDAO;
import it.unicas.clinic.address.model.dao.StaffException;
import it.unicas.clinic.address.model.dao.mysql.StaffDAOMySQLImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;


public class StaffManagementLayoutController {

    @FXML
    private TableView<Staff> staffTable;
    @FXML
    private TableColumn<Staff, String> nameColumn;
    @FXML
    private TableColumn<Staff, String> surnameColumn;
    @FXML
    private TableColumn<Staff, String> specColumn;
    @FXML
    private TableColumn<Staff, Integer> idColumn;


    // Reference to the main application.
    private Main mainApp;
    private StaffDAO dao=StaffDAOMySQLImpl.getInstance();

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        // Add observable list data to the table
        staffTable.setItems(mainApp.getStaffData());
        mainApp.getStaffData().addAll(dao.select(new Staff(0,null, null, null)));

    }
    @FXML
    public void initialize() {
        // Binding delle colonne con le proprietà della classe Staff
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        specColumn.setCellValueFactory(cellData -> cellData.getValue().specialtiesProperty());
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject()); // IntegerProperty richiede asObject()


    }


    @FXML
    private void showStaff(){
        //StaffDAOMySQLImpl.getInstance().select(new Staff(0,null, null, null));
        mainApp.getStaffData().addAll(dao.select(new Staff(0,null, null, null)));
    }
    @FXML
    private void handleInsertStaff() {
        mainApp.showStaffInsertDialog();
    }
    @FXML
    private void handleUpdateStaff(){
        Staff selectedStaff = staffTable.getSelectionModel().getSelectedItem();
        if(selectedStaff != null){
            mainApp.showStaffUpdateDialog(selectedStaff);
            // mainApp.getStaffData().remove(selectedStaff);
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
    private void handleDeleteStaff() {

        int selectedIndex = staffTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            Staff selectedStaff = staffTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Delete a Staff Member");
            alert.setHeaderText("Do you want to delete this member?");
            alert.setContentText("Do you want to delete this member?");
            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne){
                try{
                    //mainApp.getStaffData().forEach(System.out::println);
                    dao.delete(selectedStaff);
                    mainApp.getStaffData().remove(selectedStaff);
                    //mainApp.getStaffData().forEach(System.out::println);

                }catch (StaffException e){

                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Database Error");
                    errorAlert.setHeaderText("Could not delete staff");
                    errorAlert.setContentText("An error occurred while trying to delete the staff member.");
                    errorAlert.showAndWait();

                }

            }
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
}

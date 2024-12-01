package it.unicas.clinic.address.view.appointment;

import it.unicas.clinic.address.Main;
import it.unicas.clinic.address.model.Staff;
import it.unicas.clinic.address.model.dao.StaffDAO;
import it.unicas.clinic.address.model.dao.StaffException;
import it.unicas.clinic.address.model.dao.mysql.StaffDAOMySQLImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;


public class AppStaffViewController {

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
    private Stage dialogStage;

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
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    @FXML
    private void handleSelect(){
        Staff selectedStaff = staffTable.getSelectionModel().getSelectedItem();
        if(selectedStaff != null){
            //System.out.println(selectedStaff.getId());
            mainApp.saveStaff(selectedStaff.getId());
            //mainApp.getStaffData().remove(selectedStaff);
        }
        else{
            mainApp.errorAlert("Error",
                    "Table Error",
                    "Please select a staff member");
        }
        mainApp.getStaffData().clear();
        dialogStage.close();
    }
    @FXML
    private void handleCancel(){
        dialogStage.close();
    }
}

package it.unicas.clinic.address.view.schedule;

import it.unicas.clinic.address.Main;
import it.unicas.clinic.address.model.Schedule;
import it.unicas.clinic.address.model.Staff;
import it.unicas.clinic.address.model.dao.ScheduleDAO;
import it.unicas.clinic.address.model.dao.ScheduleException;
import it.unicas.clinic.address.model.dao.StaffDAO;
import it.unicas.clinic.address.model.dao.mysql.ScheduleDAOMySQLImpl;
import it.unicas.clinic.address.model.dao.mysql.StaffDAOMySQLImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class ScheduleManagementLayoutController {
    @FXML
    private TableView<Schedule> scheduleTable;
    @FXML
    private TableColumn<Schedule, Integer> idColumn;
    @FXML
    private TableColumn<Schedule, LocalDate> dayColumn;
    @FXML
    private TableColumn<Schedule, LocalTime> starTimeColumn;
    @FXML
    private TableColumn<Schedule, LocalTime> endTimeColumn;
    @FXML
    private TableColumn<Schedule, Integer> staffidColumn;

    private Main mainApp;
    private ScheduleDAO dao= ScheduleDAOMySQLImpl.getInstance();
    private Schedule schedule;
    private Staff staff; //staff selezionato nella StaffManagementLayout, del quale mostrare gli schedule

    public void setMainApp(Main mainApp, Staff s) {
        this.mainApp = mainApp;
        // Add observable list data to the table
        scheduleTable.setItems(mainApp.getScheduleData());
        staff=s;
        staff.setId(s.getId());
        mainApp.getScheduleData().addAll(dao.select(new Schedule(0, null, null, null, staff.getId())));
    }
    @FXML
    private void initialize() {
        //nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        //surnameColumn.setCellValueFactory(cellData -> cellData.getValue().surnameProperty());
        //specColumn.setCellValueFactory(cellData -> cellData.getValue().specialtiesProperty());
        //idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        starTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("stopTime"));
        staffidColumn.setCellValueFactory(new PropertyValueFactory<>("staffId"));


    }
    @FXML
    private void handleInsertSchedule() {
        mainApp.showScheduleInsertDialog(staff);

    }
    @FXML
    private void handleDeleteSchedule() {
        //check se ho selezionato uno schedule da cancellare
        int selectedIndex = scheduleTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0){
            Schedule selectedSchedule = scheduleTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Delete a Staff Member");
            alert.setHeaderText("Do you want to delete this schedule?");
            alert.setContentText("Do you want to delete this schedule?");
            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {
                try{
                    dao.delete(selectedSchedule);
                    mainApp.getScheduleData().remove(selectedSchedule);
                }catch(ScheduleException e){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Database Error");
                    errorAlert.setHeaderText("Could not delete schedule");
                    errorAlert.setContentText(e.getMessage());
                    errorAlert.showAndWait();
                }
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Schedule Selected");
            alert.setContentText("Please select a Schedule into the table.");
            alert.showAndWait();
        }
    }
    @FXML
    private void handleUpdateSchedule() {
        //take the staff del quale dovrò mostrare gli schedule
        Schedule selectedSchedule = scheduleTable.getSelectionModel().getSelectedItem();
        if(selectedSchedule != null){
            //System.out.println("HO preso lo staff x lo schedule");
            //System.out.println(selectedStaff);
            mainApp.showScheduleUpdateDialog(selectedSchedule, staff);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Schedule Selected");
            alert.setContentText("Please select a Schedule into the table.");

            alert.showAndWait();
        }

    }



}


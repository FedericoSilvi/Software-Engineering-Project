package it.unicas.clinic.address.view.schedule;

import it.unicas.clinic.address.Main;
import it.unicas.clinic.address.model.Schedule;
import it.unicas.clinic.address.model.Staff;
import it.unicas.clinic.address.model.dao.ScheduleDAO;
import it.unicas.clinic.address.model.dao.StaffDAO;
import it.unicas.clinic.address.model.dao.mysql.ScheduleDAOMySQLImpl;
import it.unicas.clinic.address.model.dao.mysql.StaffDAOMySQLImpl;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.LocalTime;

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

    }
    @FXML
    private void handleUpdateSchedule() {

    }



}


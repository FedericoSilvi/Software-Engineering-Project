package it.unicas.clinic.address.view.appointment;

import it.unicas.clinic.address.Main;
import it.unicas.clinic.address.model.Appointment;
import it.unicas.clinic.address.model.Schedule;
import it.unicas.clinic.address.model.Staff;
import it.unicas.clinic.address.model.dao.AppointmentDAO;
import it.unicas.clinic.address.model.dao.ClientDAO;
import it.unicas.clinic.address.model.dao.StaffDAO;
import it.unicas.clinic.address.model.dao.mysql.AppointmentDAOMySQLImpl;
import it.unicas.clinic.address.model.dao.mysql.DAOClient;
import it.unicas.clinic.address.model.dao.mysql.StaffDAOMySQLImpl;
import it.unicas.clinic.address.utils.DataUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

public class AppSelectViewController {

    @FXML
    private TableView<Schedule> dateTable;
    @FXML
    private TableColumn<Schedule, LocalDate> dateColumn;
    @FXML
    private TableView<LocalTime> timeTable;
    @FXML
    private TableColumn<LocalTime, String> timeColumn;

    @FXML
    private Label serviceL;
    @FXML
    private Label dateL;
    @FXML
    private Label timeL;
    @FXML
    private Label durationL;
    @FXML
    private Label staffL;
    @FXML
    private Label clientL;


    private Main mainApp;
    private ArrayList<ArrayList<Boolean>> boolList=new ArrayList<>();
    private LocalDate date;
    private LocalTime time;
    private StaffDAO staffDAO = StaffDAOMySQLImpl.getInstance();
    private AppointmentDAO appDAO = AppointmentDAOMySQLImpl.getInstance();



    public void setMainApp(Main main,ArrayList<Schedule> schedules,ArrayList<ArrayList<Boolean>> list){
        this.mainApp = main;
        //sched has both null and not null schedules available for that appointment
        ObservableList<Schedule> sched = FXCollections.observableArrayList(schedules);
        /*for(Schedule schedule : schedules){
            dates.add(schedule.getDay());
        }*/
        // Add observable list data to the table
        ObservableList<Schedule> temp=FXCollections.observableArrayList();
        //Check the element in the boolean list and add to temp the corresponding schedule only if !=null
        for(int i=0; i<list.size();i++){
            if(list.get(i)!=null)
                temp.add(sched.get(i));
        }
        //Saving temp (containing only not null schedules) permanently in mainApp
        mainApp.saveAppSchedData(temp);
        dateTable.setItems(mainApp.getAppSchedData());
        //Saving approved schedules in boolean format boolList
        for(int i=0; i<list.size();i++){
            if(list.get(i)!=null)
                boolList.add(list.get(i));
        }
        if(temp.isEmpty())
            handleEmpty();
    }
    public void initialize() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
    }

    /**
     * Shows the possible timetables for the appointment once the user clicked the date
     */
    @FXML
    public void showTime(){
        Schedule selectedSchedule = dateTable.getSelectionModel().getSelectedItem();
        date=selectedSchedule.getDay();
        int index = dateTable.getSelectionModel().getSelectedIndex();
        if(selectedSchedule!=null){
            //Computing the necessary slots for the appointment
            int slots= (int) Math.ceil((mainApp.getSavedDuration().getHour()*60
                    +mainApp.getSavedDuration().getMinute())/30.0);
            //Selecting boolean translation of selectedSchedule
            ArrayList<Boolean> temp = boolList.get(index);
            System.out.println(temp);
            ObservableList<LocalTime> times = DataUtil.timeSlots(selectedSchedule,temp,slots);
            timeTable.setItems(times);
            timeColumn.setCellValueFactory(cellData -> {
                // Ottieni il valore LocalTime dalla riga
                LocalTime time = cellData.getValue();
                // Convertilo in una stringa formattata
                String timeString = time != null ? time.toString() : "";
                // Restituisci come ObjectProperty<String>
                return new SimpleObjectProperty<>(timeString);
            });
        }
        else{
            System.out.println("Selected date is null");
        }
    }

    /**
     * Shows the appointment recap
     * @throws SQLException
     */
    @FXML
    public void showDetail() throws SQLException {
        LocalTime selectedTime = timeTable.getSelectionModel().getSelectedItem();
        time = selectedTime;
        serviceL.setText(mainApp.getSavedService());
        dateL.setText(date.toString());
        timeL.setText(selectedTime.toString());
        durationL.setText(mainApp.getSavedDuration().toString());
        if(mainApp.getIsManager())
            staffL.setText(staffDAO.select(mainApp.getSavedStaff()).getName()+"\n"+
                 staffDAO.select(mainApp.getSavedStaff()).getSurname());
        else
            staffL.setText(staffDAO.select(mainApp.getUser_id()).getName()+"\n"+
                    staffDAO.select(mainApp.getUser_id()).getSurname());
        clientL.setText(DAOClient.getClient(mainApp.getSavedClient()).getName()+"\n"+
                DAOClient.getClient(mainApp.getSavedClient()).getSurname());
    }
    @FXML
    public void handleAddApp() throws SQLException, IOException {
        if(date==null||time==null){
            mainApp.warningAlert("Warning",
                    "Information warning",
                    "Please select date and time for the appointment");
            return;
        }
        if(mainApp.getIsManager()) {
            appDAO.insert(new Appointment(mainApp.getSavedService(), date, time, mainApp.getSavedDuration(),
                    mainApp.getSavedStaff(), mainApp.getSavedClient()));
            mainApp.getAppointmentData().add(new Appointment(appDAO.getLastApp().getId(),mainApp.getSavedService(), date,time,mainApp.getSavedDuration(),
                    mainApp.getSavedStaff(), mainApp.getSavedClient()));
        }
        else {
            appDAO.insert(new Appointment(mainApp.getSavedService(), date, time, mainApp.getSavedDuration(),
                    mainApp.getUser_id(), mainApp.getSavedClient()));
            mainApp.getAppointmentData().add(new Appointment(appDAO.getLastApp().getId(),mainApp.getSavedService(), date,time,mainApp.getSavedDuration(),
                    mainApp.getUser_id(), mainApp.getSavedClient()));
        }

        mainApp.initAppView();
    }
    @FXML
    public void handleCancel(){
        mainApp.showAppInsertDialog();
        mainApp.initAppView();
    }
    private void handleEmpty(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Empty list");
        alert.setContentText("No schedules found");

        ButtonType buttonTypeOne = new ButtonType("Ok");

        alert.getButtonTypes().setAll(buttonTypeOne);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            mainApp.showAppInsertDialog();
            mainApp.initAppView();
        }
    }

}
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
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ScheduleAddingLayoutController {
    @FXML
    private TextField dayField;
    @FXML
    private TextField startHourField;
    @FXML
    private TextField endHourField;


    private Main mainApp;
    private Stage dialogStage;
    private Schedule schedule;
    private Staff staff;
    private ScheduleDAO dao= ScheduleDAOMySQLImpl.getInstance();

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        // Set the dialog icon.
        //this.dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
    }
    public void setMainApp(Main main, Staff s) {
        this.staff = s;
        this.staff.setId(s.getId());//in questo modo ho l'id dello staff che ho selezionato nella finestra precedente
        this.mainApp = main;
    }

    @FXML
    private void handleSave(){
        //this.staff = new Staff(nameField.getText(), surnameField.getText(), specField.getText());
        try {
            LocalDate d = LocalDate.parse(dayField.getText());
            LocalTime st = LocalTime.parse(startHourField.getText());
            LocalTime et = LocalTime.parse(endHourField.getText());
            this.schedule = new Schedule(d, st, et, staff.getId());
            if(verifySchedule(this.schedule) && !isEmpty(this.schedule)){
                //aggiungo nel db e nalla lista
                try {
                    dao.insert(this.schedule);
                    //devo recuperare l'id associato a questo schedule, quindi recupero l'ultimo
                    //System.out.println(dao.getLastSchedule());
                    mainApp.getScheduleData().add(dao.getLastSchedule());
                    dialogStage.close();
                }catch (ScheduleException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Database Error");
                    errorAlert.setHeaderText("Could not insert schedule");
                    errorAlert.setContentText("An error occurred while trying to insert schedule-");
                    errorAlert.showAndWait();
                }catch (SQLException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Database Error");
                    errorAlert.setHeaderText("Could not insert schedule");
                    errorAlert.setContentText("An error occurred while trying to insert schedule");
                    errorAlert.showAndWait();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error-logic");
                alert.setHeaderText("Error");
                alert.setContentText("Errore logico nelle ore/data-Campi vuoti");
                alert.showAndWait();
            }
        }catch (DateTimeParseException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error-Format");
            alert.setHeaderText("Error"); //errore sul formato o campi vuoti
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }
    private boolean verifySchedule(Schedule schedule) {
        //controlla se l'ora di inzio viene prima di quella di fine, e se il giorno è futuro (le eccezioni sul formato sono prese prima)

        if (schedule == null) {
            return false; // Oggetto null non è valido
        }

        // Verifica che la data sia valida (deve essere una data futura)
        LocalDate day = schedule.getDay();
        if (day.isBefore(LocalDate.now())) {
            return false; // La data non può essere nel passato
        }
        LocalTime startTime = schedule.getStartTime();
        LocalTime endTime = schedule.getStopTime();
        // Controlla che l'ora di inizio sia prima dell'ora di fine
        if (!startTime.isBefore(endTime)) {
            return false; // L'ora di inizio deve essere precedente all'ora di fine
        }

        // Se tutti i controlli sono passati, l'oggetto Schedule è valido
        return true;
    }
    private boolean isEmpty(Schedule schedule) {

        if (schedule.getDay() == null || schedule.getStartTime() == null || schedule.getStopTime() == null) {
            return true;
        }
        else
            return false;
    }

}

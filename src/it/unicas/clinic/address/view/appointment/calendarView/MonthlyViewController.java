package it.unicas.clinic.address.view.appointment.calendarView;

import it.unicas.clinic.address.Main;
import it.unicas.clinic.address.model.Appointment;
import it.unicas.clinic.address.model.dao.AppointmentDAO;
import it.unicas.clinic.address.model.dao.mysql.AppointmentDAOMySQLImpl;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MonthlyViewController {

    private Main mainApp;
    public void setMainApp(Main mainApp) throws SQLException {
        this.mainApp = mainApp;
        init2();
    }

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private LocalDate date;

    private AppointmentDAO dao = AppointmentDAOMySQLImpl.getInstance();

    private int counter = 0;

    private ArrayList<Label> labelList = new ArrayList<>();

    private int clientId = 0;

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    private int staffId = 0;

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    private String service = null;

    public void setService(String service) {
        this.service = service;
    }

    int rows = 6;
    int cols = 7;

    private int month;

    private int year;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label dateLabel;

    @FXML
    private void initialize() throws Exception {
        date = LocalDate.now();

        month = date.getMonth().getValue();

        year = date.getYear();

        dateLabel.setText(Month.of(month) + " " + year);

  //      LocalDate firstDay = LocalDate.of(year, month, 1);

    }

    private void init2() throws SQLException {


        boolean first = true;

        LocalDate firstDay = LocalDate.of(year, month, 1);

        int day = firstDay.getDayOfWeek().getValue();


        for(int i = 1 ; i <= rows ; i++) {
            for(int j = 0 ; j < cols ; j++) {
                if(firstDay.getMonth().getValue() != month)
                {
                    break;
                }
                if(first) {
                    j = day - 1;
                    first = false;
                }

                labelList.add(new Label(firstDay.getDayOfMonth() + ""));
                labelList.get(counter).setAlignment(Pos.CENTER);

                if(!mainApp.getIsManager()) {
                    staffId = mainApp.getUser_id();
                }

                if(AppointmentDAOMySQLImpl.filterByDate(firstDay, clientId, staffId, service)){
                    labelList.get(counter).setStyle("-fx-background-color: lightblue; -fx-padding: 10px; -fx-text-fill: black; -fx-font-size: 16px;");
                    LocalDate finalFirstDay = firstDay;
                    labelList.get(counter).setOnMouseClicked(event -> {
                        showAppointment(finalFirstDay.getDayOfMonth(), month, finalFirstDay.getYear());
                    });
                }

                gridPane.setHalignment(gridPane, HPos.CENTER);
                gridPane.setValignment(gridPane, VPos.CENTER);
                gridPane.add(labelList.get(counter), j, i);

                counter ++;
                firstDay = firstDay.plusDays(1);
            }
        }
    }
    public void filter() throws SQLException {
        for(int i  = 0 ; i < counter ; i ++) {
            labelList.get(i).setStyle("-fx-background-color: transparent; -fx-padding: 10px; -fx-text-fill: black; -fx-font-size: 16px;");
            labelList.get(i).setOnMouseClicked(null);
        }
        LocalDate firstDay = LocalDate.of(year, month, 1);

        boolean first = true;

        int day = firstDay.getDayOfWeek().getValue();


        counter = 0;

        for(int i = 1 ; i <= rows ; i++) {
            for(int j = 0 ; j < cols ; j++) {
                if(firstDay.getMonth().getValue() != month)
                {
                    break;
                }
                if(first) {
                    j = day - 1;
                    first = false;
                }

                List<Appointment> list =  dao.select(new Appointment(null,service, firstDay, null, null, staffId, clientId));
                System.out.println(clientId);
                System.out.println(list);
                // if(AppointmentDAOMySQLImpl.filterByDate(firstDay, clientId, staffId, service))
                if(list.size() >= 1){
                    labelList.get(counter).setStyle("-fx-background-color: lightblue; -fx-padding: 10px; -fx-text-fill: black; -fx-font-size: 16px;");
                    LocalDate finalFirstDay = firstDay;
                    labelList.get(counter).setOnMouseClicked(event -> {
                        System.out.println("ENTRATO");
                        showAppointment(finalFirstDay.getDayOfMonth(), month, finalFirstDay.getYear());
                    });
                }

                gridPane.setHalignment(gridPane, HPos.CENTER);
                gridPane.setValignment(gridPane, VPos.CENTER);

                counter ++;
                firstDay = firstDay.plusDays(1);
            }
        }
    }

    private void showAppointment(int day, int month, int year) {
       // Aggiungere un pulsante per filtrare gli appointment tramite data
       // e aprire la finestra da qui, chiamando la funzione subito dopo
       AppointmentDAO dao = AppointmentDAOMySQLImpl.getInstance();

        mainApp.getAppointmentData().clear();

       String day2 = "";
       String month2 = "";

       if (day < 10 || month < 10) {
           if(day < 10) {
               day2 = "0" + day;
           } else {
               day2 = String.valueOf(day);
           }
           if(month < 10) {
               month2 = "0" + month;
           } else {
               month2 = String.valueOf(month);
           }
       }
       String ymd = year + "-" + month2 + "-" + day2;


       if (mainApp.getIsManager()) {
           mainApp.getAppointmentData().addAll(dao.select(new Appointment(0,service, LocalDate.parse(ymd), null, null, staffId, clientId)));
       } else {
           mainApp.getAppointmentData().addAll(dao.select(new Appointment(0,service, LocalDate.parse(ymd), null, null, mainApp.getUser_id(), clientId)));
       }
        mainApp.showCalendarAppView();
    }

    @FXML
    private void handleWeeklyView() throws IOException {
        mainApp.showWeeklyView();
        stage.close();

    }

    @FXML
    private void handleDailyView() throws IOException {
        mainApp.showDailyView();
        stage.close();
    }

    @FXML
    private void handleFilter() throws IOException {
        if(mainApp.getIsManager()) {
            mainApp.filterCalendarViewForManager(this, null, null);
        } else {
            mainApp.filterCalendarViewForMember(this, null, null);
        }
    }

    @FXML
    private void handleRightArrow() throws SQLException {
        month ++;

        if(month == 13) {
            month = 1;
            year++;
        }

        for(int i = 0; i < labelList.size(); i++) {
            labelList.get(i).setText("");
            labelList.get(i).setStyle("-fx-background-color: transparent; -fx-padding: 10px; -fx-text-fill: black; -fx-font-size: 16px;");
            labelList.get(i).setOnMouseClicked(null);
        }

        labelList.remove(labelList);

        dateLabel.setText(Month.of(month) + " " + year);
        init2();
    }

    @FXML
    private void handleLeftArrow() throws SQLException {
        month --;

        if(month == 0) {
            month = 12;
            year--;
        }

        for(int i = 0; i < labelList.size(); i++) {
            labelList.get(i).setText("");
            labelList.get(i).setStyle("-fx-background-color: transparent; -fx-padding: 10px; -fx-text-fill: black; -fx-font-size: 16px;");
            labelList.get(i).setOnMouseClicked(null);
        }

        labelList.remove(labelList);

        dateLabel.setText(Month.of(month) + " " + year);
        init2();
    }

    @FXML
    private void handleClose() {
        stage.close();
    }
}

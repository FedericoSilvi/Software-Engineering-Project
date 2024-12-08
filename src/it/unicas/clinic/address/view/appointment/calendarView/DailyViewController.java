package it.unicas.clinic.address.view.appointment.calendarView;

import it.unicas.clinic.address.Main;
import it.unicas.clinic.address.model.Appointment;
import it.unicas.clinic.address.model.dao.AppointmentDAO;
import it.unicas.clinic.address.model.dao.mysql.AppointmentDAOMySQLImpl;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class DailyViewController {
    private Main mainApp;
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private AppointmentDAO dao = AppointmentDAOMySQLImpl.getInstance();

    private LocalDate today = LocalDate.now();

    @FXML
    private GridPane gridPane;

    @FXML
    private Label dayLabel;

    @FXML
    private void initialize() {
        dayLabel.setText(today.toString());

        ArrayList<LocalTime> hours = new ArrayList<>();

        for(int i = 0; i < 24; i++) {
            hours.add(LocalTime.of(i, 0));
            hours.add(LocalTime.of(i, 30));
        }


        for (int i = 0; i < hours.size(); i++) {
            javafx.scene.control.Label hourLabel = new javafx.scene.control.Label(hours.get(i).toString());
            gridPane.add(hourLabel, 0, i + 1);
        }

        for(int i = 0; i < hours.size(); i++) {
            ArrayList<Appointment> list2 = (ArrayList<Appointment>) dao.select(new Appointment(0, null, today, hours.get(i), null, null, null));
            if(list2.size() > 0) {
                for(int k = 0; k < list2.size(); k++) {
                    LocalTime duration = list2.get(k).getDuration();
                    int n = (duration.getHour() * 60 + duration.getMinute())/30;

                    for(int j = 1; j <= n; j++) {
                        Label label = new Label(list2.get(k).getService());
                        label.setStyle("-fx-background-color: lightblue; -fx-padding: 10px; -fx-text-fill: black; -fx-font-size: 16px;");

                        int tempIndex = i;
                        label.setOnMouseClicked(event -> {
                            showAppointment(today.getDayOfMonth(), today.getMonthValue(), today.getYear(), hours.get(tempIndex));
                        });
                        gridPane.add(label, 1, i + j);
                    }
                }

            }
        }
    }

    private void showAppointment(int day, int month, int year, LocalTime hour) {
        // Aggiungere un pulsante per filtrare gli appointment tramite data
        // e aprire la finestra da qui, chiamando la funzione subito dopo
        AppointmentDAO dao= AppointmentDAOMySQLImpl.getInstance();
        mainApp.initAppView();
        mainApp.getAppointmentData().clear();

        System.out.println("CLICK ");

        String day2 = "";
        String ymd = "";
        if(day < 10) {
            String temp = "" + day;
            day2 = "0" + day;
            ymd = year + "-" + month + "-" + day2;
        } else {
            ymd = year + "-" + month + "-" + day;

        }

        mainApp.getAppointmentData().addAll(dao.select(new Appointment(0,null, LocalDate.parse(ymd), hour,null, null, 0)));

    }

    @FXML
    private void handleWeeklyView() throws IOException {
        mainApp.showWeeklyView();
        stage.close();

    }

    @FXML
    private void handleMonthlyView() throws IOException {
        mainApp.showMonthlyView();
        stage.close();
    }

    @FXML
    private void handleClose() {
        stage.close();
    }
}

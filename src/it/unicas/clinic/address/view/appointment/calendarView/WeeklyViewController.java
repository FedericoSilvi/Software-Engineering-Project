package it.unicas.clinic.address.view.appointment.calendarView;

import it.unicas.clinic.address.Main;
import it.unicas.clinic.address.model.Appointment;
import it.unicas.clinic.address.model.dao.AppointmentDAO;
import it.unicas.clinic.address.model.dao.mysql.AppointmentDAOMySQLImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class WeeklyViewController {
    private Main mainApp;
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        init2();
    }

    private void  init2() {
        for(int i = 0; i < 24; i++) {
            hours.add(LocalTime.of(i, 0));
            hours.add(LocalTime.of(i, 30));
        }

        for (int i = 0; i < hours.size(); i++) {
            Label hourLabel = new Label(hours.get(i).toString());
            gridPane.add(hourLabel, 0, i + 1); // +1 perché la prima riga sarà per i giorni
        }

        int day = today.getDayOfWeek().getValue();
        int x = day - 1;

        int counter = 0;

        LocalDate start = today.minusDays(x);

        if(mainApp.getIsManager()){
            staffId = 0;
        } else {
            staffId = mainApp.getUser_id();
        }

        for(int row = 1; row <= 7; row ++) {
            for(int i = 0; i < hours.size(); i++) {
                ArrayList<Appointment> list2 = (ArrayList<Appointment>) dao.select(new Appointment(0, null, start, hours.get(i), null, staffId, null));
                if(list2.size() > 0) {
                    for(int k = 0; k < list2.size(); k++) {
                        LocalTime duration = list2.get(k).getDuration();
                        int n = (duration.getHour() * 60 + duration.getMinute())/30;

                        for(int j = 1; j <= n; j++) {
                            Label label = new Label(list2.get(k).getService());
                            label.setStyle("-fx-background-color: lightblue; -fx-padding: 10px; -fx-text-fill: black; -fx-font-size: 16px;");
                            labelList.add(label);

                            LocalDate tempDate = start;
                            int tempIndex = i;
                            labelList.get(counter).setOnMouseClicked(event -> {
                                showAppointment(tempDate.getDayOfMonth(), tempDate.getMonthValue(), tempDate.getYear(), hours.get(tempIndex));
                            });
                            gridPane.add(labelList.get(counter), row, i + j);
                            counter++;
                        }
                    }

                }
            }
            start = start.plusDays(1);
        }


       /* mondayColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        tuesdayColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        wednesdayColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        thursdayColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        fridayColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        saturdayColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        sundayColumn.setCellValueFactory(new PropertyValueFactory<>("service"));

        int day = today.getDayOfWeek().getValue();
        int x = day - 1;

        LocalDate start = today.minusDays(x);
        System.out.println(start);



        ArrayList<Appointment> list = (ArrayList<Appointment>) dao.select(new Appointment(0, null, null, null, null, null, null));

        appointments.clear();
        appointments.addAll(list);

        appointmentTable.setItems(appointments);

      //  appointmentTable;*/
    }

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private AppointmentDAO dao = AppointmentDAOMySQLImpl.getInstance();

    private LocalDate today = LocalDate.now();

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

    private ArrayList<Label> labelList = new ArrayList<>();

    private ArrayList<LocalTime> hours = new ArrayList<>();

    @FXML
    private GridPane gridPane = new GridPane();


  /*  @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn hoursColumn;

    @FXML
    private TableColumn<Appointment,String> mondayColumn;

    @FXML
    private TableColumn<Appointment,String> tuesdayColumn;

    @FXML
    private TableColumn<Appointment,String> wednesdayColumn;

    @FXML
    private TableColumn<Appointment,String> thursdayColumn;

    @FXML
    private TableColumn<Appointment,String> fridayColumn;

    @FXML
    private TableColumn<Appointment,String> saturdayColumn;

    @FXML
    private TableColumn<Appointment,String> sundayColumn;*/

    @FXML
    private void initialize() {

     /*   for(int i = 0; i < 24; i++) {
            hours.add(LocalTime.of(i, 0));
            hours.add(LocalTime.of(i, 30));
        }

        for (int i = 0; i < hours.size(); i++) {
            Label hourLabel = new Label(hours.get(i).toString());
            gridPane.add(hourLabel, 0, i + 1); // +1 perché la prima riga sarà per i giorni
        }

        int day = today.getDayOfWeek().getValue();
        int x = day - 1;

        int counter = 0;

        LocalDate start = today.minusDays(x);

        for(int row = 1; row <= 7; row ++) {
            for(int i = 0; i < hours.size(); i++) {
                ArrayList<Appointment> list2 = (ArrayList<Appointment>) dao.select(new Appointment(0, null, start, hours.get(i), null, null, null));
                if(list2.size() > 0) {
                    for(int k = 0; k < list2.size(); k++) {
                        LocalTime duration = list2.get(k).getDuration();
                        int n = (duration.getHour() * 60 + duration.getMinute())/30;

                        for(int j = 1; j <= n; j++) {
                            Label label = new Label(list2.get(k).getService());
                            label.setStyle("-fx-background-color: lightblue; -fx-padding: 10px; -fx-text-fill: black; -fx-font-size: 16px;");
                            labelList.add(label);

                            LocalDate tempDate = start;
                            int tempIndex = i;
                            labelList.get(counter).setOnMouseClicked(event -> {
                                showAppointment(tempDate.getDayOfMonth(), tempDate.getMonthValue(), tempDate.getYear(), hours.get(tempIndex));
                            });
                            gridPane.add(labelList.get(counter), row, i + j);
                            counter++;
                        }
                    }

                }
            }
            start = start.plusDays(1);
        }*/


       /* mondayColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        tuesdayColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        wednesdayColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        thursdayColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        fridayColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        saturdayColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        sundayColumn.setCellValueFactory(new PropertyValueFactory<>("service"));

        int day = today.getDayOfWeek().getValue();
        int x = day - 1;

        LocalDate start = today.minusDays(x);
        System.out.println(start);



        ArrayList<Appointment> list = (ArrayList<Appointment>) dao.select(new Appointment(0, null, null, null, null, null, null));

        appointments.clear();
        appointments.addAll(list);

        appointmentTable.setItems(appointments);

      //  appointmentTable;*/

    }

    public void filter() {
        // copiare quello che c'è in monthly view

        for(int i  = 0 ; i < labelList.size() ; i ++) {
            labelList.get(i).setText("");
            labelList.get(i).setStyle("-fx-background-color: transparent; -fx-padding: 10px; -fx-text-fill: black; -fx-font-size: 16px;");
            labelList.get(i).setOnMouseClicked(null);
        }

        labelList.removeAll(labelList);

        int day = today.getDayOfWeek().getValue();
        int x = day - 1;

        LocalDate start = today.minusDays(x);

        for(int row = 1; row <= 7; row ++) {
            for(int i = 0; i < hours.size(); i++) {
                ArrayList<Appointment> list2 = (ArrayList<Appointment>) dao.select(new Appointment(0, service, start, hours.get(i), null, staffId, clientId));
                if(list2.size() > 0) {
                    for(int k = 0; k < list2.size(); k++) {
                        LocalTime duration = list2.get(k).getDuration();
                        int n = (duration.getHour() * 60 + duration.getMinute())/30;

                        for(int j = 1; j <= n; j++) {
                            Label label = new Label(list2.get(k).getService());
                            label.setStyle("-fx-background-color: lightblue; -fx-padding: 10px; -fx-text-fill: black; -fx-font-size: 16px;");
                            labelList.add(label);

                            LocalDate tempDate = start;
                            int tempIndex = i;
                            label.setOnMouseClicked(event -> {
                                showAppointment(tempDate.getDayOfMonth(), tempDate.getMonthValue(), tempDate.getYear(), hours.get(tempIndex));
                            });
                            gridPane.add(label, row, i + j);
                        }
                    }

                }
            }
            start = start.plusDays(1);
        }
        clientId = 0;
        staffId = 0;
        service = null;

    }

    private void showAppointment(int day, int month, int year, LocalTime hour) {
        // Aggiungere un pulsante per filtrare gli appointment tramite data
        // e aprire la finestra da qui, chiamando la funzione subito dopo
        AppointmentDAO dao= AppointmentDAOMySQLImpl.getInstance();
        mainApp.initAppView();
        mainApp.getAppointmentData().clear();

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
    private void handleDailyView() throws IOException {
        mainApp.showDailyView();
        stage.close();
    }

    @FXML
    private void handleMonthlyView() throws IOException, SQLException {
        mainApp.showMonthlyView();
        stage.close();
    }

    @FXML
    private void handleFilter() throws IOException {
        mainApp.filterCalendarViewForManager(null, this, null);
    }


    @FXML
    private void handleClose() {
        stage.close();
    }
}

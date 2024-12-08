package it.unicas.clinic.address.view.appointment.calendarView;

import it.unicas.clinic.address.Main;
import it.unicas.clinic.address.model.Appointment;
import it.unicas.clinic.address.model.Client;
import it.unicas.clinic.address.model.dao.AppointmentDAO;
import it.unicas.clinic.address.model.dao.mysql.AppointmentDAOMySQLImpl;
import it.unicas.clinic.address.model.dao.mysql.DAOClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class FilterByController {
    private Main mainApp;
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private AppointmentDAO dao = AppointmentDAOMySQLImpl.getInstance();

    private MonthlyViewController controller;

    public void setMonthlyViewController (MonthlyViewController _controller) {
        controller = _controller;
    }

    @FXML
    private TextField filterTxt;

    @FXML
    private void handleFilter() throws SQLException {
        //Filtro per cliente (cognome)
        //dal cognome del cliente devo trovare l'id

        System.out.println("FILTERED");

        ArrayList<Client> list = DAOClient.filterClient("", filterTxt.getText(), "");

        if(list.size() == 1) {
            int clientId = list.get(0).getId();

            controller.setClientId(clientId);
            controller.filter();
            System.out.println("FILTERED");

         //   mainApp.getAppointmentData().addAll(dao.select(new Appointment(0,null, null, null,null, null, clientId)));
        } else if(list.size() > 1) {
            for(int i = 0 ; i < list.size() ; i++) {
                int clientId = list.get(i).getId();

                controller.setClientId(clientId);
                controller.filter();

            }
        } else {
            System.out.println("No client selected");
        }

        stage.close();
    }

    @FXML
    private void handleClose() {
        stage.close();
    }
}

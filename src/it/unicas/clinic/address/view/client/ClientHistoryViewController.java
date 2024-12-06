package it.unicas.clinic.address.view.client;

import it.unicas.clinic.address.Main;
import it.unicas.clinic.address.model.Appointment;
import it.unicas.clinic.address.model.dao.AppointmentDAO;
import it.unicas.clinic.address.model.dao.StaffDAO;
import it.unicas.clinic.address.model.dao.StaffException;
import it.unicas.clinic.address.model.dao.mysql.AppointmentDAOMySQLImpl;
import it.unicas.clinic.address.model.dao.mysql.LoginDAOImplementation;
import it.unicas.clinic.address.model.dao.mysql.StaffDAOMySQLImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class ClientHistoryViewController {
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private TableColumn<Appointment, String> serviceColumn;
    @FXML
    private TableColumn<Appointment, LocalDate> dateColumn;
    @FXML
    private TableColumn<Appointment, LocalTime> timeColumn;
    @FXML
    private TableColumn<Appointment, LocalTime> durationColumn;
    @FXML
    private TableColumn<Appointment, String> staffColumn;


    // Reference to the main application.
    private Main mainApp;
    private AppointmentDAO dao= AppointmentDAOMySQLImpl.getInstance();
    private StaffDAO staffDao = StaffDAOMySQLImpl.getInstance();

    public ClientHistoryViewController() throws SQLException {
    }


    public void setMainApp(Main mainApp,int id) throws SQLException {
        this.mainApp = mainApp;
        // Add observable list data to the table
        appointmentTable.setItems(mainApp.getAppointmentData());
        if(mainApp.getIsManager())
            mainApp.getAppointmentData().addAll(dao.getHistoryApp(id));
        else
            mainApp.getAppointmentData().addAll(dao.getHistoryApp(id));

    }
    @FXML
    public void initialize() {
        // Column binding with Appointment's properties
        serviceColumn.setCellValueFactory(cellData -> cellData.getValue().serviceProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        timeColumn.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        durationColumn.setCellValueFactory(cellData -> cellData.getValue().durationProperty());
        staffColumn.setCellValueFactory(cellData -> {
            int staffId = cellData.getValue().getStaffId();
            String staffName = null;
            try {
                staffName = staffDao.select(staffId).getName()+ " " +staffDao.select(staffId).getSurname();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return new SimpleStringProperty(staffName);
        });
    }


    @FXML
    public void handleClose() throws SQLException, IOException {
        mainApp.showClientView();
    }
}

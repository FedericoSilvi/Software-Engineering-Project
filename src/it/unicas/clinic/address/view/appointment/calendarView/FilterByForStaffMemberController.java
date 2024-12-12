package it.unicas.clinic.address.view.appointment.calendarView;

import it.unicas.clinic.address.Main;
import it.unicas.clinic.address.model.Client;
import it.unicas.clinic.address.model.Staff;
import it.unicas.clinic.address.model.dao.AppointmentDAO;
import it.unicas.clinic.address.model.dao.StaffDAO;
import it.unicas.clinic.address.model.dao.mysql.AppointmentDAOMySQLImpl;
import it.unicas.clinic.address.model.dao.mysql.DAOClient;
import it.unicas.clinic.address.model.dao.mysql.StaffDAOMySQLImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class FilterByForStaffMemberController {
    private boolean clickClient=false;
    private boolean atLeastOneFilter=false;

    private static AppointmentDAO daoApp= AppointmentDAOMySQLImpl.getInstance();
    private static StaffDAO daoStaff = StaffDAOMySQLImpl.getInstance();
    @FXML
    private TextField startDatePeriodTF;
    @FXML
    private TextField endDatePeriodTF;
    @FXML
    private TextField serviceTF;

    private Staff choosenStaff = null;
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;

    private ArrayList<Client> choosenClient = null;
    @FXML
    private Label clientNameLabel;
    @FXML
    private Label clientSurnameLabel;

    //private String titleTxt="report";
    private Main mainApp;
    private Stage dialogStage;
    String service=null;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    MonthlyViewController mvController;

    public void setMvController(MonthlyViewController mvController) {
        this.mvController = mvController;
    }

    WeeklyViewController wvController;

    public void setWvController(WeeklyViewController wvController) {
        this.wvController = wvController;
    }

    DailyViewController dvController;

    public void setDvController(DailyViewController dvController) {
        this.dvController = dvController;
    }


    @FXML
    private void choosenClient() throws SQLException, IOException {
        clickClient=true;
        //set the choosenStaff fields

        mainApp.showAppClient();
        if (mainApp.getSavedClient() != 0) {
            System.out.println(mainApp.getSavedClient());
            Client c = DAOClient.select(mainApp.getSavedClient());
            choosenClient = DAOClient.filterClient(c.getName(), c.getSurname(), c.getEmail());

            clientNameLabel.setText(choosenClient.get(0).getName());
            clientSurnameLabel.setText(choosenClient.get(0).getSurname());

        }

        //set labels

    }

    @FXML
    private void filter() throws SQLException {
        service = serviceTF.getText();

        if(mvController != null){
            if(choosenClient != null) {
                mvController.setClientId(choosenClient.get(0).getId());
            }

            if(choosenStaff != null) {
                mvController.setStaffId(choosenStaff.getId());
            }

            if(service != null || service != "") {
                mvController.setService(service);
            }

            mvController.filter();
        } else if(wvController != null){
            if(choosenClient != null) {
                wvController.setClientId(choosenClient.get(0).getId());
            }

            if(choosenStaff != null) {
                wvController.setStaffId(choosenStaff.getId());
            }

            if(service != null || service != "") {
                wvController.setService(service);
            }

            wvController.filter();
        } else if(dvController != null){
            if(choosenClient != null) {
                dvController.setClientId(choosenClient.get(0).getId());
            }

            if(choosenStaff != null) {
                dvController.setStaffId(choosenStaff.getId());
            }

            if(service != null || service != "") {
                dvController.setService(service);
            }

            dvController.filter();
        }


        dialogStage.close();

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

}
package it.unicas.clinic.address.view;

import it.unicas.clinic.address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class StaffManagerInitialLayoutController {

    private MainApp mainApp;

    @FXML
    private ImageView clientBackground;
    @FXML
    private ImageView staffBackground;
    @FXML
    private ImageView appointmentBackground;
    @FXML
    private ImageView calendarBackground;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize(){
    }
    @FXML
    private void handleExit(){
        mainApp.handleExit();
    }
    @FXML
    private void handleClientHighlight(){
        clientBackground.setOpacity(0.50);
    }
    @FXML
    private void handleStaffHighlight(){
        staffBackground.setOpacity(0.50);
    }
    @FXML
    private void handleAppointmentsHighlight(){
        appointmentBackground.setOpacity(0.50);
    }
    @FXML
    private void handleCalendarHighlight(){
        calendarBackground.setOpacity(0.50);
    }
    @FXML
    private void handleClientDownlight(){
        clientBackground.setOpacity(0.25);
    }
    @FXML
    private void handleStaffDownlight(){
        staffBackground.setOpacity(0.25);
    }
    @FXML
    private void handleAppointmentsDownlight(){
        appointmentBackground.setOpacity(0.25);
    }
    @FXML
    private void handleCalendarDownlight(){
        calendarBackground.setOpacity(0.25);
    }
}

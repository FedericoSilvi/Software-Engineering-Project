package it.unicas.clinic.address.view;

import it.unicas.clinic.address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

import java.util.Optional;

public class StaffMemberInitialLayoutController {

    private MainApp mainApp;

    @FXML
    private ImageView clientBackground;
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
    private void handleLogout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("Click "+"\n"+"'Yes' to logout"+"\n"+"'Back' to close the window");

        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Back", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            mainApp.initLogin();
        }
    }
    @FXML
    private void handleClientHighlight(){
        clientBackground.setOpacity(0.50);
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
    private void handleAppointmentsDownlight(){
        appointmentBackground.setOpacity(0.25);
    }
    @FXML
    private void handleCalendarDownlight(){
        calendarBackground.setOpacity(0.25);
    }
}

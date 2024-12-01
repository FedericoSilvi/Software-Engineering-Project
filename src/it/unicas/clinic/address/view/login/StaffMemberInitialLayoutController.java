package it.unicas.clinic.address.view.login;

import it.unicas.clinic.address.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Staff manager GUI. It provides 3 sections to select
 * - Client section to manage clients
 * - Appointments section to manage appointments
 * - Calendar section to see appointments in calendar mode
 */
public class StaffMemberInitialLayoutController {

    private Main main;

    @FXML
    private ImageView clientBackground;
    @FXML
    private ImageView appointmentBackground;
    @FXML
    private ImageView calendarBackground;

    public void setMainApp(Main main) {
        this.main = main;
    }

    @FXML
    private void initialize(){
    }
    @FXML
    private void handleLogout(){

        //Alert to go back to login GUI
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("Click "+"\n"+"'Yes' to logout"+"\n"+"'Back' to close the window");

        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Back", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            main.initLogin();
        }
    }
    //Methods to highlight sections only when mouse passes on them
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
    @FXML
    private void handleClientView() throws SQLException, IOException {
        main.showClientView();
    }
}

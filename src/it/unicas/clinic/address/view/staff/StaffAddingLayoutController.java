package it.unicas.clinic.address.view.staff;

import it.unicas.clinic.address.Main;
import it.unicas.clinic.address.model.Staff;
import it.unicas.clinic.address.model.dao.StaffDAO;
import it.unicas.clinic.address.model.dao.StaffException;
import it.unicas.clinic.address.model.dao.mysql.StaffDAOMySQLImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Optional;

public class StaffAddingLayoutController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField specField;


    private Main mainApp;
    private Stage dialogStage;
    private boolean verifyLen = true;
    private Staff staff;
    private boolean okClicked = false;
    private StaffDAO dao=StaffDAOMySQLImpl.getInstance();
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.verifyLen = verifyLen;

        // Set the dialog icon.
        //this.dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    public void setMainApp(Main main) {
        this.mainApp = main;
    }
    /*public void setStaff(){
        this.staff = new Staff(0, null, null, null);
        this.staff.setName(nameField.getText());
        this.staff.setSurname(surnameField.getText());
        this.staff.setSpecialties(specField.getText());

    }*/

    @FXML
    private void handleSave() {
        this.staff = new Staff(nameField.getText(), surnameField.getText(), specField.getText());
        try {
            if(this.staff.verifyStaff(this.staff)){
                try {
                    dao.insert(this.staff);
                    mainApp.getStaffData().add(dao.getLastStaff());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initOwner(mainApp.getPrimaryStage());
                    alert.setTitle("Schedule");
                    alert.setHeaderText("Do you want to add a schedule?");
                    alert.setContentText("Do you want to add a schedule?");
                    ButtonType buttonTypeOne = new ButtonType("Yes");
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);
                    dialogStage.close();
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == buttonTypeOne){
                        mainApp.addSchedule();
                    }
                }catch (StaffException e){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Database Error");
                    errorAlert.setHeaderText("Could not delete staff");
                    errorAlert.setContentText("An error occurred while trying to add the staff member.");
                    errorAlert.showAndWait();
                }


            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("ERror");
                alert.setContentText("You have to fill all the fields!!");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    private boolean isInputValid() {
        return true;
    }

    private void insertSchedule() {

    }

}

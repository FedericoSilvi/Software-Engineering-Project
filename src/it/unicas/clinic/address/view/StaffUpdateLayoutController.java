package it.unicas.clinic.address.view;

import it.unicas.clinic.address.Main;
import it.unicas.clinic.address.model.Staff;
import it.unicas.clinic.address.model.dao.StaffDAO;
import it.unicas.clinic.address.model.dao.StaffException;
import it.unicas.clinic.address.model.dao.mysql.StaffDAOMySQLImpl;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class StaffUpdateLayoutController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField specField;

    private Staff staff;
    private Stage dialogStage;
    private Main mainApp;
    private StaffDAO dao= StaffDAOMySQLImpl.getInstance();
    @FXML
    private void initialize() {
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        // Set the dialog icon.
        //this.dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
    }
    public void setField(Staff s){
        staff=s;
        nameField.setText(s.getName());
        surnameField.setText(s.getSurname());
        specField.setText(s.getSpecialties());
    }
    @FXML
    private void handleUpdate() {
        staff.setName(nameField.getText());
        staff.setSurname(surnameField.getText());
        staff.setSpecialties(specField.getText());
        try {
            dao.update(staff);
            Staff updatedStaff = new Staff(staff.getId(), staff.getName(), staff.getSurname(), staff.getSpecialties());
            int index = mainApp.getStaffData().indexOf(staff);
            if (index >= 0) {
                mainApp.getStaffData().set(index, updatedStaff); // Sostituisce l'oggetto nella lista
            }

            dialogStage.close();
        } catch (StaffException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Database Error");
            errorAlert.setHeaderText("Could not delete staff");
            errorAlert.setContentText("An error occurred while trying to delete the staff member.");
            errorAlert.showAndWait();
        }
    }
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

}

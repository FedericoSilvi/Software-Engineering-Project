package it.unicas.clinic.address.view;

import it.unicas.clinic.address.MainApp;
import it.unicas.clinic.address.model.dao.mysql.LoginDAOImplementation;
import it.unicas.clinic.address.utils.DataUtil.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class LoginLayoutController {

    private MainApp mainApp;


    private String password;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordShowField;
    @FXML
    private PasswordField passwordHideField;
    @FXML
    private ImageView openedEye;
    @FXML
    private ImageView closedEye;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    @FXML
    private void initialize(){
        passwordShowField.setVisible(false);
        passwordHideField.setVisible(true);
        closedEye.setVisible(false);
        openedEye.setVisible(true);
        password="";
    }

    @FXML
    private void handleShowPassword() {
        password=passwordHideField.getText();
        passwordShowField.setVisible(true);
        passwordShowField.setText(password);
        passwordHideField.setVisible(false);
        closedEye.setVisible(true);
        openedEye.setVisible(false);
    }
    @FXML
    private void handleHidePassword() {
        password=passwordShowField.getText();
        passwordShowField.setVisible(false);
        passwordHideField.setVisible(true);
        passwordHideField.setText(password);
        closedEye.setVisible(false);
        openedEye.setVisible(true);
    }
    @FXML
    private void handleExit(){
        mainApp.handleExit();
    }
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
         if(closedEye.isVisible()){
             password= passwordShowField.getText();
         }
         else
             password= passwordHideField.getText();
         LoginDAOImplementation impl = new LoginDAOImplementation(username,password);
        User data = null;
        try {
            data = impl.searchUser();
        } catch (SQLException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Database Error");
            error.setHeaderText("Something went wrong within database connection/operations");
            error.setContentText("Please contact the database assistance");

            ButtonType buttonTypeOne = new ButtonType("Ok");

            error.getButtonTypes().setAll(buttonTypeOne);

            Optional<ButtonType> result = error.showAndWait();
            if (result.get() == buttonTypeOne){
                System.exit(0);
            }
        }
        if(data!=null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login result");
            alert.setHeaderText("Logged in successfully");
            alert.setContentText("Welcome back "+data.getName()+" "+data.getSurname());
             ButtonType button = new ButtonType("Ok");
             alert.getButtonTypes().setAll(button);
             Optional<ButtonType> r = alert.showAndWait();
             if (r.get() == button){
                 alert.close();
                 if(data.getManager())
                     mainApp.initStaffManager();
                 else
                     mainApp.initStaff();
             }

         }
         else {
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Login result");
             alert.setHeaderText("Login failed");
             alert.setContentText("Username or password is incorrect");
             ButtonType button = new ButtonType("Ok");
             alert.getButtonTypes().setAll(button);
             Optional<ButtonType> r = alert.showAndWait();
             if (r.get() == button){
                 alert.close();
             }
         }
    }
    @FXML
    private void handleEnter(KeyEvent event) throws SQLException {
        if(event.getCode()==KeyCode.ENTER){
            handleLogin();
        }
    }
}

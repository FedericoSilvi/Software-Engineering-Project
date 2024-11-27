package it.unicas.clinic.address;

import it.unicas.clinic.address.model.Staff;
import it.unicas.clinic.address.view.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import java.io.IOException;

import static javafx.application.Application.launch;

/**
 * Class implementing the application
 */

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane loginLayout;
    private BorderPane staffInitialLayout;
    private ObservableList<Staff> staffData = FXCollections.observableArrayList();
    private BorderPane page;
    public ObservableList<Staff> getStaffData() {
        return staffData;
    }


    public Main() {
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Default method called at the start of the application.
     * It makes the login GUI appear.
     */
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.getIcons().add(new Image("file:src/resources/login_icons/clinic-icon.png"));
        this.primaryStage.setTitle("Clinic");

        initLogin();
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Pop up an alert for the exit operation.
     */
    public void handleExit() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure you want to quit the app?");
        alert.setContentText("Click " + "\n" + "'Yes' to exit" + "\n" + "'Back' to close the window");

        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Back", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            System.exit(0);
        }
    }

    /**
     * Load the login GUI to the application window
     */
    public void initLogin() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class
                    .getResource("view/LoginLayout.fxml"));
            loginLayout = (BorderPane) loader.load();

            // Show the scene containing the login layout.
            Scene scene = new Scene(loginLayout);
            primaryStage.setScene(scene);

            //Implementing alert when you click on the 'X' of the window
            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                handleExit();
            });


            // Give the controller access to the main app.
            LoginLayoutController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Load the initial staff member GUI to the application window
     */
    public void initStaff(){
        try{

            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class
                    .getResource("view/StaffMemberInitialLayout.fxml"));
            staffInitialLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(staffInitialLayout);
            primaryStage.setScene(scene);

            //Implementing alert when you click on the 'X' of the window
            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                handleExit();
            });


            // Give the controller access to the main app.
            StaffMemberInitialLayoutController controller = loader.getController();
            controller.setMainApp(this);

            //Set and show primary stage
            primaryStage.centerOnScreen();
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Load the initial staff manager GUI to the application window
     */
    public void initStaffManager(){
        try{

            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class
                    .getResource("view/StaffManagerInitialLayout.fxml"));
            staffInitialLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(staffInitialLayout);
            primaryStage.setScene(scene);

            //Implementing alert when you click on the 'X' of the window
            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                handleExit();
            });


            // Give the controller access to the main app.
            StaffManagerInitialLayoutController controller = loader.getController();
            controller.setMainApp(this);

            //Set and show primary stage
            primaryStage.centerOnScreen();
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStaffInsertDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/StaffAddingLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Staff");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Controller
            StaffAddingLayoutController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);
            //controller.setStaff();

            // Set the dialog icon.
            //dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
            dialogStage.showAndWait();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void addSchedule() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ScheduleAddingLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Schedule");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Controller
            ScheduleAddingLayoutController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);
            //controller.setStaff();

            // Set the dialog icon.
            //dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
            dialogStage.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void showStaffUpdateDialog(Staff s){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/StaffUpdateLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Update Stuff");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Controller
            StaffUpdateLayoutController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);
            controller.setField(s);

            // Set the dialog icon.
            //dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
            dialogStage.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadStaffManagementChoose() throws IOException{
        primaryStage.getIcons().add(new Image("file:resources/clinic-icon-3.png"));
        this.primaryStage.setTitle("Clinic");
        FXMLLoader loader = new FXMLLoader();
        this.primaryStage.setTitle("Clinic");
        loader.setLocation(Main.class.getResource("view/ChooseOwnerLayout.fxml"));
        page =  loader.load();
        Scene scene = new Scene(page);
        primaryStage.setScene(scene);
        ChooseOwnerLayoutController controller = loader.getController();
        controller.setMain(this);
    }
    public void loadStaffManagement() throws IOException{
        staffData.clear();
        loadStaffManagementChoose();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/StaffManagementLayout2.fxml"));
        page.setCenter(loader.load());
        StaffManagementLayoutController controller = loader.getController();
        controller.setMainApp(this);
        primaryStage.show();
}

    public void loadScheduleManagement() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/ScheduleAddingLayout.fxml"));
        page.setCenter(loader.load());
        ScheduleAddingLayoutController controller = loader.getController();
        controller.setMainApp(this);
        primaryStage.show();
    }
        public static void main(String[] args) {
        launch(args);
    }
}
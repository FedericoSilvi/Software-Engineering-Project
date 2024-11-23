package it.unicas.clinic.address;


import it.unicas.clinic.address.view.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static javafx.application.Application.launch;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane loginLayout;
    private BorderPane staffInitialLayout;

    public void start(Stage primaryStage){
        this.primaryStage=primaryStage;
        primaryStage.getIcons().add(new Image("file:src/resources/login_icons/clinic-icon.png"));
        this.primaryStage.setTitle("Clinic");

        initLogin();
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void handleExit(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exit");
        alert.setHeaderText("Are you sure you want to quit the app?");
        alert.setContentText("Click "+"\n"+"'Yes' to exit"+"\n"+"'Back' to close the window");

        ButtonType buttonTypeOne = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Back", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            System.exit(0);
        }
    }

    public void initLogin() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/LoginLayout.fxml"));
            loginLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
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

    public void initStaffManager(){
    try{
        //Close previous scenes
        //primaryStage.close();
        // Load root layout from fxml file.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class
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

    public void initStaff(){
        try{
            //Close previous scenes
            //primaryStage.close();
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
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

    public static void main(String[] args) {
        launch(args);
    }
}
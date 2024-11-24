package it.unicas.clinic.address;

import it.unicas.clinic.address.model.Staff;
import it.unicas.clinic.address.view.ScheduleAddingLayoutController;
import it.unicas.clinic.address.view.StaffAddingLayoutController;
import it.unicas.clinic.address.view.StaffManagementLayoutController;
import it.unicas.clinic.address.view.StaffUpdateLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {

    private Stage primaryStage;
    private ObservableList<Staff> staffData = FXCollections.observableArrayList();
    public ObservableList<Staff> getStaffData() {
        return staffData;
    }


    public Main() {
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

    public void start(Stage primaryStage) throws IOException {
        this.primaryStage=primaryStage;
        //primaryStage.getIcons().add(new Image("file:resources/clinic-icon-3.png"));
        this.primaryStage.setTitle("Clinic");
        FXMLLoader loader = new FXMLLoader();
        //loader.setLocation(Main.class.getResource("view/StaffManagementLayout.fxml"));
        loader.setLocation(Main.class.getResource("view/StaffManagementLayout2.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

// Ottieni il controller e passa il riferimento di `Main`
        StaffManagementLayoutController controller = loader.getController();
        controller.setMainApp(this);

        primaryStage.show();

    }
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public static void main(String[] args) {

        launch(args);

    }
}
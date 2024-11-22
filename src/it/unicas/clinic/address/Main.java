package it.unicas.clinic.address;

import it.unicas.clinic.address.model.Staff;
import it.unicas.clinic.address.model.dao.mysql.StaffDAOMySQLImpl;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.SQLException;

import static javafx.application.Application.launch;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {

    private Stage primaryStage;

    public void start(Stage primaryStage){
        this.primaryStage=primaryStage;
        primaryStage.getIcons().add(new Image("file:resources/clinic-icon-3.png"));
        this.primaryStage.setTitle("Clinic");

        primaryStage.show();

    }

    public static void main(String[] args) {

        launch(args);

    }
}
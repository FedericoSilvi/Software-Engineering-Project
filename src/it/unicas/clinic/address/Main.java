package it.unicas.clinic.address;

import javafx.application.Application;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {

    private Stage primaryStage;

    public void start(Stage primaryStage){
        this.primaryStage=primaryStage;
        this.primaryStage.setTitle("Clinic");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
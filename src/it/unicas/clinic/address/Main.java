package it.unicas.clinic.address;

import it.unicas.clinic.address.model.Client;
import it.unicas.clinic.address.model.dao.mysql.DAOMySQLSettings;
import it.unicas.clinic.address.view.ClientOverviewController;
import it.unicas.clinic.address.view.SearchClientController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javafx.application.Application.launch;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        Client client = null;
        ArrayList<Client> list = null;


        try{
            DAOMySQLSettings.check();
          //  client = DAOMySQLSettings.getClient(1);

            System.out.println("");

            list =DAOMySQLSettings.getClientsList();

            DAOMySQLSettings.checkSchedule();

         /*   for(int i = 0; i < list.size(); i++){
                System.out.println(list.get(i));
            }

            System.out.println(client);*/

        //    System.out.println("Modifico Donald Duck:");

         //   DAOMySQLSettings.insert();
        //    DAOMySQLSettings.delete(10);
        //    DAOMySQLSettings.update(10);
        //    DAOMySQLSettings.check();
        } catch (SQLException e) {
            System.out.println("ERRORE NELLA LETTURA DEL DATABASE");
            e.printStackTrace();
        }

        launch(args);


    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage=primaryStage;
        this.primaryStage.setTitle("Clinic");
        BorderPane root = FXMLLoader.load(getClass().getResource("/it/unicas/clinic/address/view/RootLayout.fxml"));
        root.setCenter(FXMLLoader.load(getClass().getResource("/it/unicas/clinic/address/view/ClientOverview.fxml")));


     /*   ClientOverviewController overviewController = FXMLLoader.load(getClass().getResource("/it/unicas/clinic/address/view/ClientOverview.fxml"));
        SearchClientController searchController = FXMLLoader.load(getClass().getResource("/it/unicas/clinic/address/view/SearchClient.fxml"));

        searchController.SetClientOverviewController(overviewController);*/


        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
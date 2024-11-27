package it.unicas.clinic.address;

import it.unicas.clinic.address.model.Client;
import it.unicas.clinic.address.model.dao.mysql.DAOClient;
import it.unicas.clinic.address.model.dao.mysql.DAOMySQLSettings;
import it.unicas.clinic.address.view.AddClientController;
import it.unicas.clinic.address.view.ClientOverviewController;
import it.unicas.clinic.address.view.SearchClientController;
import it.unicas.clinic.address.view.UpdateClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static javafx.application.Application.launch;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        Client client = null;
        ArrayList<Client> list = null;


        try{
            DAOClient.check();
          //  client = DAOMySQLSettings.getClient(1);

            System.out.println("");

            list =DAOClient.getClientsList();

            DAOClient.checkSchedule();

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

    public Main(){

    }

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        this.primaryStage=primaryStage;
        this.primaryStage.setTitle("Clinic");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/it/unicas/clinic/address/view/ClientOverview.fxml"));
     //   BorderPane root = FXMLLoader.load(getClass().getResource("/it/unicas/clinic/address/view/RootLayout.fxml"));
        AnchorPane root = loader.load();

        ClientOverviewController controller = loader.getController();
        controller.setMainApp(this);
        controller.ShowAllClients();

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public void searchClientLayout(ClientOverviewController clientController) throws IOException {
        Stage searchWindow = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicas/clinic/address/view/SearchClient.fxml"));
        AnchorPane layout = loader.load();


        SearchClientController controller = loader.getController();
        controller.setStage(searchWindow);
        controller.setMainApp(this);
        controller.SetClientOverviewController(clientController);

        searchWindow.initModality(Modality.WINDOW_MODAL);
        searchWindow.initOwner(primaryStage);


        searchWindow.setScene(new Scene(layout));
        searchWindow.showAndWait();
    }

    public void AddClientLayout(ClientOverviewController clientController) throws IOException {
        Stage addWindow = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicas/clinic/address/view/AddClient.fxml"));
        AnchorPane layout = loader.load();
        AddClientController controller = loader.getController();
        controller.setStage(addWindow);
        controller.setMainApp(this);
        controller.SetClientOverviewController(clientController);

        addWindow.initModality(Modality.WINDOW_MODAL);
        addWindow.initOwner(primaryStage);

        addWindow.setScene(new Scene(layout));
        addWindow.show();
    }

    public void UpdateClientLayout(ClientOverviewController clientController, Client client) throws IOException {
        Stage updateWindow = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/it/unicas/clinic/address/view/UpdateClient.fxml"));
        AnchorPane layout = (AnchorPane) loader.load();
        UpdateClientController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(updateWindow);
        controller.SetClientOverviewController(clientController);
        controller.getClient(client);

        //definisco suo padre (x forza)
        updateWindow.initModality(Modality.WINDOW_MODAL);
        updateWindow.initOwner(primaryStage);

        updateWindow.setScene(new Scene(layout));
        updateWindow.showAndWait();
    }
}
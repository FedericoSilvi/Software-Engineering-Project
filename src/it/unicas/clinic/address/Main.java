package it.unicas.clinic.address;

import it.unicas.clinic.address.model.Appointment;
import it.unicas.clinic.address.model.Schedule;
import it.unicas.clinic.address.model.Staff;
import it.unicas.clinic.address.utils.DataUtil.AppInfo;
import it.unicas.clinic.address.view.appointment.*;
import it.unicas.clinic.address.view.client.*;
import it.unicas.clinic.address.view.credential.EditStaffCredentialController;
import it.unicas.clinic.address.view.login.ChangePasswordController;
import it.unicas.clinic.address.view.login.ChangeUsernameController;
import it.unicas.clinic.address.view.login.LoginLayoutController;
import it.unicas.clinic.address.view.login.StaffManagerInitialLayoutController;
import it.unicas.clinic.address.view.login.StaffMemberInitialLayoutController;
import it.unicas.clinic.address.view.schedule.ScheduleAddingLayoutController;
import it.unicas.clinic.address.view.schedule.ScheduleManagementLayoutController;
import it.unicas.clinic.address.view.schedule.ScheduleUpdateLayoutController;
import it.unicas.clinic.address.view.staff.ChooseOwnerLayoutController;
import it.unicas.clinic.address.view.staff.StaffAddingLayoutController;
import it.unicas.clinic.address.view.staff.StaffManagementLayoutController;
import it.unicas.clinic.address.view.staff.StaffUpdateLayoutController;
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
import it.unicas.clinic.address.model.Client;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import java.sql.SQLException;

/**
 * Class implementing the application
 */

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane loginLayout;
    private BorderPane staffInitialLayout;
    private AnchorPane appInitialLayout;
    private ObservableList<Staff> staffData = FXCollections.observableArrayList();
    private ObservableList<Appointment> appointmentData = FXCollections.observableArrayList();
    private ObservableList<Schedule> appSchedData = FXCollections.observableArrayList();
    private BorderPane page;
    private AppInfo appInfo = new AppInfo();
    private Boolean isManager;
    private int user_id;
    private ObservableList<Schedule> scheduleData = FXCollections.observableArrayList();
    public ObservableList<Schedule> getScheduleData() {
        return scheduleData;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public ObservableList<Schedule> getAppSchedData() {
        return appSchedData;
    }
    public void saveAppSchedData(ObservableList<Schedule> list){appSchedData=list;}
    public ObservableList<Staff> getStaffData() {
        return staffData;
    }
    public ObservableList<Appointment> getAppointmentData() {
        return appointmentData;
    }
    public Boolean getIsManager(){return isManager;}
    public void setIsManager(Boolean isManager){this.isManager=isManager;}

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

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        this.primaryStage=primaryStage;
        this.primaryStage.setTitle("Clinic");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/it/unicas/clinic/address/view/client/ClientOverview.fxml"));
        //   BorderPane root = FXMLLoader.load(getClass().getResource("/it/unicas/clinic/address/view/RootLayout.fxml"));
        AnchorPane root = loader.load();

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
                    .getResource("view/login/LoginLayout.fxml"));
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
                    .getResource("view/login/StaffMemberInitialLayout.fxml"));
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
                    .getResource("view/login/StaffManagerInitialLayout.fxml"));
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
            loader.setLocation(Main.class.getResource("view/staff/StaffAddingLayout.fxml"));
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



    public void showStaffUpdateDialog(Staff s){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/staff/StaffUpdateLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Update Staff");
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
        loader.setLocation(Main.class.getResource("view/staff/ChooseOwnerLayout.fxml"));
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
        loader.setLocation(Main.class.getResource("view/staff/StaffManagementLayout2.fxml"));
        page.setCenter(loader.load());
        StaffManagementLayoutController controller = loader.getController();
        controller.setMainApp(this);
        primaryStage.show();
    }



    public void searchClientLayout(ClientOverviewController clientController) throws IOException {
        Stage searchWindow = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicas/clinic/address/view/client/SearchClient.fxml"));
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

    public void addClientLayout(ClientOverviewController clientController) throws IOException {
        Stage addWindow = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unicas/clinic/address/view/client/AddClient.fxml"));
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

    public void updateClientLayout(ClientOverviewController clientController, Client client) throws IOException {
        Stage updateWindow = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/it/unicas/clinic/address/view/client/UpdateClient.fxml"));
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

    public void showClientView() throws IOException, SQLException {
        this.primaryStage.setTitle("Clinic");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/it/unicas/clinic/address/view/client/ClientOverview.fxml")); //   BorderPane root = FXMLLoader.load(getClass().getResource("/it/unicas/clinic/address/view/RootLayout.fxml"));
        AnchorPane root = loader.load();
        ClientOverviewController controller = loader.getController();    controller.setMainApp(this);
        controller.ShowAllClients();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    public void initAppView(){
        try{
            appointmentData.clear();
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class
                    .getResource("view/appointment/AppointmentView.fxml"));
            appInitialLayout = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(appInitialLayout);
            primaryStage.setScene(scene);

            //Implementing alert when you click on the 'X' of the window
            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                handleExit();
            });


            // Give the controller access to the main app.
            AppointmentViewController controller = loader.getController();
            controller.setMainApp(this);

            //Set and show primary stage
            primaryStage.centerOnScreen();
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void showAppInsertDialog() {
        if(isManager) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("view/appointment/AppAddingLayout2.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Add Appointment");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                //Controller
                AppAddingLayoutController2 controller = loader.getController();
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
        else{
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("view/appointment/AppAddingLayout3.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Add Appointment");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);
                Scene scene = new Scene(page);
                dialogStage.setScene(scene);

                //Controller
                AppAddingLayoutController3 controller = loader.getController();
                controller.setMainApp(this);
                controller.setDialogStage(dialogStage);
                //controller.setStaff();

                // Set the dialog icon.
                //dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
                dialogStage.showAndWait();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            catch (SQLException e){
                throw new RuntimeException(e);
            }
        }

    }
    public void showAppUpdateDialog(Appointment a){
        try {
            FXMLLoader loader = new FXMLLoader();
            if(isManager)
                loader.setLocation(Main.class.getResource("view/appointment/AppUpdateLayout2.fxml"));
            else
                loader.setLocation(Main.class.getResource("view/appointment/AppUpdateLayout3.fxml"));

            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Update Stuff");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Controller
            if(isManager) {
                AppUpdateLayoutController2 controller = loader.getController();
                controller.setMainApp(this);
                controller.setDialogStage(dialogStage);
                controller.setField(a);
            }
            else{
                AppUpdateLayoutController3 controller = loader.getController();
                controller.setMainApp(this);
                controller.setDialogStage(dialogStage);
                controller.setField(a);
            }
            // Set the dialog icon.
            //dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
            dialogStage.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void errorAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void warningAlert(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void showAppStaff() throws IOException {
        appointmentData.clear();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/appointment/AppStaffView.fxml"));
        AnchorPane p = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Appointment");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(p);
        dialogStage.setScene(scene);
        AppStaffViewController controller = loader.getController();
        controller.setMainApp(this);
        controller.setDialogStage(dialogStage);

        dialogStage.show();
    }
    public void showAppClient() throws IOException, SQLException {
        appointmentData.clear();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/appointment/AppClientView.fxml"));
        AnchorPane p = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Appointment");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(p);
        dialogStage.setScene(scene);
        AppClientViewController controller = loader.getController();
        controller.setMainApp(this);
        controller.ShowAllClients();
        controller.setDialogStage(dialogStage);

        dialogStage.show();
    }
    public void saveStaff(int id){
        appInfo.setStaff(id);
    }
    public int getSavedStaff(){
        return appInfo.getStaff();
    }
    public void saveClient(int id){appInfo.setClient(id);}
    public int getSavedClient(){return appInfo.getClient();}
    public void saveService(String service){appInfo.setService(service);}
    public String getSavedService(){return appInfo.getService();}
    public void saveDuration(LocalTime duration){appInfo.setDuration(duration);}
    public LocalTime getSavedDuration(){return appInfo.getDuration();}
    public void showAvailableApp(ArrayList<Schedule> schedules,ArrayList<ArrayList<Boolean>> list) throws IOException {
        appSchedData.clear();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/appointment/AppSelectView.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Scene scene = new Scene(page);
        primaryStage.setScene(scene);
        AppSelectViewController controller = loader.getController();
        controller.setMainApp(this,schedules,list);
        primaryStage.show();
    }
    public void showAvailableAppUp(ArrayList<Schedule> schedules,ArrayList<ArrayList<Boolean>> list,Appointment a) throws IOException {
        appSchedData.clear();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/appointment/AppSelectView2.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Scene scene = new Scene(page);
        primaryStage.setScene(scene);
        AppSelectViewController2 controller = loader.getController();
        controller.setMainApp(this,schedules,list,a);
        primaryStage.show();
    }

    //schedule mangament
    public void showScheduleManagmentLayout(Staff s) throws IOException {
        //System.out.println(s);
        scheduleData.clear();
        Stage updateWindow = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/it/unicas/clinic/address/view/schedule/ScheduleManagementLayout.fxml"));
        AnchorPane layout = (AnchorPane) loader.load();
        ScheduleManagementLayoutController controller = loader.getController();
        controller.setMainApp(this, s);
        //controller.setStage(updateWindow);

        updateWindow.initModality(Modality.WINDOW_MODAL);
        updateWindow.initOwner(primaryStage);

        updateWindow.setScene(new Scene(layout));
        // Aggiungi un listener per la chiusura della finestra. Quando si chiude devo svuotare la lista degli schedule
        updateWindow.setOnCloseRequest(event -> {
            scheduleData.clear();
        });
        updateWindow.showAndWait();
    }

    public void showScheduleInsertDialog(Staff s) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/schedule/ScheduleAddingLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add Schedule");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Controller
            ScheduleAddingLayoutController controller = loader.getController();
            controller.setMainApp(this, s);
            controller.setDialogStage(dialogStage);

            // Set the dialog icon.
            //dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
            dialogStage.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showScheduleUpdateDialog(Schedule s, Staff staff){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/schedule/ScheduleUpdateLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Update Schedule");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //Controller
            ScheduleUpdateLayoutController controller = loader.getController();
            controller.setMainApp(this, staff);
            controller.setField(s);
            controller.setDialogStage(dialogStage);

            // Set the dialog icon.
            //dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
            dialogStage.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showRescheduleApp(ArrayList<Schedule> schedules,ArrayList<ArrayList<Boolean>> list,Appointment a) throws IOException {
        appSchedData.clear();
        Stage rescheduleWindow = new Stage();
        rescheduleWindow.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/appointment/AppSelectView3.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Scene scene = new Scene(page);
        rescheduleWindow.setScene(scene);
        AppSelectViewController3 controller = loader.getController();
        controller.setMainApp(this,schedules,list,a,rescheduleWindow);
        rescheduleWindow.showAndWait();
    }


    public void changePassword() throws IOException, SQLException {
        Stage changePasswordWindow = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/login/ChangePassword.fxml"));

        AnchorPane anchorPane = loader.load();
        ChangePasswordController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(changePasswordWindow);
        controller.setId(user_id);

        changePasswordWindow.initModality(Modality.WINDOW_MODAL);
        changePasswordWindow.initOwner(primaryStage);

        changePasswordWindow.setScene(new Scene(anchorPane));
        changePasswordWindow.showAndWait();

    }

    public void changeUsername() throws IOException{
        Stage changeUsernameWindow = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/login/ChangeUsername.fxml"));

        AnchorPane anchorPane = loader.load();
        ChangeUsernameController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(changeUsernameWindow);
        controller.setId(user_id);

        changeUsernameWindow.initModality(Modality.WINDOW_MODAL);
        changeUsernameWindow.initOwner(primaryStage);

        changeUsernameWindow.setScene(new Scene(anchorPane));
        changeUsernameWindow.showAndWait();


    }

    public void setId(int id) {user_id = id;}

    public void EditStaffCredential(int id) throws IOException {
        Stage editStaffWindow = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/credential/EditStaffCredential.fxml"));
        AnchorPane anchorPane = loader.load();
        EditStaffCredentialController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(editStaffWindow);
        controller.setManagerId(user_id);
        controller.setId(id);

        editStaffWindow.initModality(Modality.WINDOW_MODAL);
        editStaffWindow.initOwner(primaryStage);

        editStaffWindow.setScene(new Scene(anchorPane));
        editStaffWindow.showAndWait();
    }

    public void showClientHistory(int id){
        try{
            appointmentData.clear();
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class
                    .getResource("view/client/ClientHistoryView.fxml"));
            appInitialLayout = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(appInitialLayout);
            primaryStage.setScene(scene);

            //Implementing alert when you click on the 'X' of the window
            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                handleExit();
            });


            // Give the controller access to the main app.
            ClientHistoryViewController controller = loader.getController();
            controller.setMainApp(this,id);

            //Set and show primary stage
            primaryStage.centerOnScreen();
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }




}
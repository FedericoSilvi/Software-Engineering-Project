package it.unicas.clinic.address.view.appointment;

import it.unicas.clinic.address.Main;
import it.unicas.clinic.address.model.Appointment;
import it.unicas.clinic.address.model.Client;
import it.unicas.clinic.address.model.Schedule;
import it.unicas.clinic.address.model.Staff;
import it.unicas.clinic.address.model.dao.*;
import it.unicas.clinic.address.model.dao.mysql.AppointmentDAOMySQLImpl;
import it.unicas.clinic.address.model.dao.mysql.DAOClient;
import it.unicas.clinic.address.model.dao.mysql.ScheduleDAOMySQLImpl;
import it.unicas.clinic.address.model.dao.mysql.StaffDAOMySQLImpl;
import it.unicas.clinic.address.utils.DataUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.util.ArrayList;

public class AppUpdateLayoutController3 {
    @FXML
    private TextField serviceField;
    @FXML
    private TextField timeField;

    @FXML
    private Label staffL;
    @FXML
    private Label clientName;
    @FXML
    private Label clientSurname;


    private Main mainApp;
    private Stage dialogStage;
    private boolean verifyLen = true;
    private Appointment app;
    private boolean okClicked = false;
    private AppointmentDAO dao= AppointmentDAOMySQLImpl.getInstance();
    private StaffDAO staffDao= StaffDAOMySQLImpl.getInstance();
    private ScheduleDAO scheduleDao = ScheduleDAOMySQLImpl.getInstance();
    private Staff selectedStaff;
    private Client selectedClient;

    @FXML
    private void initialize(){
    }
    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        this.verifyLen = verifyLen;

        // Set the dialog icon.
        //this.dialogStage.getIcons().add(new Image("file:resources/images/edit.png"));
    }
    public void setMainApp(Main main) throws SQLException {
        this.mainApp = main;
        Staff s = staffDao.select(mainApp.getUser_id());
        staffL.setText(s.getName()+" "+s.getSurname());
    }
    public void setField(Appointment a){
        app=a;
        serviceField.setText(a.getService());
        timeField.setText(a.getDuration().toString());
        mainApp.saveStaff(app.getStaffId());
        mainApp.saveClient(app.getClientId());
    }
    @FXML
    private void handleSave() throws SQLException, IOException {
        dao.softDelete(app.getId());
        mainApp.saveService(serviceField.getText());
        try{
            mainApp.saveDuration(DataUtil.parseToDuration(timeField.getText(),true));
        }
        catch(DateTimeException e){
            mainApp.errorAlert("Error",
                    "Time Error",
                    "Please insert valid duration");
        }
        catch(IllegalArgumentException e){
            mainApp.errorAlert("Error",
                    "Format error",
                    "Please insert correct duration format. Expected hh:mm or mm.");
        }
        if(serviceField.getText().isEmpty() || timeField.getText().isEmpty()
                ||mainApp.getSavedService().isEmpty()||mainApp.getSavedDuration()==null){
            mainApp.errorAlert("Error",
                    "Module error",
                    "Please fill all the fields");
        }
        else {


            System.out.println(mainApp.getSavedService());
            System.out.println(mainApp.getSavedStaff());
            System.out.println(mainApp.getSavedClient());
            System.out.println(mainApp.getSavedDuration());

            //Each element of arrayList is linked to a single schedule of scheduleList
            ArrayList<ArrayList<Boolean>> arrayList = new ArrayList<>();
            ArrayList<Schedule> scheduleList= scheduleDao.futureSchedule(mainApp.getUser_id());
            //Boolean translation from schedule list
            for(Schedule schedule:scheduleList){
                arrayList.add(DataUtil.avApp(schedule));
            }
            System.out.println(arrayList);
            //dim saves the position of unavailable schedules
            ArrayList<Integer>dim= new ArrayList<Integer>();
            for(int i=0; i<arrayList.size(); i++){
                System.out.println("Ci sono!!!");
                ArrayList<Boolean> temp =DataUtil.avFilter(arrayList.get(i),mainApp.getSavedDuration());
                if(temp==null) {
                    dim.add(i);
                }
            }
            System.out.println(dim);
            //Set null unavailable schedules using dim to find
            //unavailable schedules
            for(int i=0; i<dim.size(); i++){
                arrayList.set((int)dim.get(i),null);
            }
            System.out.println(arrayList);
            dialogStage.close();
            mainApp.showAvailableAppUp(scheduleList,arrayList,app);
        }
    }

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }
    @FXML
    private void handleClientSelect() throws IOException, SQLException {
        mainApp.showAppClient();
        if(mainApp.getSavedClient()!=0){
            selectedClient= DAOClient.select(mainApp.getSavedClient());
            clientName.setText(selectedClient.getName());
            clientSurname.setText(selectedClient.getSurname());
        }
    }



}

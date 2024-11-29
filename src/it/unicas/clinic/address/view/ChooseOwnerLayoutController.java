package it.unicas.clinic.address.view;

import it.unicas.clinic.address.Main;
import javafx.fxml.FXML;

import java.io.IOException;

public class ChooseOwnerLayoutController {

    private Main main;

    public void setMain(Main main) {
        this.main = main;
    }
    @FXML
    private void handleChooseStaff() throws IOException {
        main.loadStaffManagement();
    }
    /*private void handleChooseStaff() throws IOException {
        main.loadStaffManagement();
    }*/
    @FXML
    private void handleChooseSchedule()throws IOException {
        //main.loadScheduleManagement();
    }
}

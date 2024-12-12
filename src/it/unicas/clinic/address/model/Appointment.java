package it.unicas.clinic.address.model;



import javafx.beans.property.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
    private IntegerProperty id;
    private StringProperty service;
    private ObjectProperty<LocalDate> date;
    private ObjectProperty<LocalTime> time;
    private ObjectProperty<LocalTime> duration;
    private IntegerProperty staffId;
    private IntegerProperty clientId;

    public Boolean getCancellation() {
        return cancellation;
    }

    public void setCancellation(Boolean cancellation) {
        this.cancellation = cancellation;
    }

    private Boolean cancellation;

    public Boolean getNotice() {
        return notice;
    }

    public void setNotice(Boolean notice) {
        this.notice = notice;
    }

    private Boolean notice;


    /**
     * Constructor to build an appointment passing all its attributes
     * @param id: appointment id
     * @param service: service description string
     * @param date: day of the appointment (yyyy-mm-dd format)
     * @param time: appointment hour (?hh-mm format?)
     * @param staffId: id of corresponding staff member
     * @param clientId: id of corresponding client
     */
    public Appointment(Integer id, String service, LocalDate date, LocalTime time, LocalTime duration,Integer staffId, Integer clientId) {
        if(id!=null)
            this.id = new SimpleIntegerProperty(id);
        else
            this.id = new SimpleIntegerProperty(0);
        this.service = new SimpleStringProperty(service);
        this.date = new SimpleObjectProperty<>(date);
        this.time = new SimpleObjectProperty<>(time);
        this.duration = new SimpleObjectProperty<>(duration);
        if(staffId!=null)
            this.staffId = new SimpleIntegerProperty(staffId);
        else
            this.staffId = new SimpleIntegerProperty(0);
        if(clientId!=null)
            this.clientId = new SimpleIntegerProperty(clientId);
        else
            this.clientId = new SimpleIntegerProperty(0);
        cancellation=null;
        notice=null;
    }
    public Appointment(Integer id, String service, LocalDate date, LocalTime time, LocalTime duration,Integer staffId, Integer clientId, Boolean cancellation) {
        if(id!=null)
            this.id = new SimpleIntegerProperty(id);
        else
            this.id = new SimpleIntegerProperty(0);
        this.service = new SimpleStringProperty(service);
        this.date = new SimpleObjectProperty<>(date);
        this.time = new SimpleObjectProperty<>(time);
        this.duration = new SimpleObjectProperty<>(duration);
        if(staffId!=null)
            this.staffId = new SimpleIntegerProperty(staffId);
        else
            this.staffId = new SimpleIntegerProperty(0);
        if(clientId!=null)
            this.clientId = new SimpleIntegerProperty(clientId);
        else
            this.clientId = new SimpleIntegerProperty(0);
        if(cancellation!=null)
            this.cancellation = cancellation;
        else
            this.cancellation = null;
        this.notice=null;
    }
    public Appointment(Integer id, String service, LocalDate date, LocalTime time, LocalTime duration,Integer staffId, Integer clientId, Boolean cancellation, Boolean notice) {
        if(id!=null)
            this.id = new SimpleIntegerProperty(id);
        else
            this.id = new SimpleIntegerProperty(0);
        this.service = new SimpleStringProperty(service);
        this.date = new SimpleObjectProperty<>(date);
        this.time = new SimpleObjectProperty<>(time);
        this.duration = new SimpleObjectProperty<>(duration);
        if(staffId!=null)
            this.staffId = new SimpleIntegerProperty(staffId);
        else
            this.staffId = new SimpleIntegerProperty(0);
        if(clientId!=null)
            this.clientId = new SimpleIntegerProperty(clientId);
        else
            this.clientId = new SimpleIntegerProperty(0);
        if(cancellation!=null)
            this.cancellation = cancellation;
        else
            this.cancellation = null;
        if(notice!=null)
            this.notice = notice;
        else
            this.notice = null;
    }

    /**
     * Constructor to build an appointment passing all its attributes except the appointment id
     * @param service: service description string
     * @param date: day of the appointment (yyyy-mm-dd format)
     * @param time: appointment hour (?hh-mm format?)
     * @param staffId: id of corresponding staff member
     * @param clientId: id of corresponding client
     */
    public Appointment(String service, LocalDate date, LocalTime time, LocalTime duration, Integer staffId, Integer clientId) {
        this.service = new SimpleStringProperty(service);
        this.date = new SimpleObjectProperty<>(date);
        this.time = new SimpleObjectProperty<>(time);
        this.duration = new SimpleObjectProperty<>(duration);
        if(staffId!=null)
            this.staffId = new SimpleIntegerProperty(staffId);
        else
            this.staffId = new SimpleIntegerProperty(0);
        if(clientId!=null)
            this.clientId = new SimpleIntegerProperty(clientId);
        else
            this.clientId = new SimpleIntegerProperty(0);
        cancellation=null;
        notice=null;
    }

    //getters and setters

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getService() {
        return service.get();
    }

    public StringProperty serviceProperty() {
        return service;
    }

    public void setService(String service) {
        this.service.set(service);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public LocalTime getTime() {
        return time.get();
    }

    public ObjectProperty<LocalTime> timeProperty() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time.set(time);
    }

    public LocalTime getDuration() {
        return duration.get();
    }
    public ObjectProperty<LocalTime> durationProperty() {
        return duration;
    }
    public void setDuration(LocalTime duration) {
        this.duration.set(duration);
    }

    public int getStaffId() {
        return staffId.get();
    }

    public IntegerProperty staffIdProperty() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId.set(staffId);
    }

    public int getClientId() {
        return clientId.get();
    }

    public IntegerProperty clientIdProperty() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId.set(clientId);
    }

    public boolean verifyAppointment(Appointment a){
        if (a.getService()==null) {
            return false;
        }
        else
            return true;
    }
    @Override
    public String toString() {
        return
                "id: "+id.get()+"\n"
                +"service: "+service.get()+"\n"
                +"date: "+date.get()+"\n"
                +"time: "+time.get()+"\n"
                +"staffId: "+staffId.get()+"\n"
                +"clientId: "+clientId.get()+"\n"
                +"cancellation: "+cancellation+"\n"
                +"notice: "+notice+"\n";
    }

}

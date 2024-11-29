package it.unicas.clinic.address.model;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {
    private  IntegerProperty id;
    private  ObjectProperty<LocalDate> day;
    private  ObjectProperty<LocalTime> startTime;
    private  ObjectProperty<LocalTime> stopTime;
    private  IntegerProperty staffId;


    public Schedule(int id, LocalDate day, LocalTime startTime, LocalTime stopTime, int staffId) {
        this.id = new SimpleIntegerProperty(id);
        this.day = new SimpleObjectProperty<>(day);
        this.startTime = new SimpleObjectProperty<>(startTime);
        this.stopTime = new SimpleObjectProperty<>(stopTime);
        this.staffId = new SimpleIntegerProperty(staffId);
    }

    public Schedule(LocalDate day, int staffId) {
        this.id = new SimpleIntegerProperty(0);
        this.day = new SimpleObjectProperty<>(day);
        this.staffId = new SimpleIntegerProperty(staffId);
        this.startTime = new SimpleObjectProperty<>(null); // Start time not set
        this.stopTime = new SimpleObjectProperty<>(null); // Stop time not set
    }
    public Schedule(LocalDate day, LocalTime startTime, LocalTime stopTime, int staffId) {
        this.day = new SimpleObjectProperty<>(day);
        this.startTime = new SimpleObjectProperty<>(startTime);
        this.stopTime = new SimpleObjectProperty<>(stopTime);
        this.staffId = new SimpleIntegerProperty(staffId);
        //this.id = new SimpleIntegerProperty(-1);

    }


    @Override
    public String toString() {
        return
                "id=" + idProperty() +
                ", day=" + day.get() +
                ", startTime=" + startTime.get() +
                ", stopTime=" + stopTime.get() +
                ", staffId=" + staffId.get() +
                +'\n';
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public ObjectProperty<LocalDate> dayProperty() {
        return day;
    }

    public LocalDate getDay() {
        return day.get();
    }

    public void setDay(LocalDate day) {
        this.day.set(day);
    }

    public ObjectProperty<LocalTime> startTimeProperty() {
        return startTime;
    }

    public LocalTime getStartTime() {
        return startTime.get();
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime.set(startTime);
    }

    public ObjectProperty<LocalTime> stopTimeProperty() {
        return stopTime;
    }

    public LocalTime getStopTime() {
        return stopTime.get();
    }

    public void setStopTime(LocalTime stopTime) {
        this.stopTime.set(stopTime);
    }

    public IntegerProperty staffIdProperty() {
        return staffId;
    }

    public int getStaffId() {
        return staffId.get();
    }

    public void setStaffId(int staffId) {
        this.staffId.set(staffId);
    }
}

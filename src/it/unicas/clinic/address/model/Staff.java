package it.unicas.clinic.address.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for Staff
 */
public class Staff {
    //the same field of the table Staff in the db
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty surname;
    private StringProperty specialties;
    private IntegerProperty work_hours;
    //Constructos
    public Staff(Integer id, String name, String surname, String specialties, Integer work_hours){
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.specialties = new SimpleStringProperty(specialties);
        if(work_hours > 0){
            this.work_hours = new SimpleIntegerProperty(work_hours);
        }
        else{
            this.work_hours = new SimpleIntegerProperty(0);
        }
        if(id != null){
            this.id = new SimpleIntegerProperty(id);
        }
        else{
            this.id = null;
        }

    }

    //gets and sets

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getSpecialties() {
        return specialties.get();
    }

    public StringProperty specialtiesProperty() {
        return specialties;
    }

    public void setSpecialties(String specialties) {
        this.specialties.set(specialties);
    }

    public int getWork_hours() {
        return work_hours.get();
    }

    public IntegerProperty work_hoursProperty() {
        return work_hours;
    }

    public void setWork_hours(int work_hours) {
        this.work_hours.set(work_hours);
    }

    //Override toString

    @Override
    public String toString() {
        return "Staff member \n" +
                id.getValue()+ ", " +
                name.getValue() + ", " +
                surname.getValue() + ", " +
                specialties.getValue() + ", " +
                work_hours.getValue() ;
    }
    public static void main(String[] args){

    }
}

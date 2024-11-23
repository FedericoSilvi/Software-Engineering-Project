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
    //Constructos
    public Staff(Integer id, String name, String surname, String specialties){
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.specialties = new SimpleStringProperty(specialties);
        if(id != null){
            this.id = new SimpleIntegerProperty(id);
        }
        else{
            this.id = new SimpleIntegerProperty(0);
        }
    }
    public Staff(String name, String surname, String specialties){
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.specialties = new SimpleStringProperty(specialties);
        //this.id= new SimpleIntegerProperty(0);

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


    //Override toString

    @Override
    public String toString() {
        return
                "id: "+ id.getValue()+ ", " +
                "name: " + name.getValue() + ", " +
                "surname: " +surname.getValue() + ", " +
                "specialities: " +specialties.getValue() + "\n";
    }
    public static void main(String[] args){

    }
}

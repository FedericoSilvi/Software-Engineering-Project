package it.unicas.clinic.address.model.dao;

import it.unicas.clinic.address.model.Appointment;

import java.sql.SQLException;
import java.util.List;

public interface AppointmentDAO <T>{
    List<T> select(T s) throws AppointmentException;
    void update(T s) throws AppointmentException;
    void insert(T s) throws AppointmentException;
    void delete(int s) throws AppointmentException;
    Appointment getLastApp() throws SQLException;
}

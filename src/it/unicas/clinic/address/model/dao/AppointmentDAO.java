package it.unicas.clinic.address.model.dao;

import it.unicas.clinic.address.model.Appointment;
import it.unicas.clinic.address.model.Schedule;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface AppointmentDAO <T>{
    List<T> select(T s) throws AppointmentException;
    void update(T s) throws AppointmentException;
    void insert(T s) throws AppointmentException;
    void delete(int s) throws AppointmentException;
    Appointment getLastApp() throws SQLException;

    //select app also in a period
    List<Appointment> select(Appointment s, LocalDate startDate, LocalDate endDate) throws AppointmentException;
    ArrayList<T> getSchedApp(Schedule s) throws SQLException;
    List<T> getHistoryApp(int client_id) throws SQLException;
}

package it.unicas.clinic.address.model.dao;

import java.util.List;

public interface AppointmentDAO <T>{
    List<T> select(T s) throws AppointmentException;
    void update(T s) throws AppointmentException;
    void insert(T s) throws AppointmentException;
    void delete(int s) throws AppointmentException;
}

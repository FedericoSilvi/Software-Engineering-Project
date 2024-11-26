package it.unicas.clinic.address.model.dao;

import it.unicas.clinic.address.model.Schedule;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleDAO <T>{
    List<T> select(T s) throws StaffException;
    void update(T s) throws StaffException;
    void insert(T s) throws StaffException;
    void delete(T s) throws StaffException;
}
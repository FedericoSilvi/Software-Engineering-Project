package it.unicas.clinic.address.model.dao;

import it.unicas.clinic.address.model.Schedule;
import it.unicas.clinic.address.model.Staff;

import java.sql.SQLException;
import java.util.List;

public interface StaffDAO <T>{
    List<T> select(T s);
    void update(T s) throws StaffException;
    void insert(T s) throws StaffException, SQLException;
    void delete(T s) throws StaffException;
    Staff getLastStaff() throws SQLException;
}

package it.unicas.clinic.address.model.dao.mysql;

import it.unicas.clinic.address.model.Staff;
import it.unicas.clinic.address.model.dao.StaffDAO;
import it.unicas.clinic.address.model.dao.StaffException;

import java.sql.*;
import java.util.logging.Logger;

public class StaffDAOMySQLImpl implements StaffDAO<Staff> {

    private static StaffDAO dao = null;
    private static Logger logger = null;

    private StaffDAOMySQLImpl(){}

    public static StaffDAO getInstance(){
        if (dao == null){
            dao = new StaffDAOMySQLImpl();
            logger = Logger.getLogger(StaffDAOMySQLImpl.class.getName());
        }
        return dao;
    }
    @Override
    public void update(Staff s) throws StaffException {
    }

    @Override
    public void insert(Staff s) throws StaffException, SQLException {
        //verify the object s
        verifyStaff(s);
        //create the query (String)
        Connection con = DAOMySQLSettings.getConnection();
        String sqlInsert = "INSERT INTO staff (name, surname, specialties, work_hours) VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(sqlInsert); //asking to prepare a statement
        preparedStatement.setString(1, s.getName());
        preparedStatement.setString(2, s.getSurname());
        preparedStatement.setString(3, s.getSpecialties());
        preparedStatement.setInt(4, s.getWork_hours());
        preparedStatement.executeUpdate();
        con.close();
    }

    @Override
    public void delete(Staff s) throws StaffException {
    }
    private void verifyStaff(Staff s){
        //we want all not null and with a "meaning"
        if(s == null || s.getName() == null || s.getSurname() == null
                || s.getSpecialties() == null || s.getWork_hours()<0){
            throw new StaffException("Can not to continue because that staff member has some null field");
        }

    }


    public static void main(String args[]) throws StaffException{
        Staff s = new Staff("Marco", "Caruso", "Nessuna", 0);

        // Usa il DAO per inserire il nuovo Staff nel database
        try {
            StaffDAOMySQLImpl.getInstance().insert(s);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

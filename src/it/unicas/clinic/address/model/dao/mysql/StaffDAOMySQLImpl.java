package it.unicas.clinic.address.model.dao.mysql;

import it.unicas.clinic.address.model.Staff;
import it.unicas.clinic.address.model.dao.StaffDAO;
import it.unicas.clinic.address.model.dao.StaffException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class StaffDAOMySQLImpl implements StaffDAO<Staff> {

    private static StaffDAO dao = null;
    private static Logger logger = null;

    private StaffDAOMySQLImpl(){}

    @Override
    public void update(Staff s) throws StaffException {
    }

    @Override
    public void insert(Staff s) throws StaffException {
        //verify the object s
        verifyStaff(s);
        //create the query (String)
        String sqlInsert = "INSERT INTO staff (name, surname, specialties, work_hours) VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = null;

        //call executeupdate
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

    /*private void executeUpdate(String sql) throws StaffException {
        try{
            Statement st = DAOMySQLSettings.getStatement();
            int n = st.executeUpdate(sql);
            DAOMySQLSettings.closeStatement(st);
        }catch(SQLException ex){
            throw new StaffException("In insert(): " + ex.getMessage());
        }
    }*/
}

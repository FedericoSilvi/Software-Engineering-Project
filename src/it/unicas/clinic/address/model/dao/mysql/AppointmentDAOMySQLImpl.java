package it.unicas.clinic.address.model.dao.mysql;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import it.unicas.clinic.address.model.Schedule;
import it.unicas.clinic.address.model.Staff;
import it.unicas.clinic.address.model.dao.AppointmentDAO;
import it.unicas.clinic.address.model.dao.AppointmentException;
import it.unicas.clinic.address.model.Appointment;
import it.unicas.clinic.address.model.dao.ScheduleException;
import it.unicas.clinic.address.model.dao.StaffDAO;


public class AppointmentDAOMySQLImpl implements AppointmentDAO<Appointment>{

    private static AppointmentDAO dao = null;
    private static Logger logger = null;

    private AppointmentDAOMySQLImpl(){}

    public static AppointmentDAO getInstance(){
        if (dao == null){
            dao = new AppointmentDAOMySQLImpl();
            logger = Logger.getLogger(AppointmentDAOMySQLImpl.class.getName());
        }
        return dao;
    }

    @Override
    public List<Appointment> select(Appointment s) throws AppointmentException {
        // If passed appointment is null create new one with null fields
        if(s==null)
            s = new Appointment(0,null,null,null,0,0);

        ArrayList<Appointment> list = new ArrayList<>();

        if(s.getId()<=0 && s.getService()==null && s.getDate()==null && s.getTime()==null & s.getStaffId()==0 && s.getClientId()==0){
            s= new Appointment(0,null,null,null,0,0);
        }
        String sqlSelect = "SELECT * FROM appointment WHERE 1=1";

        // Add dynamicly the condition of the fields not null
        if (s.getId() > 0) {
            sqlSelect += " AND id = ?";
        }
        if (s.getService() != null && !s.getService().isEmpty()) {
            sqlSelect += " AND service LIKE ?";
        }
        if (s.getDate() != null) {
            sqlSelect += " AND date LIKE ?";
        }
        if (s.getTime() != null) {
            sqlSelect += " AND time LIKE ?";
        }
        if(s.getStaffId() > 0){
            sqlSelect += " AND staff_id = ?";
        }
        if(s.getClientId() > 0){
            sqlSelect += " AND client_id = ?";
        }
        // Log final query
        logger.info("SQL Query: " + sqlSelect);

        // Prepare PreparedStatement
        try (Connection con = DAOMySQLSettings.getConnection();
             PreparedStatement stmt = con.prepareStatement(sqlSelect)) {

            // Set the params dinamicly
            int index = 1; // index of the parms of the preparedStatement

            if (s.getId() > 0) {
                stmt.setInt(index++, s.getId());  //set ID
            }
            if(s.getService()!=null && !s.getService().isEmpty()){
                stmt.setString(index++, s.getService());
            }
            if (s.getDate() != null) {
                stmt.setDate(index++, Date.valueOf(s.getDate()));  // set the day
            }
            if (s.getTime() != null) {
                stmt.setTime(index++, Time.valueOf(s.getTime()));  // set start time
            }
            if (s.getStaffId()>0) {
                stmt.setInt(index++, s.getStaffId());  // set final time
            }
            if (s.getClientId() > 0) {
                stmt.setInt(index++, s.getClientId());  // set staffId
            }

            // execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // create an object with the result
                    Appointment a1 = new Appointment(
                            rs.getInt("id"),
                            rs.getString("service"),
                            rs.getDate("date").toLocalDate(),
                            rs.getTime("time").toLocalTime(),
                            rs.getInt("staff_id"),
                            rs.getInt("client_id")
                    );
                    list.add(a1);  // add the object in the list
                }
            }

            if (!list.isEmpty()) {
                logger.info("Query executed successfully: " + sqlSelect + " | Number of records found: " + list.size());
            } else {
                logger.info("Query executed successfully: " + sqlSelect + " | No records found.");
            }
        } catch (SQLException e) {
            logger.severe("SQL Error: " + e.getMessage());
            throw new AppointmentException("SQL: In select(): An error occurred while fetching appointment data");
        }

        return list;
    }

    @Override
    public void update(Appointment s) throws AppointmentException {
        if(s!=null){
            try{
                Connection connection = DAOMySQLSettings.getConnection();
                String sqlUpdate = "update appointment set service=?, date=?, time=?, staff_id=?, client_id=? where id=?";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
                preparedStatement.setString(1, s.getService());
                preparedStatement.setDate(2, Date.valueOf(s.getDate()));
                preparedStatement.setTime(3, Time.valueOf(s.getTime()));
                preparedStatement.setInt(4, s.getStaffId());
                preparedStatement.setInt(5, s.getClientId());
                preparedStatement.setInt(6, s.getId());

                preparedStatement.executeUpdate();
                connection.close();
            }catch(SQLException e){
                logger.severe("Something went wrong while updating appointment");
            }

        }
        else
            logger.severe("Appointment in update is null");

    }

    @Override
    public void insert (Appointment s) throws AppointmentException {
        if(s!=null) {
            try {
                Connection connection = DAOMySQLSettings.getConnection();
                String sqlInsert = "insert into appointment (service, date, time, staff_id, client_id) VALUES (?,?,?,?,?)";
                PreparedStatement preparedstatement = connection.prepareStatement(sqlInsert);
                preparedstatement.setString(1, s.getService());
                preparedstatement.setDate(2, Date.valueOf(s.getDate()));
                preparedstatement.setTime(3, Time.valueOf(s.getTime()));
                preparedstatement.setInt(4, s.getStaffId());
                preparedstatement.setInt(5, s.getClientId());
                preparedstatement.execute();
                connection.close();
            } catch (SQLException e) {
                logger.severe("Something went wrong in inserting new appointment");
            }
        }
        else
            logger.severe("Inserted appointment is null");
    }

    @Override
    public void delete(int s) throws AppointmentException {
        if(s>0) {
            try {
                Connection connection = DAOMySQLSettings.getConnection();
                String sqlInsert = "delete from appointment where id=?";
                PreparedStatement preparedstatement = connection.prepareStatement(sqlInsert);
                preparedstatement.setInt(1, s);
                preparedstatement.executeUpdate();
                connection.close();
            } catch (SQLException e) {
                logger.severe("Something went wrong in deleting (selected id might not exist)");
            }
        }
        else
            logger.severe("Id is invalid");
    }
    public static void main(String[] args){
        dao = getInstance();
        //LocalDate date = LocalDate.of(2024,11,30);
        //L0ocalTime time = LocalTime.of(8,0);
        //Appointment a = new Appointment("service",date,time,1,1);
        //dao.insert(a);
        List<Appointment> list=dao.select(null);
        for(Appointment el:list){
            logger.info(el.toString());
        }
    }

}

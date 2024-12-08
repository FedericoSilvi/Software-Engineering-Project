package it.unicas.clinic.address.model.dao.mysql;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import it.unicas.clinic.address.model.dao.AppointmentDAO;
import it.unicas.clinic.address.model.dao.AppointmentException;
import it.unicas.clinic.address.model.Appointment;
import it.unicas.clinic.address.utils.DataUtil;


public class AppointmentDAOMySQLImpl implements AppointmentDAO<Appointment>{

    private static AppointmentDAO dao = null;
    private static Logger logger = null;

    public AppointmentDAOMySQLImpl(){}

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
            s = new Appointment(0,null,null, null,null,0,0);

        ArrayList<Appointment> list = new ArrayList<>();

        if(s.getId()<=0 && s.getService()==null && s.getDate()==null && s.getTime()==null & s.getStaffId()==0 && s.getClientId()==0){
            s= new Appointment(0,null,null,null,null,0,0);
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
                            rs.getTime("duration").toLocalTime(),
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
                String sqlInsert = "insert into appointment (service, date, time, duration, staff_id, client_id) VALUES (?,?,?,?,?,?)";
                PreparedStatement preparedstatement = connection.prepareStatement(sqlInsert);
                preparedstatement.setString(1, s.getService());
                preparedstatement.setDate(2, Date.valueOf(s.getDate()));
                preparedstatement.setTime(3, Time.valueOf(s.getTime()));
                preparedstatement.setTime(4, Time.valueOf(s.getDuration()));
                preparedstatement.setInt(5, s.getStaffId());
                preparedstatement.setInt(6, s.getClientId());
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

    public Appointment getLastApp() throws SQLException {
        Appointment a = new Appointment(0, null, null,null, null,0,0);
        Connection connection = DAOMySQLSettings.getConnection();
        //Define command
        String searchUser = "select * from appointment order by id desc limit 1";
        PreparedStatement command = connection.prepareStatement(searchUser);
        //Execute command
        ResultSet result = command.executeQuery();

        if(result.next()){
            a.setService(result.getString("service"));
            a.setDate(DataUtil.parseToDate(result.getString("date")));
            a.setTime(DataUtil.parseToTime(result.getString("time"),false));
            a.setDuration(DataUtil.parseToDuration(result.getString("duration"),false));
            a.setId(result.getInt("id"));
            a.setStaffId(result.getInt("staff_id"));
            a.setClientId(result.getInt("client_id"));
        }
        connection.close();
        return a;
    }

    public static boolean filterByDate(LocalDate date, int clientId, int staffId, String service) throws SQLException {
        Connection connection = DAOMySQLSettings.getConnection();
        String sqlSelect;
        PreparedStatement preparedStatement;
        if(clientId != 0){
            sqlSelect = "SELECT * FROM appointment WHERE date = ? AND client_id = ?";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setInt(2, clientId);
        } else if(staffId != 0){
            sqlSelect = "SELECT * FROM appointment WHERE date = ? AND staff_id = ?";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setInt(2, staffId);
        } else if(service != null){
            sqlSelect = "SELECT * FROM appointment WHERE date = ? AND service = ?";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setString(2, service);
        }
        else{
            sqlSelect = "select * from appointment where date = ?";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setDate(1, Date.valueOf(date));
        }

        ResultSet result = preparedStatement.executeQuery();

        boolean flag;

        if(result.next()){
            flag = true;
        }
        else {
            flag = false;
        }

        connection.close();

        return flag;

    }

   /* public static ArrayList<Appointment> searchAppointment(String client, String staff, String date) throws SQLException {
        Connection connection = DAOMySQLSettings.getConnection();
        ArrayList<Integer> clientId = new ArrayList<>();
        ArrayList<Integer> staffId = null;
        ArrayList<Appointment> appointments = null;
        int i = 0;
        int z = 0;
        boolean first = true;
        
        if(client != null || client.length() > 0){
            String sqlSelect1 = "SELECT id FROM client WHERE surname =?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlSelect1);
            preparedStatement.setString(1, client);
            ResultSet result = preparedStatement.executeQuery();
            i = 0;

            while(result.next()){
                if(i == 0){
                    clientId.add(result.getInt("id"));
                }
                else if(clientId.get(i) != result.getInt("id")){
                    i++;
                    clientId.add(result.getInt("id"));
                }
            }
        }
        
        if(staff != null || staff.length() > 0){
            String sqlSelect2 = "SELECT id FROM staff WHERE surname =?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sqlSelect2);
            preparedStatement2.setString(1, staff);
            ResultSet result2 = preparedStatement2.executeQuery();
            i = 0;

            while(result2.next()){
                if(i == 0){
                    staffId.add(result2.getInt("id"));
                } else if(staffId.get(i) != result2.getInt("id")){
                    staffId.add(result2.getInt("id"));
                }
            }
        }
        
        if(date != null || date.length() > 0)
        {
            String sqlSelect3 = "SELECT * FROM appointment WHERE client_id=? and staff_id=? and date=?";
            PreparedStatement preparedStatement3 = connection.prepareStatement(sqlSelect3);
            for(int j = 0; j < clientId.size(); j++){
                for(int k = 0; k < staffId.size(); k++){
                    preparedStatement3.setInt(1, clientId.get(j));
                    preparedStatement3.setInt(2, staffId.get(k));
                    preparedStatement3.setDate(3, Date.valueOf(date));
                    preparedStatement3.executeUpdate();

                    ResultSet result3 = preparedStatement3.executeQuery();
                    while(result3.next()){
                        if(first) {
                            first = false;
                            z++;

                            int appId = result3.getInt("id");
                            String service = result3.getString("service");
                            LocalDate dateTime = result3.getDate("date").toLocalDate();
                            LocalTime time = result3.getTime("time").toLocalTime();
                            LocalTime duration = result3.getTime("duration").toLocalTime();
                            Integer staff_id = result3.getInt("staff_id");
                            Integer client_id = result3.getInt("client_id");

                            Appointment appointment = new Appointment(appId, service, dateTime, time, duration, staff_id, client_id);

                            appointments.add(appointment);
                        }
                        else if(appointments.get(z).getId() != result3.getInt("id")){
                            z++;

                            int appId = result3.getInt("id");
                            String service = result3.getString("service");
                            LocalDate dateTime = result3.getDate("date").toLocalDate();
                            LocalTime time = result3.getTime("time").toLocalTime();
                            LocalTime duration = result3.getTime("duration").toLocalTime();
                            Integer staff_id = result3.getInt("staff_id");
                            Integer client_id = result3.getInt("client_id");

                            Appointment appointment = new Appointment(appId, service, dateTime, time, duration, staff_id, client_id);

                            appointments.add(appointment);
                        }
                    }
                }
            }
        }
        return appointments;
    }*/

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

package it.unicas.clinic.address.model.dao.mysql;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import it.unicas.clinic.address.model.Schedule;
import it.unicas.clinic.address.model.dao.AppointmentDAO;
import it.unicas.clinic.address.model.dao.AppointmentException;
import it.unicas.clinic.address.model.Appointment;
import it.unicas.clinic.address.utils.DataUtil;


public class AppointmentDAOMySQLImpl implements AppointmentDAO<Appointment>{

    private static AppointmentDAO dao = null;
    private static Logger logger = null;

    public AppointmentDAOMySQLImpl() {}

    public static AppointmentDAO getInstance() {
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
        String sqlSelect = "SELECT * FROM appointment WHERE cancellation IS NULL";

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
                stmt.setString(index++, s.getService()+"%");
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
                stmt.setInt(index, s.getClientId());  // set staffId
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
        System.out.println("Lista: "+list);
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
    public void solftDelete(int s) throws AppointmentException {
        if(s>0) {
            try {
                Connection connection = DAOMySQLSettings.getConnection();
                //String sqlInsert = "delete from appointment where id=?";
                String sqlInsert = "UPDATE appointment SET cancellation = 1 WHERE id = ?";
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

    @Override
    public void delete(int s) throws AppointmentException {
        if(s>0) {
            try {
                Connection connection = DAOMySQLSettings.getConnection();
                //String sqlInsert = "delete from appointment where id=?";
                String sqlInsert = "DELETE FROM appointment WHERE id = ?";
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

    public ArrayList<Appointment> getSchedApp(Schedule s) throws SQLException {
        ArrayList<Appointment> appointments = new ArrayList<>();
        Connection connection = DAOMySQLSettings.getConnection();
        //Define command
        String searchApp="select * from appointment where staff_id=? and date=?";
        PreparedStatement preparedStatement = connection.prepareStatement(searchApp);
        preparedStatement.setInt(1, s.getStaffId());
        preparedStatement.setDate(2,Date.valueOf(s.getDay()));
        ResultSet result = preparedStatement.executeQuery();
        while(result.next()){
            // create an object with the result
            Appointment a1 = new Appointment(
                    result.getInt("id"),
                    result.getString("service"),
                    result.getDate("date").toLocalDate(),
                    result.getTime("time").toLocalTime(),
                    result.getTime("duration").toLocalTime(),
                    result.getInt("staff_id"),
                    result.getInt("client_id")
            );
            appointments.add(a1);  // add the object in the list
        }
        connection.close();
        System.out.println(appointments);
        System.out.println("\n");
        if(appointments.isEmpty())
            return null;
        else
            return appointments;
    }

    public List<Appointment> getHistoryApp(int client_id) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        if(client_id<=0)
            return null;
        Connection connection = DAOMySQLSettings.getConnection();
        String searchApp="select * from appointment where client_id=? and date<=?";
        PreparedStatement preparedStatement = connection.prepareStatement(searchApp);
        preparedStatement.setInt(1, client_id);
        preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
        ResultSet result = preparedStatement.executeQuery();
        while(result.next()){
            Appointment a = new Appointment(
                    result.getInt("id"),
                    result.getString("service"),
                    result.getDate("date").toLocalDate(),
                    result.getTime("time").toLocalTime(),
                    result.getTime("duration").toLocalTime(),
                    result.getInt("staff_id"),
                    result.getInt("client_id")
            );
            appointments.add(a);
        }
        if(appointments.isEmpty())
            return null;
        else
            return appointments;
    }

    @Override
    public List<Appointment> select(Appointment s, LocalDate startDate, LocalDate endDate) throws AppointmentException {
        // If passed appointment is null, create a new one with null fields
        if (s == null)
            s = new Appointment(0, null, null, null, null, 0, 0);

        ArrayList<Appointment> list = new ArrayList<>();

        if (s.getId() <= 0 && s.getService() == null && s.getDate() == null && s.getTime() == null && s.getStaffId() == 0 && s.getClientId() == 0) {
            s = new Appointment(0, null, null, null, null, 0, 0);
        }

        String sqlSelect = "SELECT * FROM appointment WHERE 1=1";

        // Add dynamically the condition of the fields not null
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
        if (s.getStaffId() > 0) {
            sqlSelect += " AND staff_id = ?";
        }
        if (s.getClientId() > 0) {
            sqlSelect += " AND client_id = ?";
        }
        if (startDate != null) {
            sqlSelect += " AND date >= ?";
        }
        if (endDate != null) {
            sqlSelect += " AND date <= ?";
        }

        // Log final query
        logger.info("SQL Query: " + sqlSelect);

        // Prepare PreparedStatement
        try (Connection con = DAOMySQLSettings.getConnection();
             PreparedStatement stmt = con.prepareStatement(sqlSelect)) {

            // Set the params dynamically
            int index = 1; // index of the params of the preparedStatement

            if (s.getId() > 0) {
                stmt.setInt(index++, s.getId()); // set ID
            }
            if (s.getService() != null && !s.getService().isEmpty()) {
                stmt.setString(index++, s.getService());
            }
            if (s.getDate() != null) {
                stmt.setDate(index++, Date.valueOf(s.getDate())); // set the day
            }
            if (s.getTime() != null) {
                stmt.setTime(index++, Time.valueOf(s.getTime())); // set start time
            }
            if (s.getStaffId() > 0) {
                stmt.setInt(index++, s.getStaffId()); // set staffId
            }
            if (s.getClientId() > 0) {
                stmt.setInt(index++, s.getClientId()); // set clientId
            }
            if (startDate != null) {
                stmt.setDate(index++, Date.valueOf(startDate)); // set startDate
            }
            if (endDate != null) {
                stmt.setDate(index++, Date.valueOf(endDate)); // set endDate
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
                            rs.getInt("client_id"),
                            rs.getBoolean("cancellation")
                    );
                    //System.out.println(a1);
                    list.add(a1); // add the object in the list
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

    //restituisce gli appuntamenti più vecchi di 10 anni dalla data d
    @Override
    public List<Appointment> getPastApp(LocalDate d) throws RuntimeException{
        String query = "SELECT * FROM appointment WHERE  date <= ?";
        List<Appointment> appList = new ArrayList<>();
        try (Connection con = DAOMySQLSettings.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setDate(1, java.sql.Date.valueOf(d));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Appointment a = new Appointment(
                            rs.getInt("id"),
                            rs.getString("service"),
                            rs.getDate("date").toLocalDate(),
                            rs.getTime("time").toLocalTime(),
                            rs.getTime("duration").toLocalTime(),
                            rs.getInt("staff_id"),
                            rs.getInt("client_id"),
                            rs.getBoolean("cancellation")
                    );
                    appList.add(a);
                }
            }
        } catch (SQLException e) {
            logger.severe("Error selecting old appointment: " + e.getMessage());
            throw new RuntimeException("Error selecting old appointment", e);
        }
        return appList;

    }
    @Override
    public List<Appointment> getfutureAppStaff(LocalDate d, int id) throws RuntimeException{
        String query = "SELECT * FROM appointment WHERE  date >= ? AND staff_id=?";
        List<Appointment> appList = new ArrayList<>();
        try (Connection con = DAOMySQLSettings.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setDate(1, java.sql.Date.valueOf(d));
            stmt.setInt(2, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Appointment a = new Appointment(
                            rs.getInt("id"),
                            rs.getString("service"),
                            rs.getDate("date").toLocalDate(),
                            rs.getTime("time").toLocalTime(),
                            rs.getTime("duration").toLocalTime(),
                            rs.getInt("staff_id"),
                            rs.getInt("client_id"),
                            rs.getBoolean("cancellation")
                    );
                    appList.add(a);
                }
            }
        } catch (SQLException e) {
            logger.severe("Error selecting old appointment: " + e.getMessage());
            throw new RuntimeException("Error selecting old appointment", e);
        }
        return appList;

    }

    @Override
    public List<Appointment> getfutureAppClient(LocalDate d, int id) throws RuntimeException{
        String query = "SELECT * FROM appointment WHERE  date >= ? AND client_id=?";
        List<Appointment> appList = new ArrayList<>();
        try (Connection con = DAOMySQLSettings.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setDate(1, java.sql.Date.valueOf(d));
            stmt.setInt(2, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Appointment a = new Appointment(
                            rs.getInt("id"),
                            rs.getString("service"),
                            rs.getDate("date").toLocalDate(),
                            rs.getTime("time").toLocalTime(),
                            rs.getTime("duration").toLocalTime(),
                            rs.getInt("staff_id"),
                            rs.getInt("client_id"),
                            rs.getBoolean("cancellation")
                    );
                    appList.add(a);
                }
            }
        } catch (SQLException e) {
            logger.severe("Error selecting future appointment: " + e.getMessage());
            throw new RuntimeException("Error selecting future appointment", e);
        }
        return appList;

    }


    public static boolean filterByDate(LocalDate date, int clientId, int staffId, String service) throws SQLException {
        Connection connection = DAOMySQLSettings.getConnection();
        String sqlSelect;
        PreparedStatement preparedStatement;
        if(clientId != 0){
            sqlSelect = "SELECT * FROM appointment WHERE date = ? AND client_id = ? AND cancellation IS NULL";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setInt(2, clientId);
        } else if(staffId != 0){
            sqlSelect = "SELECT * FROM appointment WHERE date = ? AND staff_id = ? AND cancellation IS NULL";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setInt(2, staffId);
        } else if(service != null){
            sqlSelect = "SELECT * FROM appointment WHERE date = ? AND service = ? AND cancellation IS NULL";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setString(2, service);
        }
        else{
            sqlSelect = "select * from appointment where date = ? AND cancellation IS NULL";
            preparedStatement = connection.prepareStatement(sqlSelect);
            preparedStatement.setDate(1, Date.valueOf(date));
        }

        ResultSet result = preparedStatement.executeQuery();

        boolean flag;
        System.out.println(result);
        if(result.next()){
            flag = true;
        }
        else {
            flag = false;
        }

        connection.close();

        return flag;

    }

    public static void main(String[] args) throws SQLException {
        /*dao=new AppointmentDAOMySQLImpl();
        List<Appointment> a = dao.getHistoryApp();
        System.out.println(a);*/
    }

}

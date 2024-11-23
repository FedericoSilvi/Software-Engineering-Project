package it.unicas.clinic.address.model.dao.mysql;

import it.unicas.clinic.address.model.Schedule;
import it.unicas.clinic.address.model.Staff;
import it.unicas.clinic.address.model.dao.ScheduleException;
import it.unicas.clinic.address.model.dao.StaffDAO;
import it.unicas.clinic.address.model.dao.StaffException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
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
        verifyStaff(s);
        String sqlUpdate = "UPDATE staff SET name = ?, surname = ?, specialties = ? WHERE id = ?";
        try (Connection con = DAOMySQLSettings.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sqlUpdate)) {
            preparedStatement.setString(1, s.getName());
            preparedStatement.setString(2, s.getSurname());
            preparedStatement.setString(3, s.getSpecialties());
            preparedStatement.setInt(4, s.getId());

            int rowAffected=preparedStatement.executeUpdate();
            logger.info("Query executed successfully: " + sqlUpdate);
            if(rowAffected==0){
                logger.info("No staff found with id " + s.getId());
            }
        } catch (SQLException e) {
            logger.severe(("SQL: In update(): An error occurred while updating staff data"));
            throw new StaffException("SQL: In update(): An error occurred while updating staff data");
        }
    }

    @Override
    public void insert(Staff s) throws StaffException {
        // Verifica l'oggetto Staff
        verifyStaff(s);

        // Creiamo la query per l'inserimento dello Staff
        String sqlInsertStaff = "INSERT INTO staff (name, surname, specialties) VALUES(?, ?, ?)";

        try (Connection con = DAOMySQLSettings.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sqlInsertStaff, Statement.RETURN_GENERATED_KEYS)) {

            // Inserisci i dati dello staff
            preparedStatement.setString(1, s.getName());
            preparedStatement.setString(2, s.getSurname());
            preparedStatement.setString(3, s.getSpecialties());

            preparedStatement.executeUpdate();  // Esegui l'aggiornamento per inserire lo staff


            logger.info("Query executed successfully: " + sqlInsertStaff);

        } catch (SQLException e) {
            logger.severe("SQL: In insert(): An error occurred while inserting staff data, connection problem");
            throw new StaffException("SQL: In insert(): An error occurred while inserting staff data, connection problem");
        }
    }


    @Override
    public void delete(Staff s) throws StaffException {
        if(s == null || s.getId() <= 0){
            throw new StaffException("SQL: In delete(): Staff object cannot be null or with an invalid id ");
        }
        String sqlDelete = "DELETE FROM staff WHERE id = ? ";
        try (Connection con = DAOMySQLSettings.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sqlDelete)) {
            preparedStatement.setInt(1, s.getId());
            int rowAffected=preparedStatement.executeUpdate();
            logger.info("Query executed successfully: " + sqlDelete);
            if(rowAffected==0){
                logger.info("No staff found with id " + s.getId());
            }
        }catch(SQLException e){
            logger.severe(("SQL: In delete(): An error occurred while deleting staff data"));
            throw new StaffException("SQL: In delete(): An error occurred while deleting staff data");
        }
    }

    @Override
    public List<Staff> select(Staff s) {
        // if the object is null, create a Stuff with default value (0, null, null, null, null)
        if (s == null) {
            s = new Staff(0, "", "", ""); //select all
        }

        ArrayList<Staff> list = new ArrayList<>();

        // If all the fields are null o 0 => select all
        if (s.getId() <= 0 && s.getName() == null && s.getSurname() == null && s.getSpecialties() == null) {
            s = new Staff(0, null, null, null);
        }

        String sqlSelect = "SELECT * FROM staff WHERE 1=1";

        // Add dynamicly the condition of the fields not null
        if (s.getId() > 0) {
            sqlSelect += " AND id = ?";
        }
        if (s.getName() != null && !s.getName().isEmpty()) {
            sqlSelect += " AND name LIKE ?";
        }
        if (s.getSurname() != null && !s.getSurname().isEmpty()) {
            sqlSelect += " AND surname LIKE ?";
        }
        if (s.getSpecialties() != null && !s.getSpecialties().isEmpty()) {
            sqlSelect += " AND specialties LIKE ?";
        }

        // Log final query
        logger.info("SQL Query: " + sqlSelect);

        // Prepare PreparedStatement
        try (Connection con = DAOMySQLSettings.getConnection();
             PreparedStatement stmt = con.prepareStatement(sqlSelect)) {

            // Set the params dinamicly
            int index = 1; // index of the parms of the preparedStatement

            if (s.getId() > 0) {
                stmt.setInt(index++, s.getId());  //set l'ID
            }
            if (s.getName() != null && !s.getName().isEmpty()) {
                stmt.setString(index++, "%" + s.getName() + "%");  // set name
            }
            if (s.getSurname() != null && !s.getSurname().isEmpty()) {
                stmt.setString(index++, "%" + s.getSurname() + "%");  // set surname
            }
            if (s.getSpecialties() != null && !s.getSpecialties().isEmpty()) {
                stmt.setString(index++, "%" + s.getSpecialties() + "%");  // set speciality
            }

            //  execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // create an object with the result
                    Staff s1 = new Staff(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("specialties")
                    );
                    list.add(s1);  // add the object in the list
                }
            }

            if (!list.isEmpty()) {
                logger.info("Query executed successfully: " + sqlSelect + " | Number of records found: " + list.size());
            } else {
                logger.info("Query executed successfully: " + sqlSelect + " | No records found.");
            }
        } catch (SQLException e) {
            logger.severe("SQL Error: " + e.getMessage());
            throw new StaffException("SQL: In select(): An error occurred while fetching staff data");
        }

        return list;  // Ritorna la lista di risultati
    }





    private void verifyStaff(Staff s) throws StaffException {
        //we want all not null and with a "meaning"
            if (s == null || s.getName() == null || s.getSurname() == null
                    || s.getSpecialties() == null) {
                throw new StaffException("Can not to continue because that staff member has some null field");
            }
    }


    public static void main(String args[]) throws StaffException, SQLException{
        dao=StaffDAOMySQLImpl.getInstance();
        Staff newStaff = new Staff("John", "Doe", "Dermatology");

        // Crea una lista di orari di lavoro
        //List<Schedule> scheduleList = new ArrayList<>();
        //scheduleList.add(new Schedule(1, LocalDate.of(2024, 11, 24), LocalTime.of(9, 0), LocalTime.of(17, 0), 0));
        //scheduleList.add(new Schedule(2, LocalDate.of(2024, 11, 25), LocalTime.of(9, 0), LocalTime.of(17, 0), 0));

        //dao.insert(new Staff("Marco", "Caruso", "Nessuna"));
        //dao.insert(new Staff("Federico", "Silvi", "massaggi"));
        //List<Staff> selectStuffMassage = dao.select(new Staff(null, "F", null, null));
        //System.out.println(selectStuffMassage);
        List<Staff> selectAll = dao.select(new Staff(0, null, null, null));
        System.out.println(selectAll);
    }

}

package it.unicas.clinic.address.model.dao.mysql;

import it.unicas.clinic.address.model.dao.mysql.DAOMySQLSettings;
import it.unicas.clinic.address.utils.DataUtil.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Contain the operations regarding the login operations with SQL database
 */

public class LoginDAOImplementation {

    private String username;
    private String password;

    /**
     * Constructor with passed arguments
     * @param us: username
     * @param pass: password
     */
    public LoginDAOImplementation(String us, String pass) {
        this.username = us;
        this.password = pass;
    }

    /**
     * Getter of username
     */
    public String getUsername() {
        return username;
    }
    /**
     * Getter of username
     * @param username: wanted username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Getter of password
     */
    public String getPassword() {
        return password;
    }
    /**
     * Setter of password
     * @param password: wanted password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Search for the credentials contained in the Class. If found, search for the
     * corresponding user and return it, else return null
     * @throws SQLException: cannot execute the wanted queries
     */
    public User searchUser() throws SQLException {
        //Open connection
        Connection connection = DAOMySQLSettings.getConnection();
        //Define command
        String searchUser = "select * from credential where username=? and password=?";
        PreparedStatement command = connection.prepareStatement(searchUser);
        command.setString(1,this.username);
        command.setString(2,this.password);
        //Execute command
        ResultSet result = command.executeQuery();
        boolean isManager = false;
        if(result.next()){  //If command has found something
            int staff_id=0;
            //Save the info about user being manager or not
            isManager = result.getBoolean("owner");
            //Get the staff id from credential table
            staff_id = result.getInt("staff_id");
            //Command for searching in staff table
            String staffSearch = "select * from staff where id=?";
            PreparedStatement staff = connection.prepareStatement(staffSearch);
            staff.setString(1,Integer.toString(staff_id));
            //Execute command
            ResultSet staff_data = staff.executeQuery();
            //Define user to store staff member if found
            User user = new User();
            while(staff_data.next()) {
                //Save staff member infos
                user.setName(staff_data.getString("name"));
                user.setSurname(staff_data.getString("surname"));
                user.setManager(isManager);
                user.setId(staff_id);
            }
            //Close connection
            DAOMySQLSettings.closeConnection(connection);
            return user;
        }

        else {  // If command hasn't found anything
            //Close connection
            DAOMySQLSettings.closeConnection(connection);
            return null;
        }
    }

}

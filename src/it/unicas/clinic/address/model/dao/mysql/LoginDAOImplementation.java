package it.unicas.clinic.address.model.dao.mysql;

import it.unicas.clinic.address.model.dao.mysql.DAOMySQLSettings;
import it.unicas.clinic.address.utils.DataUtil.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDAOImplementation {

    private String username;
    private String password;

    public LoginDAOImplementation(String us, String pass) {
        this.username = us;
        this.password = pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public User searchUser() throws SQLException {
        Connection connection = DAOMySQLSettings.getConnection();
        String searchUser = "select * from credential where username=? and password=?";
        PreparedStatement command = connection.prepareStatement(searchUser);
        command.setString(1,this.username);
        command.setString(2,this.password);
        ResultSet result = command.executeQuery();
        boolean isManager = false;
        if(result.next()){
            String staff_id="";
            isManager = result.getBoolean("owner");
            staff_id = result.getString("staff_id");
            String staffSearch = "select * from staff where id=?";
            PreparedStatement staff = connection.prepareStatement(staffSearch);
            staff.setString(1,staff_id);
            ResultSet staff_data = staff.executeQuery();
            User user = new User();
            while(staff_data.next()) {
                user.setName(staff_data.getString("name"));
                user.setSurname(staff_data.getString("surname"));
                user.setManager(isManager);
            }
            DAOMySQLSettings.closeConnection(connection);
            return user;
        }

        else {
            DAOMySQLSettings.closeConnection(connection);
            return null;
        }
    }

}

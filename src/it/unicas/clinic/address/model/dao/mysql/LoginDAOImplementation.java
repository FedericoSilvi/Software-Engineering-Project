package it.unicas.clinic.address.model.dao.mysql;

import it.unicas.clinic.address.model.dao.mysql.DAOMySQLSettings;

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
    public List<String> searchUser() throws SQLException {
        Connection connection = DAOMySQLSettings.getConnection();
        String searchUser = "select * from credential where username=? and password=?";
        PreparedStatement command = connection.prepareStatement(searchUser);
        command.setString(1,this.username);
        command.setString(2,this.password);
        ResultSet result = command.executeQuery();

        if(result!=null){
            String staff_id="";
            while(result.next()) {
                 staff_id = result.getString("staff_id");
            }
            String staffSearch = "select * from staff where id=?";
            PreparedStatement staff = connection.prepareStatement(staffSearch);
            staff.setString(1,staff_id);
            ResultSet staff_data = staff.executeQuery();

            List<String> list = new ArrayList<>();
            while(staff_data.next()) {
                list.add(staff_data.getString("name"));
                list.add(staff_data.getString("surname"));
            }
            DAOMySQLSettings.closeConnection(connection);
            return list;
        }

        else {
            DAOMySQLSettings.closeConnection(connection);
            return null;
        }
    }

}

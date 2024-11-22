package it.unicas.clinic.address.model.dao.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;


public class DAOMySQLSettings {
    public final static String DRIVERNAME = "com.mysql.cj.jdbc.Driver";
    public final static String HOST = "localhost";
    public final static String USERNAME = "staff_manager";
    public final static String PWD = "PasswordBella123";
    public final static String SCHEMA = "clinic";
    public final static String PARAMETERS = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";


    //String url = "jdbc:mysql://localhost:3306/amici?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";


    //private String driverName = "com.mysql.cj.jdbc.Driver";
    private String host = "localhost";
    private String userName = "staff_manager";
    private String pwd = "PasswordBella123";
    private String schema = "clinic";

    public String getHost() {
        return host;
    }

    public String getUserName() {
        return userName;
    }

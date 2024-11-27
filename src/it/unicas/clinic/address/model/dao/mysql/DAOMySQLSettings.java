package it.unicas.clinic.address.model.dao.mysql;

import it.unicas.clinic.address.model.Client;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOMySQLSettings {
    public static final String URL ="jdbc:mysql://localhost:3306/clinic?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    public static final String USERNAME = "staff";
    public static final String PASSWORD = "staffPassword!";

    public static void check() throws SQLException {

        // VERIFICA ACCESSO AL DATABASE

        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String sqlView = "SELECT * FROM clinic.client";
        PreparedStatement preparedstatement = connection.prepareStatement(sqlView);
        ResultSet resultset = preparedstatement.executeQuery();
        while (resultset.next()) {
            System.out.println("ID: " + resultset.getString("id"));
            System.out.println("Name: " + resultset.getString("name"));
            System.out.println("Surname: " + resultset.getString("surname"));
            System.out.println("E-mail: " + resultset.getString("email"));
            System.out.println("Phone number: " + resultset.getString("number"));
        }

        connection.close();
    }

    public static void insert(String name, String surname, String email, String number) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String sqlInsert = "INSERT INTO clinic.client (name, surname, email, number) VALUES (?,?,?,?)";
        PreparedStatement preparedstatement = connection.prepareStatement(sqlInsert);
        preparedstatement.setString(1, name);
        preparedstatement.setString(2, surname);
        preparedstatement.setString(3, email);
        preparedstatement.setString(4, number);
        preparedstatement.execute();
        connection.close();
    }
    public static void delete(int id) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String sqlDelete = "DELETE FROM clinic.client WHERE id = ?";
        PreparedStatement preparedstatement = connection.prepareStatement(sqlDelete);
        preparedstatement.setInt(1, id);
        preparedstatement.execute();
        connection.close();
    }

    public static void update(int id, String name, String surname, String email, String number) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String sqlUpdate = "UPDATE clinic.client SET name=?, surname=?, email=?, number=? WHERE id = ?";
        PreparedStatement preparedstatment = connection.prepareStatement(sqlUpdate);
        preparedstatment.setString(1, name);
        preparedstatment.setString(2, surname);
        preparedstatment.setString(3, email);
        preparedstatment.setString(4, number);
        preparedstatment.setInt(5, id);
        preparedstatment.execute();
        connection.close();
    }

    // TEMPORANEO: DA SOSTITUIRE CON FILTER CLIENT
    public static Client getClient(int id) throws SQLException {
        Client client = null;
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String sqlSelect = "SELECT * FROM clinic.client WHERE id = ?";
        PreparedStatement preparedstatement = connection.prepareStatement(sqlSelect);
        preparedstatement.setInt(1, id);
        ResultSet resultset = preparedstatement.executeQuery();
        if (resultset.next()) {
            client = new Client(id, resultset.getString("name"), resultset.getString("surname"), resultset.getString("email"), resultset.getString("number"));
        }
        return client;
    }

    public static ArrayList<Client> filterClient(String name, String surname, String email) throws SQLException {
        ArrayList<Client> clients = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String sqlSelect = "SELECT * FROM clinic.client WHERE name = ? OR surname = ? OR email = ?";
        PreparedStatement preparedstatement = connection.prepareStatement(sqlSelect);
        preparedstatement.setString(1, name);
        preparedstatement.setString(2, surname);
        preparedstatement.setString(3, email);
        ResultSet resultset = preparedstatement.executeQuery();

        while(resultset.next()) {
            int id = resultset.getInt("id");
            String n = resultset.getString("name");
            String s = resultset.getString("surname");
            String e = resultset.getString("email");
            String p = resultset.getString("number");
            Client client = new Client(id, n, s, e, p);
            clients.add(client);
        }

        connection.close();

        return clients;
    }

    public static ArrayList<Client> getClientsList() throws SQLException {

        ArrayList<Client> list = new ArrayList<>();

        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String sqlSelect = "SELECT * FROM clinic.client ORDER BY id ASC";
        PreparedStatement preparedstatement = connection.prepareStatement(sqlSelect);
        ResultSet resultset = preparedstatement.executeQuery();
        while (resultset.next()) {
            int id = resultset.getInt("id");
            String name = resultset.getString("name");
            String surname = resultset.getString("surname");
            String email = resultset.getString("email");
            String number = resultset.getString("number");

            Client temp = new Client(id, name, surname, email, number);
            list.add(temp);
        }

        connection.close();


        return list;
    }

    public static void checkSchedule() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        String sqlSelect = "SELECT * FROM clinic.appointment";
        PreparedStatement preparedstatement = connection.prepareStatement(sqlSelect);
        ResultSet resultset = preparedstatement.executeQuery();
        while (resultset.next()) {
            System.out.println("ID: " + resultset.getString("id"));
            System.out.println("Service: " + resultset.getString("service"));
            System.out.println("Date: " + resultset.getString("date"));
            System.out.println("Time: " + resultset.getString("time"));
            System.out.println("Staff ID:  " + resultset.getString("staff_id"));
            System.out.println("Client ID:  " + resultset.getString("client_id"));


        }

    }
}


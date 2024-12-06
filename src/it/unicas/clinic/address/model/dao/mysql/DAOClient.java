package it.unicas.clinic.address.model.dao.mysql;

import it.unicas.clinic.address.model.Client;
import it.unicas.clinic.address.model.Staff;
import it.unicas.clinic.address.model.dao.StaffException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DAOClient {

    public static void check() throws SQLException {

        // VERIFICA ACCESSO AL DATABASE

        Connection connection = DAOMySQLSettings.getConnection();
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
        Connection connection = DAOMySQLSettings.getConnection();
        String sqlInsert = "INSERT INTO clinic.client (name, surname, email, number) VALUES (?,?,?,?)";
        PreparedStatement preparedstatement = connection.prepareStatement(sqlInsert);
        preparedstatement.setString(1, name);
        preparedstatement.setString(2, surname);
        preparedstatement.setString(3, email);
        preparedstatement.setString(4, number);
        preparedstatement.execute();
        connection.close();
    }

    /**
     * "Hard delete"
     * @param id
     * @throws SQLException
     */
    public static void delete(int id) throws SQLException {
        Connection connection = DAOMySQLSettings.getConnection();
        String sqlDelete = "DELETE FROM clinic.client WHERE id = ?";
        PreparedStatement preparedstatement = connection.prepareStatement(sqlDelete);
        preparedstatement.setInt(1, id);
        preparedstatement.execute();
        connection.close();
    }
    public static void softDelete(Client c) throws SQLException {
        if (c.getId() <= 0) {
            throw new StaffException("Invalid client ID.");
        }
        String sqlUpdateFiredDate = "UPDATE client SET cancellationDate = ? WHERE id = ?";
        try (Connection con = DAOMySQLSettings.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sqlUpdateFiredDate)) {

            // Set the firing data
            LocalDate today = LocalDate.now();
            preparedStatement.setDate(1, Date.valueOf(today));
            preparedStatement.setInt(2, c.getId());

            // to the update
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 0) {
                throw new StaffException("No staff found with ID " + c.getId());
            }

        } catch (SQLException e) {
            throw new StaffException("SQL: Error setting firedDate for staff with ID: " );
        }
    }

    public static void update(int id, String name, String surname, String email, String number) throws SQLException {
        Connection connection = DAOMySQLSettings.getConnection();
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
        Connection connection = DAOMySQLSettings.getConnection();
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
        Connection connection = DAOMySQLSettings.getConnection();
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

        Connection connection = DAOMySQLSettings.getConnection();
        String sqlSelect = "SELECT * FROM clinic.client WHERE cancellationDate IS NULL ORDER BY id ASC";
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
        Connection connection = DAOMySQLSettings.getConnection();
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

    public static Client select(int id) throws SQLException {
        if(id<=0){
            return null;
        }
        else{
            Connection connection = DAOMySQLSettings.getConnection();
            String searchStaff = "select * from client where id = ? AND cancellationDate IS NULL";
            PreparedStatement command = connection.prepareStatement(searchStaff);
            command.setInt(1, id);
            ResultSet result = command.executeQuery();
            if(result.next()){
                Client c = new Client();
                c.setId(result.getInt("id"));
                c.setName(result.getString("name"));
                c.setSurname(result.getString("surname"));
                c.setEmail(result.getString("email"));
                c.setNumber(result.getString("number"));
                return c;
            }
            else
                return null;
        }
    }
    public static void main(String[] args) throws SQLException {
        //Client c = select(1);
        //System.out.println(c);
        //softDelete(new Client(1, "Joe", "Smith", "joesmith@gmail.com", "123456789"));
    }

    /**
     * Select of the client with cancellationDate greater than date
     * @param date
     * @return
     */
    public static List<Client> select(LocalDate date){
        String query = "SELECT * FROM client WHERE cancellationDate IS NOT NULL AND cancellationDate <= ?";
        List<Client> clientList = new ArrayList<>();
        try (Connection con = DAOMySQLSettings.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setDate(1, java.sql.Date.valueOf(date));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Client client = new Client(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getString("email"),
                            rs.getString("number"),
                            rs.getDate("cancellationDate").toLocalDate()
                    );
                    clientList.add(client);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error selecting old clients", e);
        }
        return clientList;
    }
}
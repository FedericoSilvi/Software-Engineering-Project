package it.unicas.clinic.address.model;

import it.unicas.clinic.address.model.dao.AppointmentDAO;
import it.unicas.clinic.address.model.dao.mysql.AppointmentDAOMySQLImpl;
import it.unicas.clinic.address.model.dao.mysql.DAOClient;
import javax.mail.*;
import javax.mail.internet.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class Email {
    // Email Configuration
    String sender = "projectclinic123@gmail.com";
    String password = "vbnt xrhr bbdr pppg ";
    String receiver;
    String host = "smtp.gmail.com";
    private AppointmentDAO appointmentDAO = AppointmentDAOMySQLImpl.getInstance();
    // SMTP Server Properties
    Properties properties;
    public Email(String receiver) {
        this.receiver = receiver;
        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
    }

    public boolean sendEmail(Appointment appointment) {
        System.out.println("NOTICE :"+appointment.getNotice());
        if(appointment.getNotice()==null) {
            // Create session with authentication
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sender, password);
                }
            });

            try {
                // Create email message
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(sender));
                message.setRecipients(
                        Message.RecipientType.TO, InternetAddress.parse(receiver));
                message.setSubject("Appointment Reminder");
                message.setText("Dear " + DAOClient.getClient(appointment.getClientId()).getName() + ",\n" +
                        "We would like to remind you the appointment for " + appointment.getDate().toString() + ".\n" +
                        "We recommend to be punctual, the starting time is at " + appointment.getTime().toString());

                // Send the email
                Transport.send(message);
                appointmentDAO.setNotice(appointment);
                System.out.println("Email sent successfully!");

            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        else
            return false;
    }
    public boolean sendCancellation(Appointment appointment) {
        System.out.println("NOTICE :"+appointment.getNotice());
        if(appointment.getNotice()==null) {
            // Create session with authentication
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sender, password);
                }
            });

            try {
                // Create email message
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(sender));
                message.setRecipients(
                        Message.RecipientType.TO, InternetAddress.parse(receiver));
                message.setSubject("Appointment Cancellation");
                message.setText("Dear " + DAOClient.getClient(appointment.getClientId()).getName() + ",\n" +
                        "We are sorry to inform you that the appointment for " + appointment.getDate().toString() +
                        " has been deleted.\n" +
                        "Please contact the clinic center in order to reschedule it. ");

                // Send the email
                Transport.send(message);
                appointmentDAO.setNotice(appointment);
                System.out.println("Email sent successfully!");

            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        else
            return false;
    }

    public boolean sendUpdate(Appointment appointment) {
        System.out.println("NOTICE :"+appointment.getNotice());
        if(appointment.getNotice()==null) {
            // Create session with authentication
            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sender, password);
                }
            });

            try {
                // Create email message
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(sender));
                message.setRecipients(
                        Message.RecipientType.TO, InternetAddress.parse(receiver));
                message.setSubject("Appointment Reschedule");
                message.setText("Dear " + DAOClient.getClient(appointment.getClientId()).getName() + ",\n" +
                        "We are sorry to inform you that the appointment: " + appointment.getService()+
                        " has been rescheduled to the following date:"+ appointment.getDate().toString() +".\n" +
                        "Please contact the clinic center if you want to reschedule it. ");

                // Send the email
                Transport.send(message);
                appointmentDAO.setNotice(appointment);
                System.out.println("Email sent successfully!");

            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        else
            return false;
    }


}

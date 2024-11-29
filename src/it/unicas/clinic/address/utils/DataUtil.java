package it.unicas.clinic.address.utils;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Contain useful classes for data manipulation
 */
public class DataUtil {
    /**
     * Class containing name, surname and whether the user is a manager or not, useful for login operations
     */
    public static class User{

        private String name;
        private String surname;
        private boolean isManager;

        /**
         * Getter of name
         */
        public String getName() {
            return name;

        }
        /**
         * Setter of name, setting it equal to the one passed by argument
         * @param name: wanted name
         */
        public void setName(String name) {
            this.name = name;
        }
        /**
         * Getter of surname
         */
        public String getSurname() {
            return surname;
        }
        /**
         * Setter of surname, setting it equal to the one passed by argument
         * @param surname: wanted surname
         */
        public void setSurname(String surname) {
            this.surname = surname;
        }
        /**
         * Getter of info about being manager or not
         */
        public boolean getManager() {
            return isManager;
        }
        /**
         * Setter of the info about being a manager or not,
         * setting it equal to the one passed by argument
         * @param manager: wanted info
         */
        public void setManager(boolean manager) {
            isManager = manager;
        }

        /**
         * Constructor: sets name and surname as empty strings and info about
         * being a manager false
         */
        public User() {
            this.name = "";
            this.surname = "";
            this.isManager = false;
        }
    }

    /**
     * Creates a LocalDate variable based on the string passed as argument
     * @param dateString: string containing format "yyyy-mm-dd"
     * @return: LocalDate variable
     */
    public static LocalDate parseToDate(String dateString) {
        //Format control
        if (dateString == null || !dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-MM-dd.");
        }

        // Split the string into components
        String[] parts = dateString.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);
        if(month<0 || month>12 || day<0 || day>31) {
            throw new DateTimeException("Invalid date data!");
        }
        // Construct and return the LocalDate object
        return LocalDate.of(year, month, day);
    }

    /**
     * Creates a LocalTime variable based on the string passed as argument
     * @param timeString: string containing format "hh:mm:ss" or "hh:mm" depending on
     * modify parameter
     * @param modify: if true format is "hh:mm",
     *              if false format is "hh:mm:ss"
     * @return: LocalTime variable
     */
    public static LocalTime parseToTime(String timeString,boolean modify) {
        //We are building LocalTime from TextField ("hh:mm" format)
        if (modify) {
            //Format control
            if (timeString == null || !timeString.matches("\\d{1,2}:\\d{2}")) {
                throw new IllegalArgumentException("Invalid time format. Expected HH:mm");
            }

            // Split the string into components
            String[] parts = timeString.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            int second = 0;

            // Construct and return the LocalTime object
            return LocalTime.of(hour, minute, second);

        }
        //We already have "hh:mm:ss" format
        else {
            if (timeString == null || !timeString.matches("\\d{1,2}:\\d{2}:\\d{2}")) {
                System.out.print("\n" + timeString + " stringa eccomi qua 2" + "\n");

                throw new IllegalArgumentException("Invalid time format. Expected HH:mm:ss");
            }
            // Split the string into components
            String[] parts = timeString.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);
            int second = Integer.parseInt(parts[2]);

            // Construct and return the LocalTime object
            return LocalTime.of(hour, minute, second);
        }
    }
}

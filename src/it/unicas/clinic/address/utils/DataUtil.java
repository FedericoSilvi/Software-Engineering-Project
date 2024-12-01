package it.unicas.clinic.address.utils;

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
}

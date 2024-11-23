package it.unicas.clinic.address.utils;

public class DataUtil {
    public static class User{


        private String name;
        private String surname;
        private boolean isManager;

        public String getName() {
            return name;

        }

        public void setName(String username) {
            this.name = username;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public boolean getManager() {
            return isManager;
        }

        public void setManager(boolean manager) {
            isManager = manager;
        }
        public User() {
            this.name = "";
            this.surname = "";
            this.isManager = false;
        }
    }
}

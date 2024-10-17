package com.example.pleasegodwhy;

public class firebaselogin {

    String Username,Password, Email;
    int PhoneNumber;
    boolean Notifications;

    public firebaselogin(boolean notifications, int phoneNumber, String email, String password, String username) {
        Notifications = notifications;
        PhoneNumber = phoneNumber;

        Email = email;
        Password = password;
        Username = username;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }


    public int getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public boolean isNotifications() {
        return Notifications;
    }

    public void setNotifications(boolean notifications) {
        Notifications = notifications;
    }
}

package com.example.pleasegodwhy;

public class firebaselogin {

    String username,password, email;
    int phoneNumber;
    boolean notifications;

    public firebaselogin(boolean notifications, int phoneNumber, String email, String password, String username) {
        this.notifications = notifications;
        this.phoneNumber = phoneNumber;

        this.email = email;
        this.password = password;
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }
}

package com.example.pleasegodwhy;

public class UseSingleton {

    private static UseSingleton instance;
    private String username;
    private String phoneNumber;
    private int accountNo;
    private String email;

    private UseSingleton(){}

    public static synchronized UseSingleton getInstance(){

        if(instance == null){
            instance = new UseSingleton();
        }
        return instance;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public int getaccountNo(){
        return accountNo;
    }

    public void setaccountNo(int accountNo){
        this.accountNo = accountNo;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }


}

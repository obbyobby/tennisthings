package com.example.pleasegodwhy;

public class UseSingleton {

    private static UseSingleton instance;
    private String username;
    private String phoneNumber;
    private String accountNo;
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

    public String getAccountNo(){
        return accountNo;
    }

    public void setAccountNo(String accountNo){
        this.accountNo = accountNo;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }


}

package com.example.expensemanagement.Entity;

public class User {
    private int userId;
    private String userName;
    private String passWord;
    private int balance;
    private String email;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(String userName, String passWord, int balance, String email) {
        this.userName = userName;
        this.passWord = passWord;
        this.balance = balance;
        this.email = email;
    }
    public User() {

    }
}

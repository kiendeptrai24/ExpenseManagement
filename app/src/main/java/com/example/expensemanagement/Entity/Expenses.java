package com.example.expensemanagement.Entity;

public class Expenses {
    int id;
    String date;
    int amount;
    String category;
    String description;
    String userName;

    public Expenses(String date, int amount, String category, String description, String userName) {
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.userName = userName;
    }
    public Expenses(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

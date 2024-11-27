package com.example.expensemanagement.Interface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {
    private int date;
    private  int month;
    private int year;

    public MyDate(int date, int year, int month) {
        this.date = date;
        this.year = year;
        this.month = month;
    }
    public MyDate()
    {

    }
    public void setEmpty()
    {
        year = 0;
        month = 0;
        date = 0;
    }

    public String GetDateAll()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date specificDate = new Date();
        String v =String.format("%d-%d-%d", this.year,this.month,this.date);
        try{
            specificDate = dateFormat.parse(v);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return dateFormat.format(specificDate);
    }
    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

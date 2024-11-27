package com.example.expensemanagement.Interface;

public class MonthlySummary {
    private String month;
    private float totalIncome;
    private float totalExpense;

    public MonthlySummary(String month, Float totalIncome, Float totalExpense){
        this.month = month;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
    }
    public MonthlySummary()
    {
    }

    public String getMonth() {
        return month;
    }

    public Float getTotalIncome() {
        return totalIncome;
    }

    public Float getTotalExpense() {
        return totalExpense;
    }


    @Override
    public String toString() {
        return "MonthlySummary{" +
                "month='" + month + '\'' +
                ", totalIncome=" + totalIncome +
                ", totalExpense=" + totalExpense +
                '}';
    }
}

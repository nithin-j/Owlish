package com.example.owlish.Data;

public class Expense {

    private String Id;
    private String Title;
    private String Description;
    private float Amount;
    private String ExpenseDate;
    private String Type;

    public Expense(String id, String title, String description, float amount, String expenseDate, String type) {
        Id = id;
        Title = title;
        Description = description;
        Amount = amount;
        ExpenseDate = expenseDate;
        Type = type;
    }

    public Expense() {
    }

    @Override
    public String toString() {
        return Title +": "+Type+ " $" +Amount+"\nDate: " + ExpenseDate + "\nDescription: " + Description;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public String getExpenseDate() {
        return ExpenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        ExpenseDate = expenseDate;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}

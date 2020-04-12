package com.example.owlish.Data;

public class Income {
    private String Id;
    private String Title;
    private String Description;
    private float Amount;
    private String IncomeDate;
    private String Type;

    public Income(String id, String title, String description, float amount, String incomeDate, String type) {
        Id = id;
        Title = title;
        Description = description;
        Amount = amount;
        IncomeDate = incomeDate;
        Type = type;
    }

    public Income() {
    }

    @Override
    public String toString() {
        return Title +": "+Type+ " $" +Amount+"\nDate: " + IncomeDate + "\nDescription: " + Description;
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

    public String getIncomeDate() {
        return IncomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        IncomeDate = incomeDate;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}

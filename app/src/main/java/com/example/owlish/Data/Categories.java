package com.example.owlish.Data;

public class Categories {
    private String Id;
    private String Name;

    public Categories(String id, String name) {
        Id = id;
        Name = name;
    }

    public Categories() {
    }

    @Override
    public String toString() {
        return Name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}

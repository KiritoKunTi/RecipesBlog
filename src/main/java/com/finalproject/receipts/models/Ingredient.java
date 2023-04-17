package com.finalproject.receipts.models;

import lombok.Data;

@Data
public class Ingredient {
    private long id;
    private String name;
    private String measurement;
    private long receiptID;
    private double amount;
    public Ingredient(){}

    public Ingredient(long id, String name, String measurement, double amount) {
        this.id = id;
        this.name = name;
        this.measurement = measurement;
        this.amount = amount;
    }

    public Ingredient(String name, String measurement, double amount){
        this.name = name;
        this.measurement = measurement;
        this.amount = amount;
    }
}

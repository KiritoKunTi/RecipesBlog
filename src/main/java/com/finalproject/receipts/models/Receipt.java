package com.finalproject.receipts.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
public class Receipt {
    private long id;
    private String name;
    private long userID;
    private Date createdAt;
    private String description;
    private List<Ingredient> ingredients = new ArrayList<>();

    public Receipt(){
        ingredients = new ArrayList<>();
    }

    public Receipt(String name, String description, List<Ingredient> ingredients){
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
    }
    public Receipt(long id, String name, long userID, Date createdAt, String description, List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.userID = userID;
        this.createdAt = createdAt;
        this.description = description;
        this.ingredients = ingredients;
    }

    public Receipt(long id, String name, long userID, Date createdAt, String description) {
        this.id = id;
        this.name = name;
        this.userID = userID;
        this.createdAt = createdAt;
        this.description = description;
        this.ingredients = new ArrayList<>();
    }

    public void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
    }
}

package com.finalproject.receipts.models;

import lombok.Data;

import java.sql.Date;

@Data
public class Receipt {
    private long id;
    private String name;
    private long userID;
    private Date createdAt;
    private String description;
    public Receipt(long id, long userID, Date createdAt){
        this.id = id;
        this.userID = userID;
        this.createdAt = createdAt;
    }

}

package com.finalproject.receipts.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Session {
    private long id;
    private long userID;
    private String uuid;
    private Date expiredAt;
    public Session(long userID){
        this.userID = userID;
        this.uuid = uuid;
        expiredAt = new Date(new java.util.Date().getTime());
    }
}

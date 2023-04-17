package com.finalproject.receipts.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
public class User {

    public User(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    private long id;
    private String username;
    private String password;

}

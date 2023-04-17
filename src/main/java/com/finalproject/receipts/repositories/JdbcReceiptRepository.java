package com.finalproject.receipts.repositories;

import com.finalproject.receipts.models.Receipt;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public class JdbcReceiptRepository implements ReceiptRepository{
    private JdbcTemplate jdbc;
    public JdbcReceiptRepository (JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }
    @Override
    public void save(Receipt receipt) {
        receipt.setCreatedAt(getToday());
        jdbc.update("INSERT INTO Receipts(NAME, USER_ID, created_at, description) VALUES (?, ?, ?, ?)", receipt.getName(), receipt.getUserID(), receipt.getCreatedAt(), receipt.getDescription());
    }
    private Date getToday(){
        return new Date(new java.util.Date().getTime());
    }
}

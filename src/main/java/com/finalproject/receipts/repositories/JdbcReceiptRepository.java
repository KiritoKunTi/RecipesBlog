package com.finalproject.receipts.repositories;

import com.finalproject.receipts.models.Receipt;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcReceiptRepository implements ReceiptRepository{
    private JdbcTemplate jdbc;

    public JdbcReceiptRepository (JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }
    @Override
    public void save(Receipt receipt) {
        receipt.setCreatedAt(getToday());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO Receipts(NAME, USER_ID, created_at, description) VALUES (?, ?, ?, ?)";
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement preparedStatement = con.prepareStatement(sql, new String[]{"id"});
                preparedStatement.setString(1, receipt.getName());
                preparedStatement.setLong(2, receipt.getUserID());
                preparedStatement.setDate(3, receipt.getCreatedAt());
                preparedStatement.setString(4, receipt.getDescription());
                return preparedStatement;
            }
        };


        jdbc.update(preparedStatementCreator, keyHolder);
        receipt.setId(keyHolder.getKey().longValue());
    }
    private Date getToday(){
        return new Date(System.currentTimeMillis());
    }
}

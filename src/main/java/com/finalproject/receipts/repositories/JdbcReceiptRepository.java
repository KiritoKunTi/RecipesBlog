package com.finalproject.receipts.repositories;

import com.finalproject.receipts.models.Ingredient;
import com.finalproject.receipts.models.Receipt;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcReceiptRepository implements ReceiptRepository{
    private JdbcTemplate jdbc;

    public JdbcReceiptRepository (JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }



    @Override
    public List<Receipt> findAll() {
        return jdbc.query("SELECT ID, NAME, USER_ID, CREATED_AT, DESCRIPTION FROM Receipts", this::mapRowToReceipt);
    }

    @Override
    public Receipt findByID(long id) {
        return jdbc.queryForObject("SELECT ID, NAME, USER_ID, CREATED_AT, DESCRIPTION FROM Receipts WHERE ID = ?", this::mapRowToReceipt, id);
    }

    private Receipt mapRowToReceipt(ResultSet resultSet, int rowNum) throws SQLException {
        return new Receipt(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getLong("user_id"), resultSet.getDate("created_at"), resultSet.getString("description"));
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

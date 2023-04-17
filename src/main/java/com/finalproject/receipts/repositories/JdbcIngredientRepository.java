package com.finalproject.receipts.repositories;

import com.finalproject.receipts.models.Ingredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcIngredientRepository implements IngredientRepository{
    JdbcTemplate jdbc;
    public JdbcIngredientRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }
    @Override
    public void save(Ingredient ingredient) {
        jdbc.update("INSERT INTO Ingredients(NAME, MEASUREMENT, RECEIPT_ID, AMOUNT) VALUES (?, ?, ?, ?)", ingredient.getName(), ingredient.getMeasurement(), ingredient.getReceiptID(), ingredient.getAmount());
    }

    @Override
    public List<Ingredient> findIngredientsByReceiptID(long receiptID) {
        return jdbc.query("SELECT ID, NAME, MEASUREMENT, RECEIPT_ID, AMOUNT FROM Ingredients WHERE RECEIPT_ID = ?", this::mapRowToIngredient, receiptID);
    }

    private Ingredient mapRowToIngredient(ResultSet resultSet, int rowNum) throws SQLException {
        return new Ingredient(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("measurement"), resultSet.getDouble("amount"));
    }
}

package com.finalproject.receipts.repositories;

import com.finalproject.receipts.models.User;
import com.finalproject.receipts.security.SecurityConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.*;
@Repository
@AllArgsConstructor

public class JdbcUserRepository implements UserRepository{
    private JdbcTemplate jdbc;
    @Override
    public void save(User user) {
        String generatedPassword = SecurityConfiguration.passwordEncoder.encode(user.getPassword());
        jdbc.update("INSERT INTO Users(USERNAME, PASSWORD) VALUES (?, ?)", user.getUsername(), generatedPassword);
    }

    @Override
    public User findByID(long id) {
        return jdbc.queryForObject("SELECT ID, USERNAME, PASSWORD FROM Users WHERE ID = ?", this::mapRowToUser, id);
    }

    @Override
    public Iterable<User> findAll() {
        return jdbc.query("SELECT ID, USERNAME, PASSWORD FROM Users", this::mapRowToUser);
    }

    @Override
    public User findByUsername(String username) {
        return jdbc.queryForObject("SELECT ID, USERNAME, PASSWORD FROM Users WHERE USERNAME = ?", this::mapRowToUser, username);
    }

    private User mapRowToUser(ResultSet resultSet, int rowNumber) throws SQLException {
        return new User(resultSet.getLong("id"), resultSet.getString("username"), resultSet.getString("password"));
    }
}

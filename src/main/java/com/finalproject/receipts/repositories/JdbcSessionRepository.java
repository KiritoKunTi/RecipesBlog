package com.finalproject.receipts.repositories;

import com.finalproject.receipts.models.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.UUID;

@Repository
public class JdbcSessionRepository implements SessionRepository{
    private static final int maxAgeDays = 1;
    private JdbcTemplate jdbc;

    public JdbcSessionRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }
    @Override
    public Session save(long userID) {
        Session session = new Session(userID);
        session.setUuid(generateUUID());
        session.setExpiredAt(getExpirationDate());
        jdbc.update("INSERT INTO Sessions(USER_ID, UUID, EXPIRED_AT) VALUES (?, ?, ?)", session.getUserID(), session.getUuid(), session.getExpiredAt());
        return session;
    }

    @Override
    public Session findByUUID(String uuid) {
        return jdbc.queryForObject("SELECT ID, USER_ID, UUID, EXPIRED_AT FROM Sessions WHERE UUID = ?", this::mapRowToSession, uuid);
    }

    private Session mapRowToSession(ResultSet resultSet, int rowNumber) throws SQLException {
        return new Session(resultSet.getInt("id"), resultSet.getLong("user_id"), resultSet.getString("uuid"), resultSet.getDate("expired_at"));
    }

    private static String generateUUID(){
        return UUID.randomUUID().toString();
    }
    private static java.sql.Date getExpirationDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date());
        calendar.add(Calendar.DAY_OF_MONTH, maxAgeDays);
        return new Date(calendar.getTime().getTime());
    }
}

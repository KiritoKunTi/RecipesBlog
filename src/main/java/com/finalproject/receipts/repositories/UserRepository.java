package com.finalproject.receipts.repositories;

import com.finalproject.receipts.models.User;

public interface UserRepository{
    void save(User user);
    User findByID(long id);
    Iterable<User> findAll();
    User findByUsername(String username);
}

package com.finalproject.receipts.validators;

import com.finalproject.receipts.models.User;
import com.finalproject.receipts.repositories.JdbcUserRepository;
import com.finalproject.receipts.repositories.UserRepository;

import java.sql.SQLException;

public class UserValidator extends AbstractValidator{
    private User user;
    private UserRepository userRepository;

    public UserValidator(User user, UserRepository userRepository){
        this.userRepository = userRepository;
        this.user = user;
    }
    @Override
    public void check() {
        if (!validatePassword(user.getPassword())){
            addError("password", "password should consist at least 8 characters");
        }
        if (!validateUsernameLength(user.getUsername())){
            addError("username", "username should consist at least 1 character");
        }
        if (!validateUniqueUsername(user.getUsername())){
            addError("username", "username applied to another user");
        }
    }

    private boolean validatePassword(String password){
        return password.length() >= 8;
    }


    private boolean validateUniqueUsername(String username){
        try{
            userRepository.findByUsername(username);
        }catch (Exception exception){
            return true;
        }
        return false;
    }

    private boolean validateUsernameLength(String username){
        return username.length() != 0;

    }

}

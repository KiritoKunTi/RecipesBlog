package com.finalproject.receipts.controllers;


import com.finalproject.receipts.models.User;
import com.finalproject.receipts.repositories.UserRepository;
import com.finalproject.receipts.validators.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/registration", method = RequestMethod.POST)
@AllArgsConstructor
public class RegistrationController {
    private UserRepository userRepository;

    @PostMapping
    @ResponseBody
    public Object register(@RequestBody User user){
        UserValidator validator = new UserValidator(user, userRepository);
        validator.check();
        if (validator.isValid()){
            userRepository.save(user);
            return user;
        }
        return validator.getErrors();
    }

}

package com.finalproject.receipts.controllers;

import com.auth0.jwt.JWT;
import com.finalproject.receipts.models.Session;
import com.finalproject.receipts.models.User;
import com.finalproject.receipts.repositories.SessionRepository;
import com.finalproject.receipts.repositories.UserRepository;
import com.finalproject.receipts.security.SecurityConfiguration;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping(path = "api/login", method = RequestMethod.POST)
public class LoginController {
    private final static int maxAgeMinutes = 1;

    private UserRepository userRepository;
    private SessionRepository sessionRepository;

    public LoginController(UserRepository userRepository, SessionRepository sessionRepository){
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @PostMapping
    @ResponseBody
    public Object login(@RequestBody User user, HttpServletResponse response){
        HashMap<String, String> map = new HashMap<>();
        try{
            User userDB = userRepository.findByUsername(user.getUsername());
            if (!SecurityConfiguration.passwordEncoder.matches(user.getPassword(), userDB.getPassword())){
                response.setStatus(403);
                map.put("errors", "Password or username is not correct");
                return map;
            }
            Session session = sessionRepository.save(userDB.getId());
            Cookie cookieAccess = new Cookie("access_token", generateJWTToken(userDB.getId()));
            Cookie cookieRefresh = new Cookie("refresh_token", session.getUuid());
            response.addCookie(cookieRefresh);
            response.addCookie(cookieAccess);
        }catch (Exception exception){
            response.setStatus(403);
            map.put("errors", "Password or username is not correct");
            return map;
        }
        response.setStatus(201);
        return null;
    }



    private static Date getExpirationDate(){
        return new Date(System.currentTimeMillis() + 60 * 1000 * maxAgeMinutes);
    }

    public static String generateJWTToken(long userID){
        return JWT.create().withIssuer("auth0").withClaim("user_id", userID).withExpiresAt(getExpirationDate()).withIssuedAt(new Date()).sign(SecurityConfiguration.algorithm);
    }
}

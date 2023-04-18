package com.finalproject.receipts.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.finalproject.receipts.controllers.LoginController;
import com.finalproject.receipts.models.Session;
import com.finalproject.receipts.models.User;
import com.finalproject.receipts.repositories.SessionRepository;
import com.finalproject.receipts.repositories.UserRepository;
import com.finalproject.receipts.tokens.AccessToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class Authentication {
    static UserRepository userRepository;
    static SessionRepository sessionRepository;
    public Authentication(UserRepository userRepository, SessionRepository sessionRepository){
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public static long getUserID(String accessToken, String refreshToken, HttpServletResponse response){
        long userID;
        try {
            userID = new AccessToken(accessToken).getUserID();
            if (userID != 0) {
                setAccessToken(accessToken, response);
                return userID;
            }
        }catch (Exception e) {
            String newAccessToken = getAccessToken(refreshToken);
            if (newAccessToken == "") {
                return 0;
            }
            AccessToken newToken = new AccessToken(newAccessToken);
            setAccessToken(newToken.getAccessToken(), response);
            userID = newToken.getUserID();
        }
        return userID;
    }

    public static HashMap<String, String> needAuthenticationResponse(){
        HashMap<String, String> response = new HashMap<>();
        response.put("error", "You need authenticate");
        return response;
    }

    private static void setAccessToken(String accessToken, HttpServletResponse response){
        response.addCookie(new Cookie("access_token", accessToken));
    }
    private static String getAccessToken(String refreshToken){
        Session session = sessionRepository.findByUUID(refreshToken);
        if (session == null){
            throw new JWTVerificationException("User need to login");
        }
        return LoginController.generateJWTToken(session.getUserID());
    }
}

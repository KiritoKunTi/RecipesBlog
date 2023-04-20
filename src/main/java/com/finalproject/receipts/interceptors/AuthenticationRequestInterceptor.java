package com.finalproject.receipts.interceptors;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.finalproject.receipts.controllers.LoginController;
import com.finalproject.receipts.models.Session;
import com.finalproject.receipts.repositories.SessionRepository;
import com.finalproject.receipts.tokens.AccessToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;

@Slf4j
public class AuthenticationRequestInterceptor implements HandlerInterceptor {
    private static SessionRepository sessionRepository;

    public AuthenticationRequestInterceptor(SessionRepository sessionRepository){
        this.sessionRepository = sessionRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken;
        String refreshToken;
        try {
            accessToken = getValueFromCookie(request, "access_token");
            refreshToken = getValueFromCookie(request, "refresh_token");
            if (getUserID(accessToken, refreshToken, response) == 0){
                response.setStatus(511);
                return false;
            }
        }catch (Exception e){
            response.setStatus(511);
            return false;
        }
        return true;
    }
    private static String getValueFromCookie(HttpServletRequest request, String cookieName){
        String cookies = request.getHeader("Cookie");
        String[] rawCookies = cookies.split(";");
        for(String rawCookie: rawCookies){
            String[] rawCookieArray = rawCookie.split("=");
            if (rawCookieArray[0].contains(cookieName)){
                return rawCookieArray[1];
            }
        }
        return "";
    }
    public static long getUserID(String accessToken, String refreshToken, HttpServletResponse response){
        long userID;
        try {
            userID = new AccessToken(accessToken).getUserID();
            if (userID != 0) {
                return userID;
            }
        }catch (Exception e) {
            String newAccessToken = getAccessToken(refreshToken);
            if (newAccessToken == "") {
                return 0;
            }
            AccessToken newToken = new AccessToken(newAccessToken);
            userID = newToken.getUserID();
        }
        return userID;
    }
    private static String getAccessToken(String refreshToken){
        Session session = sessionRepository.findByUUID(refreshToken);
        if (session == null){
            throw new JWTVerificationException("User need to login");
        }
        return LoginController.generateJWTToken(session.getUserID());
    }
}

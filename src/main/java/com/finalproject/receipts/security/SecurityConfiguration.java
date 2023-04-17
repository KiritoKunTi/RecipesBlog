package com.finalproject.receipts.security;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration {
    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final String secretKey = "secret key";
    public static final Algorithm algorithm = Algorithm.HMAC256(secretKey);
}

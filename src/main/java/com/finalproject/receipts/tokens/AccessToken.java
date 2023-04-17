package com.finalproject.receipts.tokens;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.finalproject.receipts.security.SecurityConfiguration;

public class AccessToken {
    public String getAccessToken() {
        return accessToken;
    }

    private String accessToken;
    public AccessToken(String accessToken){
        this.accessToken = accessToken;
    }
    public long getUserID(){
        JWTVerifier verifier = JWT.require(SecurityConfiguration.algorithm).withIssuer("auth0").build();
        DecodedJWT decodedJWT = verifier.verify(accessToken);
        return decodedJWT.getClaim("user_id").asLong();
    }
}

package com.task.test.util;

import com.google.gson.Gson;
import com.task.test.dto.profile.UpdateEmailDTO;
import com.task.test.dto.profile.UserDetails;
import io.jsonwebtoken.*;

import java.util.Date;

public class TokenProcess {
    private static String secretKey = "taskkey";

    public static <T> String encodeJwt(T arg) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(new Gson().toJson(arg));
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
        jwtBuilder.signWith(SignatureAlgorithm.HS256, secretKey);
        jwtBuilder.setIssuer("testTask");
        String jwt = jwtBuilder.compact();
        return jwt;
    }


    public static Integer decodeJwtForId(String jwt) {
        JwtParser jwtParser = Jwts.parser();

        jwtParser.setSigningKey(secretKey);
        Jws jws = jwtParser.parseClaimsJws(jwt);

        Claims claims = (Claims) jws.getBody();
        String id = claims.getSubject();

        return Integer.parseInt(id);
    }

    public static UserDetails decodeJwt(String jwt) {
        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(secretKey);
        Jws jws = jwtParser.parseClaimsJws(jwt);
        Claims claims = (Claims) jws.getBody();
        UserDetails userDetails = new Gson().fromJson(claims.getSubject(), UserDetails.class);
        return userDetails;
    }

    public static UpdateEmailDTO decodeJwt2(String jwt) {
        JwtParser jwtParser = Jwts.parser();
        jwtParser.setSigningKey(secretKey);
        Jws jws = jwtParser.parseClaimsJws(jwt);
        Claims claims = (Claims) jws.getBody();
        UpdateEmailDTO updateEmailDTO = new Gson().fromJson(claims.getSubject(), UpdateEmailDTO.class);
        return updateEmailDTO;
    }
}

package com.hyperionoj.web.infrastructure.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
public class JWTUtils {

    private static final String JWTTOKEN = "HyperionLR";

    public static String createToken(Long userId, long l) {
        Map<String, Object> claims = new HashMap<>(4);
        claims.put("userId", userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                // 签发算法，秘钥为jwtToken
                .signWith(SignatureAlgorithm.HS256, JWTTOKEN)
                // body数据，要唯一，自行设置
                .setClaims(claims)
                // 设置签发时间
                .setIssuedAt(new Date())
                // 有效时间
                .setExpiration(new Date(System.currentTimeMillis() + l * 1000));
        return jwtBuilder.compact();
    }

    public static Map<String, Object> checkToken(String token) {
        try {
            Jwt parse = Jwts.parser().setSigningKey(JWTTOKEN).parse(token);
            return (Map<String, Object>) parse.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
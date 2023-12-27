package com.ansim.service;

import com.ansim.util.JWTUtil2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTestService {

    @Value("${jwt.secret}")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60l; //1시간
    
    public String login(String username, String password){
        //인증과정 생략
        Map<String,Object> payloads = new HashMap<>();
        payloads.put("username",username);
        payloads.put("password",password);
        return JWTUtil2.generateToken(payloads,secretKey,expiredMs);
    }
}

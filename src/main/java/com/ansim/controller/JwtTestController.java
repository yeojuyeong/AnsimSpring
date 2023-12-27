package com.ansim.controller;

import com.ansim.service.GuideService;
import com.ansim.service.JwtTestService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtTestController {

    //private final JwtTestService service;
    @Autowired
    JwtTestService service;

    @PostMapping("/jwt/login")
    public ResponseEntity<String> login(){
        System.out.println("login()");
        return ResponseEntity.ok().body(service.login("냥냥이",""));
    }

    @GetMapping("/jwt/login2")
    public String login2(){
        System.out.println("login2()");
        return "졸려";
    }

    @GetMapping("/apple/login")
    public String login3(){
        System.out.println("login3()");
        return "졸려2";
    }
}

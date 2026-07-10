package com.fintech.banking_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fintech.banking_app.dto.LoginRequest;
import com.fintech.banking_app.dto.LoginResponse;
import com.fintech.banking_app.dto.RegisterRequest;
import com.fintech.banking_app.entity.User;
import com.fintech.banking_app.service.AuthService;
import com.fintech.banking_app.service.UserService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public User register(
            @RequestBody RegisterRequest request
    ){

        return userService.register(request);

    }
    
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request
    ){

        return authService.login(request);

    }


}
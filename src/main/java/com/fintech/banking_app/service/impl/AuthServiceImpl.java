package com.fintech.banking_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fintech.banking_app.dto.LoginRequest;
import com.fintech.banking_app.dto.LoginResponse;
import com.fintech.banking_app.entity.User;
import com.fintech.banking_app.repository.UserRepository;
import com.fintech.banking_app.security.JwtService;
import com.fintech.banking_app.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JwtService jwtService;



    @Override
    public LoginResponse login(LoginRequest request) {


        User user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(
                    () -> new RuntimeException("User not found")
                );


        if(!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            throw new RuntimeException("Invalid password");

        }


        String token =
                jwtService.generateToken(user.getEmail());


        return new LoginResponse(token);

    }

}
package com.fintech.banking_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fintech.banking_app.dto.RegisterRequest;
import com.fintech.banking_app.entity.Role;
import com.fintech.banking_app.entity.User;
import com.fintech.banking_app.repository.UserRepository;
import com.fintech.banking_app.service.UserService;

@Service
public class UserServiceImpl implements UserService{


@Autowired
private UserRepository userRepository;


@Autowired
private PasswordEncoder passwordEncoder;



@Override
public User register(RegisterRequest request){


    User user = new User();

    user.setName(request.getName());

    user.setEmail(request.getEmail());

    user.setPassword(
        passwordEncoder.encode(request.getPassword())
    );


    user.setRole(Role.CUSTOMER);


    return userRepository.save(user);

}


}
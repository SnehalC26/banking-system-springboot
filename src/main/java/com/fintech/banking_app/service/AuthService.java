package com.fintech.banking_app.service;

import com.fintech.banking_app.dto.LoginRequest;
import com.fintech.banking_app.dto.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

}
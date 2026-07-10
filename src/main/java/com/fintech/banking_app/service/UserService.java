package com.fintech.banking_app.service;

import com.fintech.banking_app.dto.RegisterRequest;
import com.fintech.banking_app.entity.User;

public interface UserService {
	   User register(RegisterRequest request);
}

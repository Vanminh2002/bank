package com.example.bank.Services;

import com.example.bank.Dto.Request.AuthenticationRequest;

public interface AuthenticationService {
     boolean authenticate(AuthenticationRequest request);
}

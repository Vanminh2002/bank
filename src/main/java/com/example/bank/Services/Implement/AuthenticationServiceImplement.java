package com.example.bank.Services.Implement;

import com.example.bank.Dto.Request.AuthenticationRequest;
import com.example.bank.Exception.AppException;
import com.example.bank.Exception.ErrorCode;
import com.example.bank.Repository.UserRepository;
import com.example.bank.Services.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImplement implements AuthenticationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    @Override
    public boolean authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return passwordEncoder.matches(request.getPassword(), user.getPassword());
    }
}

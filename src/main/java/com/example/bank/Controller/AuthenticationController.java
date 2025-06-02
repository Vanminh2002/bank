package com.example.bank.Controller;

import com.example.bank.Dto.Request.AuthenticationRequest;
import com.example.bank.Dto.Request.IntroSpectRequest;
import com.example.bank.Dto.Response.ApiResponse;
import com.example.bank.Dto.Response.AuthenticationResponse;
import com.example.bank.Dto.Response.IntrospectResponse;
import com.example.bank.Services.Implement.AuthenticationServiceImplement;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationServiceImplement authenticationServiceImplement;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        var result = authenticationServiceImplement.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntroSpectRequest request) throws ParseException, JOSEException {
        var result = authenticationServiceImplement.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}

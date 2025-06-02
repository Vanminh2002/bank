package com.example.bank.Services;

import com.example.bank.Dto.Request.AuthenticationRequest;
import com.example.bank.Dto.Request.IntroSpectRequest;
import com.example.bank.Dto.Response.AuthenticationResponse;
import com.example.bank.Dto.Response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {
     AuthenticationResponse authenticate(AuthenticationRequest request);
     IntrospectResponse introspect(IntroSpectRequest request) throws JOSEException, ParseException;
}

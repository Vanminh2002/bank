package com.example.bank.Services.Implement;

import com.example.bank.Dto.Request.AuthenticationRequest;
import com.example.bank.Dto.Request.IntroSpectRequest;
import com.example.bank.Dto.Response.AuthenticationResponse;
import com.example.bank.Dto.Response.IntrospectResponse;
import com.example.bank.Entity.User;
import com.example.bank.Exception.AppException;
import com.example.bank.Exception.ErrorCode;
import com.example.bank.Mapper.UserMapper;
import com.example.bank.Repository.UserRepository;
import com.example.bank.Services.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImplement implements AuthenticationService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;
    // dùng để biến này không inject vào constructor
    @NonFinal
    @Value("${jwt.signerKey}")
    private String SECRET_KEY;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // kiểm tra xem user có tồn tại không
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.AUTHENTICATION_FAILED);
        }
        //
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(authenticated)
                .build();

    }

    @Override
    public IntrospectResponse introspect(IntroSpectRequest request) throws JOSEException, ParseException {
        // lấy token từ request
        var token = request.getToken();
        // verifier bằng thuật toán tạo nên token bằng key mã hóa
        JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());
        // parse từ chuỗi JWT sang String
        SignedJWT signedJWT = SignedJWT.parse(token);
        // kiểm tra hạn của token
        Date expirytime = signedJWT.getJWTClaimsSet().getExpirationTime();
        // hàm này sẽ trả về kiểu dữ liệu true/ false
        var verify = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verify && expirytime.after(new Date()))
                .build();
    }

//    @Override
//    public RegisterResponse register(RegisterRequest request) {
//        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
//            throw new AppException(ErrorCode.USER_EXISTED);
//        }
//        User user = userMapper.registerUser(request);
//        User savedUser = userRepository.save(user);
//
//        return userMapper.toResponseRegis(savedUser);
//    }


    private String generateToken(User user) {
        // đây là header mà jwsObject cần và nó sử dụng thuật toán HS512
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                // phát hành bởi ai
                .issuer("Bank")
                // thời gian tạo
                .issueTime(new Date())
                .claim("scope", buildScope(user))
                // thời gian hết hạn
                .expirationTime(new Date(Instant.now()
                        // 1h
                        .plus(1, ChronoUnit.HOURS)
                        .toEpochMilli()))
                .build();
        // tạo PayLoad
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        // cần 2 param là Header và PayLoad
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        // kí token
        // new MACSigner() khóa để kí và khóa giải mã trùng nhau
        // để kí cái này cần 1 chuỗi 32 byte gọi là secretKey
        try {
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Can't create token", e);
            throw new RuntimeException(e);
        }
    }

    // phương thức build Scope cho role
    private String buildScope(User user) {
        // vì scope là 1 list nên sẽ dùng StringJoiner
        // các scope trong oauth2 quy định phân cách bởi 1 dấu cách nên ta làm " "
        StringJoiner scopes = new StringJoiner(" ");
        // CollectionUtils.isEmpty hàm kiểm tra xem có empty hay không
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                scopes.add("ROLE_"+role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions()
                            .forEach(permission -> scopes.add(permission.getName()));
            });
        }
        return scopes.toString();
    }

}

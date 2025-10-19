package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.auth.AuthenticationRequest;
import com.example.ModaMint_Backend.dto.request.auth.IntrospectRequest;
import com.example.ModaMint_Backend.dto.request.auth.LogoutRequest;
import com.example.ModaMint_Backend.dto.response.auth.AuthenticationResponse;
import com.example.ModaMint_Backend.dto.response.auth.IntrospectResponse;
import com.example.ModaMint_Backend.dto.response.auth.RefreshResponse;
import com.example.ModaMint_Backend.dto.response.user.UserResponse;
import com.example.ModaMint_Backend.entity.User;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.repository.UserRepository;
import com.example.ModaMint_Backend.mapper.UserMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthenticationService {
    UserRepository userRepository;
    @NonFinal
    @Value("${jwt.signer-key}")
    protected String SIGNER_KEY;
    PasswordEncoder passwordEncoder;
    InvalidTokenService invalidTokenService;
    UserMapper userMapper;

    public AuthenticationResponse authenticate(AuthenticationRequest request,
                                               HttpServletResponse response) throws JOSEException {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Tạo token
        String accessToken = generateToken(user,1, ChronoUnit.HOURS, "access");
        String refreshToken = generateToken(user,7, ChronoUnit.DAYS, "refresh");

        // Set refreshToken vào HttpOnly cookie
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // nếu dùng https
        cookie.setPath("/refresh");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
        response.addCookie(cookie);

        // Trả về access token trong body
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .expiresIn(3600)
                .tokenType("Bearer")
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getAccessToken();
        SignedJWT signedJWT = SignedJWT.parse(token);
        // Lấy claim type
        String type = signedJWT.getJWTClaimsSet().getStringClaim("type");
        if (!"access".equals(type)) {
            throw new AppException(ErrorCode.TOKEN_IS_NOT_VALID);
        }

        // Verify chữ ký
        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());
        boolean verified = signedJWT.verify(jwsVerifier);

        // Kiểm tra blacklist
        if (invalidTokenService.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            verified = false;
        }

        // Kiểm tra expiration
        Date expiredTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        return IntrospectResponse.builder()
                .valid(verified && expiredTime.after(new Date()))
                .build();
    }




    public RefreshResponse refresh(String refreshToken) throws JOSEException, ParseException {
        SignedJWT signedJWT = verify(refreshToken);

        String username = signedJWT.getJWTClaimsSet().getSubject();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String type = signedJWT.getJWTClaimsSet().getStringClaim("type");
        if (!"refresh".equals(type)) {
            throw new AppException(ErrorCode.TOKEN_IS_NOT_VALID); // token sai loại
        }

        String newAccessToken = generateToken(user, 1, ChronoUnit.HOURS, "access");
        return RefreshResponse.builder()
                .newAccessToken(newAccessToken)
                .build();
    }


    public void logout(LogoutRequest request) {
        try {
            if(request.getAccessToken() != null) {
                SignedJWT accessJWT = verify(request.getAccessToken());
                invalidTokenService.saveInvalidToken(accessJWT);
            }

            if(request.getRefreshToken() != null) {
                SignedJWT refreshJWT = verify(request.getRefreshToken());
                invalidTokenService.saveInvalidToken(refreshJWT);
            }

        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    public UserResponse getCurrentUser(String accessToken) throws JOSEException, ParseException {
        SignedJWT signedJWT = verify(accessToken);

        String type = signedJWT.getJWTClaimsSet().getStringClaim("type");
        if (!"access".equals(type)) {
            throw new AppException(ErrorCode.TOKEN_IS_NOT_VALID);
        }

        String username = signedJWT.getJWTClaimsSet().getSubject();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }

    // Sau này có thể thêm tham số để kiểm tra token có phải refresh token hay không
    // private SignedJWT verify(String token, boolean isRefreshToken);
    private SignedJWT verify(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        boolean verified = signedJWT.verify(verifier);

        if (!(verified && expirationTime.after(new Date()))) {
            throw new AppException(ErrorCode.TOKEN_IS_NOT_VALID);
        }

        if (invalidTokenService.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
            throw new AppException(ErrorCode.TOKEN_IS_NOT_VALID);
        }
        return signedJWT;
    }

    private String generateToken(User user, long timeAmout, ChronoUnit chronoUnit,String type) throws JOSEException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .claim("type", type)
                .claim("scope", buildScope(user)) // thêm scope
                .issuer("ModaMint")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(timeAmout, chronoUnit)))
                .jwtID(UUID.randomUUID().toString())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader,payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> stringJoiner.add(role.getName().name()));
        }
        return stringJoiner.toString();
    }


}

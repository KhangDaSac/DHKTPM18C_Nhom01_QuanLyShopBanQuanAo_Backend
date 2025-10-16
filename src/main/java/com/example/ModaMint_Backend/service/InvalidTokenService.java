package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.entity.InvalidToken;
import com.example.ModaMint_Backend.repository.InvalidTokenRepository;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InvalidTokenService {
    InvalidTokenRepository invalidTokenRepository;

    public boolean existsById(String jti) {
        return invalidTokenRepository.existsById(jti);
    }

    public void saveInvalidToken(SignedJWT jwt) throws ParseException {
        String jti = jwt.getJWTClaimsSet().getJWTID();
        Date expireAt = jwt.getJWTClaimsSet().getExpirationTime();

        InvalidToken invalidToken = InvalidToken.builder()
                .id(jti)
                .expireAt(expireAt)
                .build();
        invalidTokenRepository.save(invalidToken);
    }
}

package com.example.ModaMint_Backend.service;

import com.example.ModaMint_Backend.dto.request.auth.AuthenticationRequest;
import com.example.ModaMint_Backend.dto.request.auth.IntrospectRequest;
import com.example.ModaMint_Backend.dto.request.auth.LogoutRequest;
import com.example.ModaMint_Backend.dto.response.auth.AuthenticationResponse;
import com.example.ModaMint_Backend.dto.response.auth.IntrospectResponse;
import com.example.ModaMint_Backend.dto.response.auth.RefreshResponse;
import com.example.ModaMint_Backend.dto.response.user.UserResponse;
import com.example.ModaMint_Backend.entity.User;
import com.example.ModaMint_Backend.entity.Customer;
import com.example.ModaMint_Backend.entity.Role;
import com.example.ModaMint_Backend.enums.RoleName;
import com.example.ModaMint_Backend.exception.AppException;
import com.example.ModaMint_Backend.exception.ErrorCode;
import com.example.ModaMint_Backend.repository.UserRepository;
import com.example.ModaMint_Backend.repository.CustomerRepository;
import com.example.ModaMint_Backend.repository.RoleRepository;
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
import org.springframework.web.client.RestClient;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthenticationService {
    UserRepository userRepository;
    CustomerRepository customerRepository;
    RoleRepository roleRepository;
    @NonFinal
    @Value("${jwt.signer-key}")
    protected String SIGNER_KEY;
    PasswordEncoder passwordEncoder;
    RedisTokenService redisTokenService;
    UserMapper userMapper;
    RestClient.Builder restClientBuilder;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    protected String GOOGLE_CLIENT_ID;

    @NonFinal
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    protected String GOOGLE_CLIENT_SECRET;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthenticationResponse authenticateWithGoogle(String code, HttpServletResponse response)
            throws JOSEException {
        try {
            String tokenUrl = "https://oauth2.googleapis.com/token";
            String redirectUri = "http://localhost:5173/auth/google";
            RestClient restClient = restClientBuilder.build();

            String tokenResponse = restClient.post()
                    .uri(tokenUrl)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body("code=" + code +
                            "&client_id=" + GOOGLE_CLIENT_ID +
                            "&client_secret=" + GOOGLE_CLIENT_SECRET +
                            "&redirect_uri=" + redirectUri +
                            "&grant_type=authorization_code")
                    .retrieve()
                    .body(String.class);

            JsonNode tokenJson = objectMapper.readTree(tokenResponse);

            if (tokenJson.has("error")) {
                String error = tokenJson.get("error").asText();
                String errorDescription = tokenJson.has("error_description")
                        ? tokenJson.get("error_description").asText()
                        : "No description";
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }

            String googleAccessToken = tokenJson.get("access_token").asText();
            String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";
            String userInfoResponse = restClient.get()
                    .uri(userInfoUrl)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + googleAccessToken)
                    .retrieve()
                    .body(String.class);

            JsonNode userInfoJson = objectMapper.readTree(userInfoResponse);
            String googleEmail = userInfoJson.get("email").asText();
            String googleName = userInfoJson.has("name") ? userInfoJson.get("name").asText() : "";
            String googlePicture = userInfoJson.has("picture") ? userInfoJson.get("picture").asText() : "";

            User user = userRepository.findByEmail(googleEmail)
                    .orElse(null);

            if (user == null) {
                // Tạo user mới nếu chưa tồn tại
                String[] nameParts = googleName.split(" ", 2);
                String firstName = nameParts.length > 0 ? nameParts[0] : "";
                String lastName = nameParts.length > 1 ? nameParts[1] : "";
                String username = googleEmail.split("@")[0];

                int counter = 1;
                String originalUsername = username;
                while (userRepository.findByUsername(username).isPresent()) {
                    username = originalUsername + counter;
                    counter++;
                }

                Role customerRole = roleRepository.findByName(RoleName.CUSTOMER)
                        .orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));

                user = User.builder()
                        .username(username)
                        .email(googleEmail)
                        .firstName(firstName)
                        .lastName(lastName)
                        .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                        .image(googlePicture)
                        .roles(Set.of(customerRole))
                        .active(true)
                        .build();

                user = userRepository.save(user);

                // Tự động tạo Customer record cho user mới từ Google OAuth
                log.info("Creating Customer record for new Google OAuth user: {}", user.getId());
                try {
                    Customer customer = Customer.builder()
                            .customerId(user.getId())
                            .user(user)
                            .email(googleEmail)
                            .name(googleName)
                            .build();
                    customerRepository.save(customer);
                    log.info("Customer record created successfully for userId: {}", user.getId());
                } catch (Exception e) {
                    log.warn("Customer record already exists for userId: {} - {}", user.getId(), e.getMessage());
                }
            } else {
                // Cập nhật image nếu user tồn tại
                if (googlePicture != null && !googlePicture.isEmpty()) {
                    user.setImage(googlePicture);
                    userRepository.save(user);
                }

                // Đảm bảo Customer record tồn tại cho user hiện có
                if (!customerRepository.existsByCustomerId(user.getId())) {
                    log.info("Creating missing Customer record for existing user: {}", user.getId());
                    try {
                        Customer customer = Customer.builder()
                                .customerId(user.getId())
                                .user(user)
                                .email(googleEmail)
                                .name(googleName)
                                .build();
                        customerRepository.save(customer);
                        log.info("Customer record created successfully for existing userId: {}", user.getId());
                    } catch (Exception e) {
                        log.warn("Customer record already exists for userId: {} - {}", user.getId(), e.getMessage());
                    }
                }
            }

            // Tạo JWT token cho user
            String accessToken = generateToken(user, 1, ChronoUnit.HOURS, "access");
            String refreshToken = generateToken(user, 7, ChronoUnit.DAYS, "refresh");

            // Set refreshToken vào HttpOnly cookie
            Cookie cookie = new Cookie("refreshToken", refreshToken);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/refresh");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cookie);

            return AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .expiresIn(3600)
                    .tokenType("Bearer")
                    .build();

        } catch (Exception e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request,
                                               HttpServletResponse response) throws JOSEException {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // Đảm bảo Customer record tồn tại cho user đăng nhập thông thường
        if (!customerRepository.existsByCustomerId(user.getId())) {
            log.info("Creating missing Customer record for user during login: {}", user.getId());
            try {
                Customer customer = Customer.builder()
                        .customerId(user.getId())
                        .user(user)
                        .email(user.getEmail())
                        .name((user.getFirstName() != null ? user.getFirstName() : "") + " " +
                                (user.getLastName() != null ? user.getLastName() : "").trim())
                        .phone(user.getPhone())
                        .build();
                customerRepository.save(customer);
                log.info("Customer record created successfully for userId: {}", user.getId());
            } catch (Exception e) {
                // Customer đã tồn tại (race condition) - bỏ qua lỗi
                log.warn("Customer record already exists for userId: {} - {}", user.getId(), e.getMessage());
            }
        }

        // Tạo token
        String accessToken = generateToken(user, 1, ChronoUnit.HOURS, "access");
        String refreshToken = generateToken(user, 7, ChronoUnit.DAYS, "refresh");

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
                .refreshToken(refreshToken)
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

        // Kiểm tra Redis blacklist
        if (redisTokenService.isTokenBlacklisted(signedJWT.getJWTClaimsSet().getJWTID())) {
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

        String userId = signedJWT.getJWTClaimsSet().getSubject();

        // Try to find user by ID first
        Optional<User> userOptional = userRepository.findById(userId);
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            // Fallback: try to get username from claim for backward compatibility with old
            // tokens
            String username = signedJWT.getJWTClaimsSet().getStringClaim("username");
            if (username != null) {
                user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            } else {
                throw new AppException(ErrorCode.USER_NOT_EXISTED);
            }
        }

        String type = signedJWT.getJWTClaimsSet().getStringClaim("type");
        if (!"refresh".equals(type)) {
            throw new AppException(ErrorCode.TOKEN_IS_NOT_VALID); // token sai loại
        }

        String newAccessToken = generateToken(user, 1, ChronoUnit.HOURS, "access");
        return RefreshResponse.builder()
                .newAccessToken(newAccessToken)
                .build();
    }

    public void logout(LogoutRequest request, HttpServletResponse response) {
        try {
            if (request.getAccessToken() != null) {
                SignedJWT accessJWT = verify(request.getAccessToken());
                // Kiểm tra token type
                String type = accessJWT.getJWTClaimsSet().getStringClaim("type");
                if (!"access".equals(type)) {
                    throw new AppException(ErrorCode.TOKEN_IS_NOT_VALID);
                }
                // Chỉ blacklist nếu token còn hạn
                Date expireAt = accessJWT.getJWTClaimsSet().getExpirationTime();
                long ttlMillis = expireAt.getTime() - System.currentTimeMillis();

                if (ttlMillis > 0) {
                    String tokenId = accessJWT.getJWTClaimsSet().getJWTID();
                    redisTokenService.blacklistToken(tokenId, Duration.ofMillis(ttlMillis));
                    log.info("Access token {} blacklisted, expires in {}ms", tokenId, ttlMillis);
                } else {
                    log.info("Access token already expired, no need to blacklist");
                }
            }

            if (request.getRefreshToken() != null) {
                SignedJWT refreshJWT = verify(request.getRefreshToken());
                // Kiểm tra token type
                String type = refreshJWT.getJWTClaimsSet().getStringClaim("type");
                if (!"refresh".equals(type)) {
                    throw new AppException(ErrorCode.TOKEN_IS_NOT_VALID);
                }
                // Chỉ blacklist nếu token còn hạn
                Date expireAt = refreshJWT.getJWTClaimsSet().getExpirationTime();
                long ttlMillis = expireAt.getTime() - System.currentTimeMillis();

                if (ttlMillis > 0) {
                    String tokenId = refreshJWT.getJWTClaimsSet().getJWTID();
                    redisTokenService.blacklistToken(tokenId, Duration.ofMillis(ttlMillis));
                    log.info("Refresh token {} blacklisted, expires in {}ms", tokenId, ttlMillis);
                } else {
                    log.info("Refresh token already expired, no need to blacklist");
                }
            }

            // Xóa refresh token cookie
            Cookie cookie = new Cookie("refreshToken", null);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/refresh");
            cookie.setMaxAge(0); // Xóa cookie ngay lập tức
            response.addCookie(cookie);

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

        String userId = signedJWT.getJWTClaimsSet().getSubject();

        // Try to find user by ID first
        Optional<User> userOptional = userRepository.findById(userId);
        User user;

        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            // Fallback: try to get username from claim for backward compatibility with old
            // tokens
            String username = signedJWT.getJWTClaimsSet().getStringClaim("username");
            if (username != null) {
                user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            } else {
                throw new AppException(ErrorCode.USER_NOT_EXISTED);
            }
        }

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

        // Kiểm tra Redis blacklist
        String tokenId = signedJWT.getJWTClaimsSet().getJWTID();
        if (redisTokenService.isTokenBlacklisted(tokenId)) {
            throw new AppException(ErrorCode.TOKEN_IS_NOT_VALID);
        }
        return signedJWT;
    }

    private String generateToken(User user, long timeAmout, ChronoUnit chronoUnit, String type) throws JOSEException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId()) // Use user.id instead of username for subject
                .claim("type", type)
                .claim("scope", buildScope(user)) // thêm scope
                .claim("username", user.getUsername()) // Add username as a claim for backward compatibility
                .issuer("ModaMint")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(timeAmout, chronoUnit)))
                .jwtID(UUID.randomUUID().toString())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

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
package com.example.ModaMint_Backend.controller;

import com.example.ModaMint_Backend.dto.request.auth.AuthenticationRequest;
import com.example.ModaMint_Backend.dto.request.auth.IntrospectRequest;
import com.example.ModaMint_Backend.dto.request.auth.LogoutRequest;

import com.example.ModaMint_Backend.dto.response.*;
import com.example.ModaMint_Backend.dto.response.auth.AuthenticationResponse;
import com.example.ModaMint_Backend.dto.response.auth.IntrospectResponse;
import com.example.ModaMint_Backend.dto.response.auth.RefreshResponse;
import com.example.ModaMint_Backend.dto.response.user.UserResponse;
import com.example.ModaMint_Backend.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthenticationController {
    AuthenticationService authenticationService;
    private final RestClient.Builder builder;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response) throws JOSEException {

        AuthenticationResponse result = authenticationService.authenticate(request, response);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws JOSEException, ParseException {
        IntrospectResponse result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }



    @PostMapping("/refresh")
    ApiResponse<RefreshResponse> refresh(@CookieValue("refreshToken") String refreshToken)
            throws JOSEException, ParseException {
        RefreshResponse result = authenticationService.refresh(refreshToken);
        return ApiResponse.<RefreshResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .message("Logout successful")
                .build();
    }

    @GetMapping("/me")
    public ApiResponse<UserResponse> getCurrentUser(@RequestHeader("Authorization") String authHeader) throws ParseException, JOSEException {
        // Lấy access token từ Authorization header (Bearer token)
        String accessToken = authHeader.replace("Bearer ", "");
        UserResponse result = authenticationService.getCurrentUser(accessToken);
        return ApiResponse.<UserResponse>builder()
                .result(result)
                .build();
    }

}

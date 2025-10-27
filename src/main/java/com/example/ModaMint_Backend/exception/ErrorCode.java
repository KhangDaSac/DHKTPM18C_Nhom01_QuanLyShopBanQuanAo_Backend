package com.example.ModaMint_Backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User already exists", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not found", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(1005, "User not found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    TOKEN_IS_NOT_VALID(1007, "Token is not valid", HttpStatus.UNAUTHORIZED),
    PRODUCT_NOT_FOUND(2001, "Product not found", HttpStatus.NOT_FOUND),
    BRAND_NOT_FOUND(2002, "Brand not found", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(2003, "Category not found", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(2004, "Role not found", HttpStatus.NOT_FOUND),
    CUSTOMER_NOT_FOUND(2005, "Customer not found", HttpStatus.NOT_FOUND),
    CUSTOMER_ALREADY_EXISTS(2006, "Customer already exists", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND(2007, "Order not found", HttpStatus.NOT_FOUND),
    ORDER_ITEM_NOT_FOUND(2008, "Order item not found", HttpStatus.NOT_FOUND),
    REVIEW_NOT_FOUND(2009, "Review not found", HttpStatus.NOT_FOUND),
    PRODUCT_VARIANT_NOT_FOUND(2010, "Product variant not found", HttpStatus.NOT_FOUND),
    PROMOTION_NOT_FOUND(2011, "Promotion not found", HttpStatus.NOT_FOUND),
    PRODUCT_IMAGE_NOT_FOUND(2012, "Product image not found", HttpStatus.NOT_FOUND);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}

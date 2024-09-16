package com.dinhhieu.FruitWebApp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "UNCATEGORIZED error"),
    USER_EXISTED(1001, "User existed"),
    USERNAME_INVALID(1002, "Username must be at least 3 characters"),
    IllegalArgumentException(1003,"Search name must not be null or empty");
    ;
    private int code;
    private String message;
}

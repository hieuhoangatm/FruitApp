package com.dinhhieu.FruitWebApp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ResponData<T> implements Serializable {
    private final  int status;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public ResponData(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponData(int status, String message) {
        this.status = status;
        this.message = message;
    }
}

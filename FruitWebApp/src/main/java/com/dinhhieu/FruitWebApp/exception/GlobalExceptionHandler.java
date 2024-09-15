package com.dinhhieu.FruitWebApp.exception;

import com.aspose.words.Run;
import com.dinhhieu.FruitWebApp.dto.ApiResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationException(Exception e, WebRequest webRequest){
        ErrorResponse errorResponse= new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(BAD_REQUEST.value());
        errorResponse.setPath(webRequest.getDescription(false).replace("uri=",""));
        errorResponse.setError(BAD_REQUEST.getReasonPhrase());

        String message = e.getMessage();
        int start = message.lastIndexOf('[');
        int end = message.lastIndexOf(']');
        message = message.substring(start+1,end-1);
        errorResponse.setMessage(message);
        return  errorResponse;
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException runtimeException){
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse> handlingAppException(AppException runtimeException){
        ErrorCode errorCode = runtimeException.getErrorCode();
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
}

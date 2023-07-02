package com.example.course.common.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.course.common.exception.ErrorCode.INTERNAL_SERVER_ERROR;

/**
 * ResponseEntity 형태로 전역적으로 exception 을 리턴하는 클래스
 * throw new CustomException(ENUM_TYPE) 시 @ExceptionHandler 에서 받아서 처리
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {


    //expected error
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(CustomException customException) {
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");

        ErrorCode errorCode = customException.getErrorCode();
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        return new ResponseEntity<>(errorResponse, resHeaders, HttpStatus.resolve(errorCode.getStatus()));
    }

    //unexpected error
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception e) {
        e.printStackTrace();
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        ErrorCode errorCode = INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        return new ResponseEntity<>(errorResponse, resHeaders, HttpStatus.resolve(errorCode.getStatus()));
    }


    //security filter error
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BasicResponse> handle(BadRequestException e) {
        BasicResponse exceptionDto = new BasicResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }
}
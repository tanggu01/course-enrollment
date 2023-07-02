package com.example.course.src.auth.dto;

import lombok.Getter;

@Getter
public class LoginRequest {

    private String email;
    private String password;
    private String role;
}

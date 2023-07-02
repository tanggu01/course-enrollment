package com.example.course.src.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class SignUpResponse {
    private Long id;
    private String userRole;
}

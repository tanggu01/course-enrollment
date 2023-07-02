package com.example.course.src.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter
public abstract class BaseMember {

    private String name;

    private String email;

    private String password;

    private UserRole userRole;
    private LocalDateTime createdAt;

}

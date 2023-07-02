package com.example.course.src.domain;


import com.example.course.src.auth.dto.SignUpRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;

//    private String name;
//
//    private String email;
//
//    private String password;

    public static Admin createAdmin(String email) {
        Admin admin = new Admin();
//        admin.setEmail()
        return admin;
    }


    public static Admin createAdminByRequest(SignUpRequest request) {
        Admin admin = new Admin();
        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setPassword(request.getPassword());
        admin.setCreatedAt(LocalDateTime.now());
        admin.setUserRole(UserRole.valueOf(request.getRole()));
        return admin;
    }
}
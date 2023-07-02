package com.example.course.src.domain;

import com.example.course.src.auth.dto.SignUpRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Professor extends BaseMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_id")
    private Long id;

//    private String name;
//
//    private String email;
//
//    private String password;

    @OneToMany(mappedBy = "professor")
    private List<Course> courseList = new ArrayList<>();

    public static Professor createProfessorByRequest(SignUpRequest request) {
        Professor professor = new Professor();
        professor.setName(request.getName());
        professor.setEmail(request.getEmail());
        professor.setPassword(request.getPassword());
        professor.setCreatedAt(LocalDateTime.now());
        professor.setUserRole(UserRole.valueOf(request.getRole()));
        return professor;
    }


}

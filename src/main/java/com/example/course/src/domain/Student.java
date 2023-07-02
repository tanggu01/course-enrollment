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
public class Student extends BaseMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

//    private String name;
//
//    private String email;
//
//    private String password;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentCourse> studentCourseList = new ArrayList<>();

    public static Student createStudentByRequest(SignUpRequest request) {
        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPassword(request.getPassword());
        student.setCreatedAt(LocalDateTime.now());
        student.setUserRole(UserRole.valueOf(request.getRole()));
        return student;
    }

    public void addStudentCourse(StudentCourse studentCourse) {
        studentCourseList.add(studentCourse);
        studentCourse.setStudent(this);
    }


}

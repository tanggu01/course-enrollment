package com.example.course.src.professor.dto;

import com.example.course.src.domain.Student;
import lombok.Getter;

@Getter
public class StudentDTO {

    private Long studentId;
    private String name;
    private String email;

    public StudentDTO(Student student) {
        studentId = student.getId();
        name = student.getName();
        email = student.getEmail();
    }

}

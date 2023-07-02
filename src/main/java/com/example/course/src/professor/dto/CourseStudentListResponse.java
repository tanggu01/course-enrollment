package com.example.course.src.professor.dto;

import com.example.course.src.domain.Course;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;


@Getter
public class CourseStudentListResponse {

    private Long id;
    private String title;
    private int capacity;
    private String professor;
    private String status;

    private List<StudentDTO> studentDTOList;

    public CourseStudentListResponse(Course course) {
        id = course.getId();
        title = course.getTitle();
        capacity = course.getStudentCount();
        if (course.getProfessor() != null) {
            professor = course.getProfessor().getName();
        } else {
            professor = null;
        }
        status = course.getStatus().name();
        studentDTOList = course.getStudentCourseList().stream()
                .map(studentCourse -> new StudentDTO(studentCourse.getStudent()))
                .collect(Collectors.toList());
    }


}

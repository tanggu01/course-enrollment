package com.example.course.src.admin.dto;

import com.example.course.src.domain.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseListResponse {

    private Long id;
    private String title;
    private int capacity;
    private String professor;
    private String status;

    public CourseListResponse(Course course) {
        id = course.getId();
        title = course.getTitle();
        capacity = course.getStudentCount();
        if (course.getProfessor() != null) {
            professor = course.getProfessor().getName();
        } else {
            professor = null;
        }
        status = course.getStatus().name();
    }


}

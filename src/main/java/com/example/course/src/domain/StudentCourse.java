package com.example.course.src.domain;

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
public class StudentCourse {

    @Id
    @GeneratedValue
    @Column(name = "student_course_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    private LocalDateTime enrolledDate;

    //생성 메서드
    public static StudentCourse createStudentCourse(Student student, Course course) {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setCourse(course);
        studentCourse.setStudent(student);
        course.increaseStudentCount();
        studentCourse.setEnrolledDate(LocalDateTime.now());
        return studentCourse;
    }

    //연관관계 메서드
    public void setStudent(Student student) {
        this.student = student;
        student.getStudentCourseList().add(this);
    }

    public void setCourse(Course course) {
        this.course = course;
        course.getStudentCourseList().add(this);
    }


}

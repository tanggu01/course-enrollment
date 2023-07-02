package com.example.course.src.domain;

import com.example.course.src.course.dto.CreateCourseRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    private String title;

    @Column(name = "student_count")
    private int studentCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    @OneToMany(mappedBy = "course")
    private List<StudentCourse> studentCourseList;

    public static Course createCourse(CreateCourseRequest request) {
        Course course = new Course();
        course.setTitle(request.getTitle());
        course.setStudentCount(0);
        course.setStatus(CourseStatus.CLOSED);
        return course;
        //교수가 없으면 null로 들어갈텐데
    }

    //연관관계 편의 메서드
    public void setProfessor(Professor professor) {
        this.professor = professor;
        professor.getCourseList().add(this);
    }

    public void changeStatus() {
        this.status = CourseStatus.OPEN;
    }

    public void increaseStudentCount() {
        studentCount += 1;
    }

    public void decreaseStudentCount() {
        studentCount -= 1;
    }
}

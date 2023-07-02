package com.example.course.src.course;

import com.example.course.src.domain.Course;
import com.example.course.src.domain.CourseStatus;
import com.example.course.src.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findCoursesByProfessor(Professor professor);

    Course findCourseByProfessorIdAndId(Long professorId, Long courseId);

    @Query("select c from Course c join StudentCourse sc on c.id = sc.course.id where sc.student.id = :studentId")
    List<Course> findCoursesByStudentId(@Param("studentId") Long studentId);

    List<Course> findCoursesByProfessorId(Long professorId);

    List<Course> findCoursesByStatus(CourseStatus status);

}

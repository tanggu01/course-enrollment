package com.example.course.src.studentcourse;

import com.example.course.src.domain.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {

    @Query("select count(sc) from StudentCourse sc where sc.student.id = :id")
    long findStudentCountByStudentId(@Param("id") Long id);

    @Query("select count(sc) from StudentCourse sc where sc.course.id = :id")
    long findCourseCountByCourseId(@Param("id") Long id);

    StudentCourse findStudentCourseByCourseIdAndStudentId(Long courseId, Long studentId);

}

package com.example.course.src.student;

import com.example.course.src.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findStudentByEmail(String email);

    @Query("select s from Student s join StudentCourse sc where sc.course.id = :courseId")
    List<Student> findStudentsByCourseId(@Param("courseId") Long courseId);


}

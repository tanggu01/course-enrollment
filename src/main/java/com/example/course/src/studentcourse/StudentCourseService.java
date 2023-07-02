package com.example.course.src.studentcourse;

import com.example.course.common.exception.CustomException;
import com.example.course.config.handler.JwtHandler;
import com.example.course.src.course.CourseRepository;
import com.example.course.src.domain.Course;
import com.example.course.src.domain.Phase;
import com.example.course.src.domain.Student;
import com.example.course.src.domain.StudentCourse;
import com.example.course.src.phase.PhaseRepository;
import com.example.course.src.student.StudentRepository;
import com.example.course.src.student.dto.EnrollRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Period;

import static com.example.course.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final PhaseRepository phaseRepository;

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final JwtHandler jwtHandler;


    /**
     * 수강신청 API
     * STUDENT
     *
     * @param enrollRequest
     * @return
     */
    @Transactional
    public void enroll(EnrollRequest enrollRequest) {
        Student student = studentRepository.findById(jwtHandler.getMemberId())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        Course course = courseRepository.findById(enrollRequest.getCourseId())
                .orElseThrow(() -> new CustomException(COURSE_NOT_EXIST));
        checkStudentEnrollLimit(student);
        checkCourseIsNotFull(course);
        StudentCourse studentCourse = StudentCourse.createStudentCourse(student, course);
        studentCourseRepository.save(studentCourse);
    }

    /**
     * 수강하려는 강의 정원 초과인지 검증
     *
     * @param course
     */
    public void checkCourseIsNotFull(Course course) {
        long courseCountByCourseId = studentCourseRepository.findCourseCountByCourseId(course.getId());
        if (courseCountByCourseId >= 15L) {
            throw new CustomException(COURSE_FULL_EXCEPTION);
        }
    }

    /**
     * 학생 강의 수강신청 개수 2개 이하인지 검증
     *
     * @param student
     */
    public void checkStudentEnrollLimit(Student student) {
        long studentCountByStudentId = studentCourseRepository.findStudentCountByStudentId(student.getId());
        if (studentCountByStudentId >= 3L) {
            throw new CustomException(ENROLL_LIMIT_EXCEPTION);
        }
    }


    /**
     * 수강신청 철회 API
     * STUDENT
     *
     * @param courseId
     */
    @Transactional
    public void deleteEnrolledCourse(Long courseId) { //exception
        Long studentId = jwtHandler.getMemberId();
        if (!checkIsValidTimeToWithdraw()) throw new CustomException(UNABLE_TO_WITHDRAW_COURSE);
        StudentCourse studentCourse = studentCourseRepository.findStudentCourseByCourseIdAndStudentId(courseId, studentId);
        if (studentCourse != null) {
            studentCourse.getCourse().decreaseStudentCount();
            studentCourseRepository.delete(studentCourse);
        } else {
            throw new CustomException(COURSE_NOT_EXIST);
        }
    }


    public boolean checkIsValidTimeToWithdraw() {
        if (phaseRepository.findAll().isEmpty()) throw new CustomException(PHASE_NOT_EXIST);
        Phase phase = phaseRepository.findAll().get(0);
        LocalDateTime endDate = phase.getEndDate();
        Period diff = Period.between(LocalDateTime.now().toLocalDate(), endDate.toLocalDate());
        return diff.getMonths() < 1 && (diff.getDays() > 3);
    }

}

package com.example.course.src.student;

import com.example.course.common.exception.CustomException;
import com.example.course.common.exception.ErrorCode;
import com.example.course.src.auth.dto.SignUpRequest;
import com.example.course.src.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository studentRepository;


    @Transactional
    public Student create(SignUpRequest signUpRequest) {
        if (studentRepository.findStudentByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.MEMBER_DUPLICATED);
        }
        Student student = Student.createStudentByRequest(signUpRequest);
        return studentRepository.save(student);
    }

//    /**
//     * 수강신청 API
//     * studentService /courseService??
//     *
//     * @param enrollRequest
//     * @return
//     */
//    @Transactional
//    public void enroll(EnrollRequest enrollRequest) {
//        Student student = studentRepository.findById(jwtHandler.getMemberId())
//                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
//        Course course = courseRepository.findById(enrollRequest.getCourseId())
//                .orElseThrow(() -> new CustomException(COURSE_NOT_EXIST));
//        checkStudentEnrollLimit(student);
//        checkCourseIsNotFull(course);
//        StudentCourse studentCourse = StudentCourse.createStudentCourse(student, course);
//        studentCourseRepository.save(studentCourse);
//    }
//
//    public void checkCourseIsNotFull(Course course) {
//        long courseCountByCourseId = studentCourseRepository.findCourseCountByCourseId(course.getId());
//        if (courseCountByCourseId >= 15L) {
//            throw new CustomException(COURSE_FULL_EXCEPTION);
//        }
//    }
//
//    public void checkStudentEnrollLimit(Student student) {
//        long studentCountByStudentId = studentCourseRepository.findStudentCountByStudentId(student.getId());
//        if (studentCountByStudentId >= 3L) {
//            throw new CustomException(ENROLL_LIMIT_EXCEPTION);
//        }
//    }

//    /**
//     * 강의 목록 조회 API
//     */
//    public List<CourseListResponse> getCourseList() {
//        List<Course> courseList = courseRepository.findAll();
//        return courseList.stream()
//                .map(CourseListResponse::new)
//                .collect(Collectors.toList());
//    }

//    /**
//     * 수강신청 목록 조회 API
//     */
//    public List<CourseListResponse> getEnrolledCourseList() {
//        Long studentId = jwtHandler.getMemberId();
//        List<Course> coursesByStudentId = courseRepository.findCoursesByStudentId(studentId);
//        return coursesByStudentId.stream()
//                .map(CourseListResponse::new)
//                .collect(Collectors.toList());
//    }
//
//    @Transactional
//    public void deleteEnrolledCourse(Long courseId) { //exception
//        Long studentId = jwtHandler.getMemberId();
//        studentCourseRepository.deleteStudentCourseByCourseIdAndStudentId(courseId, studentId);
//    }
}

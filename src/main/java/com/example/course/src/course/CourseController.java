package com.example.course.src.course;

import com.example.course.src.course.dto.CourseListResponse;
import com.example.course.src.course.dto.CourseStudentListResponse;
import com.example.course.src.course.dto.CreateCourseRequest;
import com.example.course.src.course.dto.CreateCourseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    /**
     * 강의 등록 API
     * By UserRole - ADMIN
     *
     * @param request
     * @return
     */
    @PostMapping("")
    public ResponseEntity<CreateCourseResponse> createCourse(@RequestBody CreateCourseRequest request) {
        CreateCourseResponse response = courseService.createCourse(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    /**
     * 강의 목록 조회 API
     * By UserRole - ADMIN, STUDENT
     */
    @GetMapping("")
    public ResponseEntity<List<CourseListResponse>> getCourseList() {
        List<CourseListResponse> courseList = courseService.getCourseList();
        return new ResponseEntity<>(courseList, HttpStatus.OK);
    }

    /**
     * 수강신청 목록 조회 API
     * By UserRole - STUDENT
     */
    @GetMapping("/students")
    public ResponseEntity<List<CourseListResponse>> getEnrolledCourseList() {
        List<CourseListResponse> courseList = courseService.getEnrolledCourseList();
        return new ResponseEntity<>(courseList, HttpStatus.OK);
    }


    /**
     * 교수 강의 추가 API
     * By UserRole - PROFESSOR
     *
     * @param courseId
     * @return
     */
    @PatchMapping("/professors/{courseId}")
    public ResponseEntity<String> registerCourse(@PathVariable Long courseId) {
        courseService.registerCourse(courseId);
        return new ResponseEntity<>("등록에 성공하였습니다.", HttpStatus.OK);
    }


    /**
     * 교수 강의 목록 조회 API
     * By UserRole - PROFESSOR
     *
     * @return
     */
    @GetMapping("/professors")
    public ResponseEntity<List<CourseListResponse>> getRegisteredCourse() {
        List<CourseListResponse> registeredCourse = courseService.getRegisteredCourse();
        return new ResponseEntity<>(registeredCourse, HttpStatus.OK);
    }


    /**
     * 강의 열기 API
     * By UserRole - PROFESSOR
     *
     * @param courseId
     */
    @PatchMapping("/professors/status/{courseId}")
    public ResponseEntity<String> openCourseStatus(@PathVariable Long courseId) {
        courseService.openCourseStatus(courseId);
        return new ResponseEntity<>("강의 상태 변경에 성공하였습니다.", HttpStatus.OK);
    }

    /**
     * 수강신청 + 학생 정보 API
     * By UserRole - PROFESSOR
     *
     * @return
     */
    @GetMapping("/professors/students")
    public ResponseEntity<List<CourseStudentListResponse>> getCourseWithStudentList() {
        List<CourseStudentListResponse> responseList = courseService.findCourseWithStudentList();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }


}

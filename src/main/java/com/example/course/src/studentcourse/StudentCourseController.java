package com.example.course.src.studentcourse;


import com.example.course.src.studentcourse.dto.EnrollRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student-course")
public class StudentCourseController {

    private final StudentCourseService studentCourseService;


    /**
     * 수강신청 등록 API
     * By UserRole - STUDENT
     *
     * @param enrollRequest
     * @return
     */
    @PostMapping()
    public ResponseEntity<String> enroll(@RequestBody EnrollRequest enrollRequest) {
        studentCourseService.enroll(enrollRequest);
        return new ResponseEntity<>("수강신청에 성공했습니다.", HttpStatus.OK);
    }

    /**
     * 수강신청 철회 API
     * By UserRole - STUDENT
     *
     * @param courseId
     */
    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteEnrolledCourse(@PathVariable Long courseId) {
        studentCourseService.deleteEnrolledCourse(courseId);
        return new ResponseEntity<>("요청에 성공했습니다.", HttpStatus.OK);
    }

}

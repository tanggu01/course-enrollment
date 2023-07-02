package com.example.course.src.student;

import com.example.course.src.auth.dto.SignUpRequest;
import com.example.course.src.auth.dto.SignUpResponse;
import com.example.course.src.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;


    /**
     * 학생 회원가입 API
     *
     * @param signUpRequest
     * @return
     */
    @PostMapping("")
    public ResponseEntity<SignUpResponse> signup(@RequestBody SignUpRequest signUpRequest) {
        Student student = studentService.create(signUpRequest);
        return new ResponseEntity<>(new SignUpResponse(student.getId(), student.getUserRole().name()), HttpStatus.CREATED);
    }
}

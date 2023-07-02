package com.example.course.src.professor;

import com.example.course.src.auth.dto.SignUpRequest;
import com.example.course.src.auth.dto.SignUpResponse;
import com.example.course.src.domain.Professor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/professors")
public class ProfessorController {

    private final ProfessorService professorService;

    /**
     * 교수 회원가입 API
     *
     * @param signUpRequest
     * @return
     */
    @PostMapping("")
    public ResponseEntity<SignUpResponse> signup(@RequestBody SignUpRequest signUpRequest) {
        Professor professor = professorService.create(signUpRequest);
        return new ResponseEntity<>(new SignUpResponse(professor.getId(), professor.getUserRole().name()), HttpStatus.CREATED);
    }
}

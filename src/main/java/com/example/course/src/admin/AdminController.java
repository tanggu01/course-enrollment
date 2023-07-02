package com.example.course.src.admin;

import com.example.course.src.auth.dto.SignUpRequest;
import com.example.course.src.auth.dto.SignUpResponse;
import com.example.course.src.domain.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    /**
     * ADMIN 계정 생성 API
     *
     * @param signUpRequest
     * @return
     */
    @PostMapping("")
    public ResponseEntity<SignUpResponse> signup(@RequestBody SignUpRequest signUpRequest) {
        Admin admin = adminService.create(signUpRequest);
        return new ResponseEntity<>(new SignUpResponse(admin.getId(), admin.getUserRole().name()), HttpStatus.CREATED);
    }
}

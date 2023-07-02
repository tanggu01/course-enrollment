package com.example.course.src.phase;

import com.example.course.src.admin.dto.PhaseRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/phase")
public class PhaseController {

    private final PhaseService phaseService;

    /**
     * 수강신청 기간 등록 API
     * By UserRole - ADMIN
     *
     * @param request
     * @return
     */
    @PostMapping("")
    public ResponseEntity<String> setEnrollmentPhase(@RequestBody PhaseRequest request) {
        phaseService.setPhase(request);
        return new ResponseEntity<>("수강기간 설정 성공.", HttpStatus.OK);
    }


    /**
     * 수강신청 기간 수정 API
     * By UserRole - ADMIN
     *
     * @param request
     * @return
     */
    @PutMapping("")
    public ResponseEntity<String> changeEnrollmentPhase(@RequestBody PhaseRequest request) {
        phaseService.changePhase(request);
        return new ResponseEntity<>("수강기간 변경 성공.", HttpStatus.OK);
    }

}

package com.example.course.src.professor;

import com.example.course.common.exception.CustomException;
import com.example.course.common.exception.ErrorCode;
import com.example.course.src.auth.dto.SignUpRequest;
import com.example.course.src.domain.Professor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
class ProfessorService {

    private final ProfessorRepository professorRepository;

    /**
     * 회원가입 API
     *
     * @param signUpRequest
     * @return
     */
    @Transactional
    public Professor create(SignUpRequest signUpRequest) {
        if (professorRepository.findProfessorByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.MEMBER_DUPLICATED);
        }
        Professor professor = Professor.createProfessorByRequest(signUpRequest);
        return professorRepository.save(professor);
    }


}

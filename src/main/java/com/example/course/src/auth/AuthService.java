package com.example.course.src.auth;

import com.example.course.common.exception.CustomException;
import com.example.course.src.admin.AdminRepository;
import com.example.course.src.auth.dto.LoginRequest;
import com.example.course.src.auth.dto.LoginResponse;
import com.example.course.src.domain.*;
import com.example.course.src.professor.ProfessorRepository;
import com.example.course.src.student.StudentRepository;
import com.example.course.utils.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.course.common.exception.ErrorCode.*;

@Service
@AllArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final TokenService tokenService;


    /**
     * 로그인 API
     * UserRole - permit all
     *
     * @param request
     * @return
     */
    public LoginResponse login(LoginRequest request) {
        String role = request.getRole(); //UserRole로 찾을 MemberRepository 구분
        TokenService.PrivateClaims privateClaims;
        String accessToken;
        switch (role) {
            case "ADMIN":
                Admin admin = adminRepository.findAdminByEmail(request.getEmail())
                        .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
                validatePassword(admin, request.getPassword());
                privateClaims = createPrivateClaims(admin.getId(), admin.getUserRole());
                break;
            case "STUDENT":
                Student student = studentRepository.findStudentByEmail(request.getEmail())
                        .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
                validatePassword(student, request.getPassword());
                privateClaims = createPrivateClaims(student.getId(), student.getUserRole());
                break;
            case "PROFESSOR":
                Professor professor = professorRepository.findProfessorByEmail(request.getEmail())
                        .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
                validatePassword(professor, request.getPassword());
                privateClaims = createPrivateClaims(professor.getId(), professor.getUserRole());
                break;
            default:
                throw new CustomException(ROLE_NOT_FOUND);
        }
        //토큰 발급
        accessToken = tokenService.createAccessToken(privateClaims);
        return new LoginResponse(role, accessToken);
    }


    public void validatePassword(BaseMember member, String password) {
        if (!member.getPassword().equals(password)) {
            throw new CustomException(LOGIN_FAIL_EXCEPTION);
        }
    }

    public TokenService.PrivateClaims createPrivateClaims(Long memberId, UserRole memberRole) {
        return new TokenService.PrivateClaims(String.valueOf(memberId), memberRole);
    }

}

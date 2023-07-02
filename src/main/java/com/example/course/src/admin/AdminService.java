package com.example.course.src.admin;

import com.example.course.common.exception.CustomException;
import com.example.course.common.exception.ErrorCode;
import com.example.course.src.auth.dto.SignUpRequest;
import com.example.course.src.domain.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final AdminRepository adminRepository;

//    /**
//     * Course 생성
//     *
//     * @param request
//     * @return
//     */
//    @Transactional
//    public CreateCourseResponse createCourse(CreateCourseRequest request) {
//        //check whether USER_ROLE is ADMIN in MemberGuard for this uri
//        //so no 인가 needed in Service
//        //Long memberId = jwtHandler.getMemberId();
//        try {
//            Course savedCourse = courseRepository.save(Course.createCourse(request));
//            return new CreateCourseResponse(savedCourse.getId());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new CreateCourseResponse(1L);
//    }

//    /**
//     * 등록된 모든 CourseList 반환
//     *
//     * @return
//     */
//    public List<CourseListResponse> getCourseList() {
//        List<Course> courseList = courseRepository.findAll();
//        return courseList.stream()
//                .map(CourseListResponse::new)
//                .collect(Collectors.toList());
//    }

    /**
     * @param signUpRequest
     * @return
     */
    @Transactional
    public Admin create(SignUpRequest signUpRequest) {
        if (adminRepository.findAdminByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.MEMBER_DUPLICATED);
        }
        Admin admin = Admin.createAdminByRequest(signUpRequest);
        return adminRepository.save(admin);
    }


//    /**
//     * 수강신청 기간 등록
//     *
//     * @param request
//     */
//    @Transactional
//    public void setPhase(PhaseRequest request) {
//        if (!checkPeriodIsValid(request)) throw new CustomException(INVALID_PHASE_PERIOD);
//        if (!isCourseAllOpen()) throw new CustomException(COURSE_NOT_OPEN);
//        if (!isEnoughCourse()) throw new CustomException(NOT_ENOUGH_COURSE);
//        if (!isAllProfessorPresent()) throw new CustomException(NOT_ENOUGH_PROFESSOR);
//        Phase phase = Phase.createPhase(request);
//        phaseRepository.save(phase);
//    }
//
//    public boolean checkPeriodIsValid(PhaseRequest request) {
//        LocalDateTime startDate = request.getStartDate();
//        LocalDateTime endDate = request.getEndDate();
//        Period diff = Period.between(startDate.toLocalDate(), endDate.toLocalDate());
//        return diff.getMonths() < 1 && ((diff.getDays() >= 14 && diff.getDays() <= 28));
//    }
//
//    public boolean isCourseAllOpen() {
//        List<Boolean> collect = courseRepository.findAll()
//                .stream()
//                .map(course -> course.getStatus().equals(CourseStatus.CLOSED))
//                .collect(Collectors.toList());
//        return collect.size() == 0;
//    }
//
//    public boolean isEnoughCourse() {
//        return courseRepository.findAll().size() > 5;
//    }
//
//    public boolean isAllProfessorPresent() {
//        List<Course> courseList = courseRepository.findAll();
//        HashSet<Long> set = new HashSet<>();
//        for (Course course : courseList) {
//            set.add(course.getProfessor().getId());
//        }
//        return set.size() == 3;
//    }

//    /**
//     * 수강신청 기간 변경
//     *
//     * @param request
//     */
//    @Transactional
//    public void changePhase(PhaseRequest request) {
//        Phase phase = phaseRepository.findById(1L).orElseThrow(() -> new CustomException(PHASE_NOT_EXIST));
//        phase.changePhase(request);
//    }

}

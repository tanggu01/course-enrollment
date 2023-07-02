package com.example.course.src.course;

import com.example.course.common.exception.CustomException;
import com.example.course.config.handler.JwtHandler;
import com.example.course.src.admin.dto.CourseListResponse;
import com.example.course.src.admin.dto.CreateCourseRequest;
import com.example.course.src.admin.dto.CreateCourseResponse;
import com.example.course.src.domain.Course;
import com.example.course.src.domain.Phase;
import com.example.course.src.domain.Professor;
import com.example.course.src.phase.PhaseRepository;
import com.example.course.src.professor.ProfessorRepository;
import com.example.course.src.professor.dto.CourseStudentListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.course.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final PhaseRepository phaseRepository;
    private final JwtHandler jwtHandler;

    /**
     * Course 생성
     * By UserRole - ADMIN
     *
     * @param request
     * @return
     */
    @Transactional
    public CreateCourseResponse createCourse(CreateCourseRequest request) {
        Course savedCourse = courseRepository.save(Course.createCourse(request));
        return new CreateCourseResponse(savedCourse.getId());
    }

    /**
     * 강의 목록 조회
     * STUDENT,ADMIN,
     */
    public List<CourseListResponse> getCourseList() {
        List<Course> courseList = courseRepository.findAll();
        return courseList.stream()
                .map(CourseListResponse::new)
                .collect(Collectors.toList());
    }


    /**
     * 수강신청 목록 조회
     * STUDENT
     */
    public List<CourseListResponse> getEnrolledCourseList() {
        Long studentId = jwtHandler.getMemberId();
        List<Course> coursesByStudentId = courseRepository.findCoursesByStudentId(studentId);
        return coursesByStudentId.stream()
                .map(CourseListResponse::new)
                .collect(Collectors.toList());
    }


    /**
     * 강의 등록 API
     *
     * @param courseId
     */
    @Transactional
    public void registerCourse(Long courseId) {
        Professor professor = findProfessorById();
        Course course = validateCourseRegisterRequest(courseId, professor);
        course.setProfessor(professor);
    }

    public Professor findProfessorById() {
        Long professorId = jwtHandler.getMemberId();
        return professorRepository.findById(professorId).orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }

    /**
     * 없는 강의 id이거나 다른 교수에 의해 등록된 강의인지 validate
     *
     * @param courseId
     * @param professor
     * @return
     * @throws CustomException
     */
    public Course validateCourseRegisterRequest(Long courseId, Professor professor) throws CustomException {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CustomException(COURSE_NOT_EXIST));
        if (professor.getCourseList().size() >= 2) throw new CustomException(COURSE_REGISTER_LIMIT_EXCEEDED);
        if (course.getProfessor() != null) throw new CustomException(COURSE_OCCUPIED);
        return course;
    }

    /**
     * 교수 강의 목록 조회 API
     *
     * @return
     */
    public List<CourseListResponse> getRegisteredCourse() {
        Professor professor = findProfessorById();
        List<Course> coursesByProfessor = courseRepository.findCoursesByProfessor(professor);
        return coursesByProfessor.stream()
                .map(CourseListResponse::new)
                .collect(Collectors.toList());
    }


    /**
     * 교수 강의 열기 API
     */
    @Transactional
    public void openCourseStatus(Long courseId) {
        Long professorId = jwtHandler.getMemberId();
        List<Course> coursesByProfessorId = courseRepository.findCoursesByProfessorId(professorId);
        if (coursesByProfessorId == null) throw new CustomException(MODIFY_NOT_ALLOWED);
        Course courseToOpen = courseRepository.findCourseByProfessorIdAndId(professorId, courseId);
        if (courseToOpen == null) throw new CustomException(COURSE_NOT_EXIST);
        courseToOpen.changeStatus();
    }

    /**
     * 수강신청 + 학생 정보 API
     *
     * @return
     */
    public List<CourseStudentListResponse> findCourseWithStudentList() {
        if (phaseRepository.findAll().isEmpty()) throw new CustomException(PHASE_NOT_EXIST);
        Phase phase = phaseRepository.findAll().get(0);
        if (LocalDateTime.now().isBefore(phase.getEndDate()))
            throw new CustomException(PHASE_NOT_ENDED);
        Long professorId = jwtHandler.getMemberId();
        List<Course> courseList = courseRepository.findCoursesByProfessorId(professorId);

        if (courseList == null) throw new CustomException(COURSE_NOT_EXIST);
        List<CourseStudentListResponse> result = courseList.stream()
                .map(course -> new CourseStudentListResponse(course))
                .collect(Collectors.toList());
        return result;
    }


}

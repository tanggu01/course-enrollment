package com.example.course.src.phase;

import com.example.course.common.exception.CustomException;
import com.example.course.src.admin.dto.PhaseRequest;
import com.example.course.src.course.CourseRepository;
import com.example.course.src.domain.Course;
import com.example.course.src.domain.CourseStatus;
import com.example.course.src.domain.Phase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashSet;
import java.util.List;

import static com.example.course.common.exception.ErrorCode.*;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PhaseService {

    private final PhaseRepository phaseRepository;
    private final CourseRepository courseRepository;

    /**
     * 수강신청 기간 등록
     *
     * @param request
     */
    @Transactional
    public void setPhase(PhaseRequest request) {
        if (!checkPeriodIsValid(request)) throw new CustomException(INVALID_PHASE_PERIOD);
        if (!isCourseAllOpen()) throw new CustomException(COURSE_NOT_OPEN);
        if (!isEnoughCourse()) throw new CustomException(NOT_ENOUGH_COURSE);
        if (!isAllProfessorPresent()) throw new CustomException(NOT_ENOUGH_PROFESSOR);
        Phase phase = Phase.createPhase(request);
        phaseRepository.save(phase);
    }

    public boolean checkPeriodIsValid(PhaseRequest request) {
        LocalDateTime startDate = request.getStartDate();
        LocalDateTime endDate = request.getEndDate();
        Period diff = Period.between(startDate.toLocalDate(), endDate.toLocalDate());
        return diff.getMonths() < 1 && ((diff.getDays() >= 14 && diff.getDays() <= 28));
    }

    public boolean isCourseAllOpen() {
        List<Course> coursesByStatus = courseRepository.findCoursesByStatus(CourseStatus.CLOSED);
        return coursesByStatus.size() == 0;
    }

    public boolean isEnoughCourse() {
        return courseRepository.findAll().size() > 5;
    }

    public boolean isAllProfessorPresent() {
        List<Course> courseList = courseRepository.findAll();
        HashSet<Long> set = new HashSet<>();
        for (Course course : courseList) {
            set.add(course.getProfessor().getId());
        }
        return set.size() == 3;
    }


    /**
     * 수강신청 기간 변경
     *
     * @param request
     */
    @Transactional
    public void changePhase(PhaseRequest request) {
        if (phaseRepository.findAll().size() > 0) {
            Phase phase = phaseRepository.findAll().get(0);
            phase.changePhase(request);
        } else {
            throw new CustomException(PHASE_NOT_EXIST);
        }
    }
}

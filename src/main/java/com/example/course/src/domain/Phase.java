package com.example.course.src.domain;


import com.example.course.src.phase.dto.PhaseRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Phase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phase_id")
    private Long id;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public static Phase createPhase(PhaseRequest request) {
        Phase phase = new Phase();
        phase.setStartDate(request.getStartDate());
        phase.setEndDate(request.getEndDate());
        return phase;
    }

    public void changePhase(PhaseRequest request) {
        this.setEndDate(request.getEndDate());
        this.setStartDate(request.getStartDate());
    }
}

package com.tjoeun.corunning.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crew_application")

public class CrewApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 신청 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crew_board_id", nullable = false)
    private CrewBoard crewBoard; // 어떤 게시글에 대한 신청인지

    @Column(nullable = false)
    private String applicantId;   // 신청자 회원 ID (User.userId)

    @Column(nullable = false)
    private String applicantName; // 신청자 이름

    private String birthDate;
    private String hireDate;
    private String phone;
    private String userAddress;

    @Column(nullable = false)
    private LocalDateTime appliedAt; // 신청 시각
}

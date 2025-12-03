package com.tjoeun.corunning.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "crew_board")
public class CrewBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // 게시글 번호

    @Column(nullable = false)
    private String title;   // 제목

    @Column(nullable = false, length = 2000)
    private String content; // 내용

    @Column(nullable = false)
    private String writerId; // 작성자 ID (User.userId)

    @Column(nullable = false)
    private String region;   // 지역 (예: 서울/인천 등)

    // 게시판 유형: NORMAL / FLASH / DRAWING
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoardType boardType;

    // 모집 인원 (최대 인원)
    @Column(nullable = false)
    private int recruitCount;

    // 현재 신청 인원
    @Column(nullable = false)
    private int currentCount;

    // 모집 마감일 
    @Column(nullable = false)
    private LocalDate deadline;

    // 경로 좌표를 JSON 문자열로 저장
    @Column(length = 4000)
    private String routePathJson;


    @Column(nullable = false)
    private LocalDateTime createdAt;
    
   
}

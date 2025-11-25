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
public class CrewComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글 작성자 (세션에 있는 loginUserId 저장)
    @Column(nullable = false)
    private String writerId;

    @Column(nullable = false, length = 1000)
    private String content;

    // 어떤 모집글의 댓글인지 (N:1 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private CrewBoard board;

    private LocalDateTime createdAt;
}

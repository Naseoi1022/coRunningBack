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
public class RouteComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 댓글 작성자 (세션에 있는 loginUserId 저장)
    @Column(nullable = false)
    private String writerId;
    
    @Column(nullable=false)
    private String writer_name;
    
    @Column(nullable=false)
    private Long routeId;
    
    @Column(nullable = false, length = 1000)
    private String content;
    
    private LocalDateTime createdAt;
}

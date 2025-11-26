package com.tjoeun.corunning.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class RouteLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;          // 단일 PK

    @Column(nullable = false)
    private String userId;    // 유저 식별자 (String이면 String 그대로)

    @Column(nullable = false)
    private Long routeId;     // 경로 ID
}

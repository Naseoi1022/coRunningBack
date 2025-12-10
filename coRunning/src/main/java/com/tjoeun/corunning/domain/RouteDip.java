package com.tjoeun.corunning.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
public class RouteDip {

    @Id
    @SequenceGenerator(
    		name="dip",
    		sequenceName="Dip_SEQ",
    		allocationSize=1
    		)
	@GeneratedValue(generator="dip")
    private Long id;          // 단일 PK

    @Column(nullable = false)
    private String userId;    // 유저 식별자 (String이면 String 그대로)

    private Long routeId;     // 경로 ID
    
    private String record; // 필드 기본값 지정
    
    @Column(nullable = false)
    private boolean complete = false;
    
    private String title;
    private double distance;
    private String location;
    
    
}

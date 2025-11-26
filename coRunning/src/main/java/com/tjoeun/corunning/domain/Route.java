package com.tjoeun.corunning.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Route {
	@Id
	@SequenceGenerator(
		name="no",
		sequenceName="Route_SEQ",
		allocationSize=1
		)
	@GeneratedValue(generator="no")
	private Long route_id;
	
	@Column(nullable=false)
	private String writer;
	
	@Column(columnDefinition="CLOB")
	private String content;
	
	@Column(nullable=false, columnDefinition="CLOB")
	private String route;
	
	@CreatedDate
	@Column(name="created_at")
	private LocalDateTime createAt;
	
	@Column(columnDefinition="NUMBER DEFAULT 0")
	private Long liked;
	
	@Column(nullable=false)
	private Long distance;
	
	@Column(nullable=false)
	private String location;
	
}

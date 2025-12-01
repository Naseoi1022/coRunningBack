package com.tjoeun.corunning.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
public class Route {
	@Id
	@SequenceGenerator(
		name="no",
		sequenceName="Route_SEQ",
		allocationSize=1
		)
	@GeneratedValue(generator="no")
	@Column(name="route_id")
	private Long route_id;
	
	@Column(nullable=false)
	private String writer;
	
	@Column(nullable=false)
	private String title;
	
	@Lob
	@Column(nullable=false, columnDefinition="CLOB")
	private String route;
	
	@Lob
	@Column(columnDefinition="CLOB")
	private String description;
	
	@CreatedDate
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	@Column(columnDefinition="NUMBER DEFAULT 0")
	private Long liked;
	
	@Column(nullable=false)
	private double distance;
	
	@Column(nullable=false)
	private String location;
	
	@Column(nullable=false)
	private String difficulty;
	
	@Column(nullable=false)
	private String type;
}

package com.tjoeun.corunning.dto;

import com.tjoeun.corunning.domain.BoardType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class CrewBoardRequestDTO {

    private String title;
    private String content;
    private String region;
    private BoardType boardType;      // NORMAL / FLASH / DRAWING
    private int recruitCount;
    private LocalDate deadline;
    private String routePathJson;     // 배열을 JSON 문자열로
}

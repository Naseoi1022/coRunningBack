package com.tjoeun.corunning.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyPageDTO {
    private String record;    // "2025-12-04 01:23:45"
    private Double distance;  // 7.8
}

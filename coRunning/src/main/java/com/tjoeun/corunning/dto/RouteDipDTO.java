package com.tjoeun.corunning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RouteDipDTO {
	private Long dipId;
    private Long routeId;
    private String record;
    private boolean complete;
    private String title;
    private double distance;
    private String location;
}

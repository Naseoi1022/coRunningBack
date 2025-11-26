package com.tjoeun.corunning.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.corunning.domain.Route;
import com.tjoeun.corunning.service.RouteService;

@RestController
@RequestMapping("/api/routes")
public class RouteController {
	private final RouteService routeService;
	
	public RouteController(RouteService routeService) {
		this.routeService = routeService;
	}
	
	@PostMapping
	public Route uploadRoute(@RequestBody Route route) {
		return routeService.uploadRoute(route);
	}
	
	@GetMapping
	public List<Route> getAllRoutes(){
		return routeService.getAllRoutes();
	}
	@GetMapping("/{id}")
	public Route getRouteById(@PathVariable("id") Long id) {
		return routeService.getRouteById(id);
	}
}

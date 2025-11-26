package com.tjoeun.corunning.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.corunning.domain.Route;
import com.tjoeun.corunning.exception.CustomException;
import com.tjoeun.corunning.service.RouteService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/routes")
public class RouteController {
	private final RouteService routeService;
	
	public RouteController(RouteService routeService) {
		this.routeService = routeService;
	}
	
	//코스 업로드
	@PostMapping
	public ResponseEntity<?> uploadRoute(@RequestBody Route route, HttpSession session) {
	    String loginUserId = (String) session.getAttribute("loginUserId");
	    if (loginUserId == null) {
	    	throw new CustomException("로그인이 필요합니다.");// 401 상태와 메시지 반환
	    }
	    route.setWriter(loginUserId);

	    Route savedRoute = routeService.uploadRoute(route);
	    return ResponseEntity.ok(savedRoute);
	}

	// 코스 전체 목록 조회
	@GetMapping
	public List<Route> getAllRoutes(){
		return routeService.getAllRoutes();
	}
	
	//코스 id로 조회
	@GetMapping("/{id}")
	public Route getRouteById(@PathVariable("id") Long id) {
		return routeService.getRouteById(id);
	}
}

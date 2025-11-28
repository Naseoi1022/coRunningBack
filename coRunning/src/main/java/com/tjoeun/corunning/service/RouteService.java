package com.tjoeun.corunning.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tjoeun.corunning.domain.Route;
import com.tjoeun.corunning.repository.RouteRepository;

@Service
public class RouteService {
	@Autowired
	private RouteRepository routeRepository;
	
	public Route uploadRoute(Route route) {
		route.setCreateAt(LocalDateTime.now());
		return routeRepository.save(route);
	}
	
	public List<Route> getAllRoutes(){
		return routeRepository.findAll();
	}
	
	public Route getRouteById(Long id) {
		return routeRepository.findById(id).orElse(null);
	}
	// 경로 삭제 (작성자만 가능)
    public void deleteRoute(Long id, String loginUserId) {
        Route route = getRouteById(id);

        if (!route.getWriter().equals(loginUserId)) {
            throw new RuntimeException("작성자만 삭제할 수 있습니다.");
        }

        routeRepository.delete(route);
    }
}

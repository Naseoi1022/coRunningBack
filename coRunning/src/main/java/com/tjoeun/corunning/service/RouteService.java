package com.tjoeun.corunning.service;

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
		return routeRepository.save(route);
	}
	
	public List<Route> getAllRoutes(){
		return routeRepository.findAll();
	}
	
	public Route getRouteById(Long id) {
		return routeRepository.findById(id).orElse(null);
	}
}

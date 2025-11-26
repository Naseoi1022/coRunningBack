package com.tjoeun.corunning.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tjoeun.corunning.domain.RouteDip;

@Repository
public interface DipRepository extends JpaRepository<RouteDip, Long> {

	boolean existsByUserIdAndRouteId(String userId, Long routeId);

	List<RouteDip> findByUserId(String userId);
	
	Optional<RouteDip> findByUserIdAndRouteId(String userId, Long routeId);

}

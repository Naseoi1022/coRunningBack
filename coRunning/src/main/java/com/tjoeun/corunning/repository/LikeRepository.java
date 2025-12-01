package com.tjoeun.corunning.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tjoeun.corunning.domain.RouteLike;

@Repository
public interface LikeRepository extends JpaRepository<RouteLike, Long> {

    boolean existsByUserIdAndRouteId(String userId, Long routeId);

	Optional<RouteLike> findByIdAndRouteId(String userId, Long routeId);
	
	void deleteByUserIdAndRouteId(String userId, Long routeId);
}

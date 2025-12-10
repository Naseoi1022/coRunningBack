package com.tjoeun.corunning.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tjoeun.corunning.domain.RouteDip;

@Repository
public interface DipRepository extends JpaRepository<RouteDip, Long> {

	boolean existsByUserIdAndRouteId(String userId, Long routeId);

	List<RouteDip> findByUserId(String userId);
	
	Optional<RouteDip> findByUserIdAndRouteId(String userId, Long routeId);
	
	// DipRepository.java (CAST 제거 - 단순 조회)
	@Query(value = """
		    SELECT 
		        d.record,
		        CASE 
		            WHEN d.route_id IS NULL THEN d.distance
		            ELSE r.distance
		        END AS distance
		    FROM route_dip d
		    LEFT JOIN route r 
		        ON d.route_id = r.route_id
		    WHERE d.user_id = :userId
		      AND d.complete = true
		    """, nativeQuery = true)
		List<Object[]> findCompletedRecords(@Param("userId") String userId);


}

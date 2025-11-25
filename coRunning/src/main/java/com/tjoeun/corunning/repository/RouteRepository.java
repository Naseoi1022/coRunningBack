package com.tjoeun.corunning.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tjoeun.corunning.domain.Route;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

}

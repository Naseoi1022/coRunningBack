package com.tjoeun.corunning.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tjoeun.corunning.domain.RouteComment;

public interface RouteCommentRepository extends JpaRepository<RouteComment, Long> {

    // 특정 게시글의 댓글을 작성일 오름차순으로 조회
    List<RouteComment> findByRouteIdOrderByCreatedAtAsc(Long routeId);
}

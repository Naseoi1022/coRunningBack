package com.tjoeun.corunning.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tjoeun.corunning.domain.RouteLike;
import com.tjoeun.corunning.repository.LikeRepository;
import com.tjoeun.corunning.repository.RouteRepository;

@Service
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final RouteRepository routeRepository;

    public LikeService(LikeRepository likeRepository, RouteRepository routeRepository) {
        this.likeRepository = likeRepository;
        this.routeRepository = routeRepository;
    }

    // userId + routeId로 중복 검사 후 좋아요 추가
    public boolean addLike(String userId, Long routeId) {
        boolean exists = likeRepository.existsByUserIdAndRouteId(userId, routeId);
        if (exists) {
            return false; // 이미 추천함
        }

        // 좋아요 행 저장
        RouteLike like = new RouteLike();
        like.setUserId(userId);
        like.setRouteId(routeId);
        likeRepository.save(like);

        // 해당 Route의 liked 수 증가
        routeRepository.findById(routeId).ifPresent(route -> {
            Long currentLikes = route.getLiked();
            route.setLiked(currentLikes == null ? 1L : currentLikes + 1);
            routeRepository.save(route);
        });

        return true;
    }
}

package com.tjoeun.corunning.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tjoeun.corunning.domain.Route;
import com.tjoeun.corunning.domain.RouteComment;
import com.tjoeun.corunning.repository.RouteCommentRepository;
import com.tjoeun.corunning.repository.RouteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RouteCommentService {
	private final RouteCommentRepository routeCommentRepository;
    private final RouteRepository routeRepository;

    // 댓글 등록
    public RouteComment createComment(Long routeId, String content, String writerId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다. id=" + routeId));

        RouteComment comment = new RouteComment();
        comment.setRouteId(route.getRoute_id());
        comment.setWriterId(writerId);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        return routeCommentRepository.save(comment);
    }

    // 게시글의 댓글 목록 조회
    public List<RouteComment> getComments(Long routeId) {
        return routeCommentRepository.findByRouteIdOrderByCreatedAtAsc(routeId);
    }

    // 댓글 삭제 (작성자만 가능)
    public void deleteComment(Long commentId, String loginUserId) {
        RouteComment comment = routeCommentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다. id=" + commentId));

        if (!comment.getWriterId().equals(loginUserId)) {
            throw new RuntimeException("본인 댓글만 삭제할 수 있습니다.");
        }

        routeCommentRepository.delete(comment);
    }
}

package com.tjoeun.corunning.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.corunning.domain.Route;
import com.tjoeun.corunning.domain.RouteComment;
import com.tjoeun.corunning.dto.RouteCommentRequestDTO;
import com.tjoeun.corunning.exception.CustomException;
import com.tjoeun.corunning.service.RouteCommentService;
import com.tjoeun.corunning.service.RouteService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {
	private final RouteService routeService;
	private final RouteCommentService routeCommentService;
	//코스 업로드
	@PostMapping
	public ResponseEntity<?> uploadRoute(@RequestBody Route route, HttpSession session) {
	    String loginUserId = (String) session.getAttribute("loginUserId");
	    String userName = (String) session.getAttribute("userName");
	    if (loginUserId == null) {
	    	throw new CustomException("로그인이 필요합니다.");
	    }
	    route.setWriter(loginUserId);
	    route.setWriter_name(userName);

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
	//코스 회원 id로 조회
	@GetMapping("/user/{userId}")
	public List<Route> getRouteByUserId(@PathVariable("userId") String writer) {
		return routeService.getRouteByUserId(writer);
			
		}
	
	// 게시글 삭제 (작성자만 가능)
    @DeleteMapping("/{id}/remove")
    public ResponseEntity<String> deleteRoute(@PathVariable("id") Long id,
                            HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        if (loginUserId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        routeService.deleteRoute(id, loginUserId);
        return ResponseEntity.ok("삭제되었습니다.");
    }
    
	//댓글작성
	@PostMapping("/{id}/comments")
    public RouteComment createComment(@PathVariable("id") Long routeId,
                                     @RequestBody RouteCommentRequestDTO dto,
                                     HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        String userName = (String) session.getAttribute("userName");
        if (loginUserId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        return routeCommentService.createComment(routeId, dto.getContent(), loginUserId, userName);
    }

    // 게시글 댓글 목록 조회
    @GetMapping("/{id}/comments")
    public List<RouteComment> getComments(@PathVariable("id") Long routeId) {
        return routeCommentService.getComments(routeId);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId,
                              HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        if (loginUserId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        routeCommentService.deleteComment(commentId, loginUserId);
    }
}

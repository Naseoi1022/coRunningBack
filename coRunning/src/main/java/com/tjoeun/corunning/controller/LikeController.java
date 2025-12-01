package com.tjoeun.corunning.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.corunning.exception.CustomException;
import com.tjoeun.corunning.service.LikeService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }
    
    
    //좋아요 추가
    @PutMapping("/add")
    public ResponseEntity<String> addLike(HttpSession session,
                                          @RequestParam(name = "routeId") Long routeId) {
    	
    	String loginUserId = (String) session.getAttribute("loginUserId");
    	if (loginUserId == null) {
            throw new CustomException("로그인이 필요합니다.");
        }
        boolean added = likeService.addLike(loginUserId, routeId);
        if (added) {
            return ResponseEntity.ok("추천이 추가되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("이미 추천한 사용자입니다.");
        }
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteLike(HttpSession session, 
                                            @RequestParam(name="routeId") Long routeId) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        if (loginUserId == null) {
            throw new CustomException("로그인이 필요합니다");
        }
        
        boolean deleted = likeService.deleteLike(loginUserId, routeId);
        if (deleted) {
            return ResponseEntity.ok("추천이 취소되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("추천 정보가 없습니다.");
        }
    }
    
    @GetMapping("/check")
    public boolean checkLike(HttpSession session, @RequestParam(name="routeId") Long routeId) {
    	String loginUserId = (String) session.getAttribute("loginUserId");
		return likeService.checkLike(loginUserId, routeId);
    }
}

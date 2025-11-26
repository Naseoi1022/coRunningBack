package com.tjoeun.corunning.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.corunning.service.LikeService;

@RestController
@RequestMapping("/api/like")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PutMapping("/add")
    public ResponseEntity<String> addLike(@RequestParam(name = "userId") String userId,
                                          @RequestParam(name = "routeId") Long routeId) {

        boolean added = likeService.addLike(userId, routeId);
        if (added) {
            return ResponseEntity.ok("추천이 추가되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("이미 추천한 사용자입니다.");
        }
    }
    
}

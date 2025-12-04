package com.tjoeun.corunning.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.corunning.dto.MyPageDTO;
import com.tjoeun.corunning.service.MyPageService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {
    
    private final MyPageService myPageService;
    
    @GetMapping("/dashboard")
    public ResponseEntity<List<MyPageDTO>> getDashboard(HttpSession session) {
        String userId = (String) session.getAttribute("loginUserId");
        if (userId == null) {
            return ResponseEntity.ok(List.of());  // 빈 배열 반환
        }
        
        List<MyPageDTO> records = myPageService.getUserRunRecords(userId);
        return ResponseEntity.ok(records);
    }

}

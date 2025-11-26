package com.tjoeun.corunning.controller;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.corunning.dto.RouteDipDTO;
import com.tjoeun.corunning.exception.LoginRequiredException;
import com.tjoeun.corunning.service.DipService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/dip")
public class DipController {
	private final DipService dipService;
	
	public DipController(DipService dipService) {
		this.dipService = dipService;
	}
	
	
	// 찜목록 추가
	@PutMapping("/add")
	public ResponseEntity<String> addToDipList(HttpSession session,
            @RequestParam(name = "routeId") Long routeId){
		
		String loginUserId = (String) session.getAttribute("loginUserId");
    	if (loginUserId == null) {
    		throw new LoginRequiredException("로그인이 필요합니다."); // 401 Unauthorized 상태 반환
        }
		boolean exist = dipService.addDip(loginUserId, routeId);
		
		if (exist) {
			return ResponseEntity.ok("경로가 찜목록에 추가되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("이미 찜한 경로입니다.");
        }
	}
	
	// 내 찜목록 불러오기
	@GetMapping("/list")
	public ResponseEntity<ArrayList<RouteDipDTO>> getList(HttpSession session) {
		String loginUserId = (String) session.getAttribute("loginUserId");
    	if (loginUserId == null) {
            throw new LoginRequiredException("로그인이 필요합니다.");
        }
	    ArrayList<RouteDipDTO> dipList = dipService.getDipList(loginUserId);
	    return ResponseEntity.ok(dipList);
	}
}

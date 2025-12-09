package com.tjoeun.corunning.controller;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.corunning.dto.RouteDipDTO;
import com.tjoeun.corunning.repository.DipRepository;
import com.tjoeun.corunning.service.DipService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/dip")
public class DipController {
	private final DipService dipService;
	private final DipRepository dipRepository;
	public DipController(DipService dipService, DipRepository dipRepository) {
		this.dipService = dipService;
		this.dipRepository = dipRepository;
		
	}
	
	@PutMapping("/add")
	public ResponseEntity<String> addToDipList(HttpSession session,
            @RequestParam(name = "routeId") Long routeId){
		boolean exist = dipService.addDip((String) session.getAttribute("loginUserId"), routeId);
		
		if (exist) {
			return ResponseEntity.ok("경로가 찜목록에 추가되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("이미 찜한 경로입니다.");
        }
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateDip(
	        @RequestParam(name = "id") Long id,
	        @RequestParam(name = "complete", required = false, defaultValue = "false") boolean complete, 
	        @RequestParam(name = "record") String record) {
	    
	    boolean updated = dipService.updateDip(id, complete, record);
	    
	    if (updated) {
	        return ResponseEntity.ok("찜목록이 업데이트되었습니다.");
	    } else {
	        return ResponseEntity.badRequest().body("업데이트할 찜목록이 존재하지 않습니다.");
	    }
	}
	
	@GetMapping("/list")
	public ResponseEntity<ArrayList<RouteDipDTO>> getList(HttpSession session) {
	    ArrayList<RouteDipDTO> dipList = dipService.getDipList((String) session.getAttribute("loginUserId"));
	    return ResponseEntity.ok(dipList);
	}
	@DeleteMapping("/removeByRoute")
	public ResponseEntity<String> removeFromDipList(HttpSession session,
            @RequestParam(name = "routeId") Long routeId) {
		boolean removed = dipService.removeDip(routeId, (String) session.getAttribute("loginUserId"));
	    if (removed) {
	        return ResponseEntity.ok("찜목록에서 경로가 삭제되었습니다.");
	    } else {
	        return ResponseEntity.badRequest().body("삭제할 찜목록이 존재하지 않습니다.");
	    }
	}
	
	@DeleteMapping("/remove/{dipId}")
	public ResponseEntity<?> removeDipById(@PathVariable("dipId") Long dipId) {
	    dipRepository.deleteById(dipId);
	    return ResponseEntity.ok("Deleted");
	}

}

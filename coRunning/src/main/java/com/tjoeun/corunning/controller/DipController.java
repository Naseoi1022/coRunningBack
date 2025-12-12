package com.tjoeun.corunning.controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tjoeun.corunning.domain.RouteDip;
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
	@PostMapping("/addCustom")
	public ResponseEntity<String> addCustomDip(
	        @RequestBody Map<String, Object> body,
	        HttpSession session) {
	    try {
	        RouteDip newDip = new RouteDip();
	        newDip.setUserId((String) session.getAttribute("loginUserId"));
	        newDip.setTitle((String) body.get("title"));
	        newDip.setDistance(
	                body.get("distance") != null 
	                    ? Double.parseDouble(body.get("distance").toString())
	                    : null
	            );
	        newDip.setLocation((String) body.get("location"));
	        newDip.setRecord((String) body.get("record"));
	        newDip.setComplete(1);

	        dipRepository.save(newDip);

	        return ResponseEntity.ok("커스텀 기록이 추가되었습니다!");
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body("서버 오류: " + e.getMessage());
	    }
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateDip(@RequestBody RouteDip req) {
	    boolean success = dipService.updateDip(
	        req.getId(),
	        req.getComplete(),
	        req.getRecord(),
	        req.getTitle(),
	        req.getDistance(),
	        req.getLocation()
	    );

	    return success 
	        ? ResponseEntity.ok("updated")
	        : ResponseEntity.badRequest().body("not found");
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

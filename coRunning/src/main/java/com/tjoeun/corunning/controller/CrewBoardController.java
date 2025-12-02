package com.tjoeun.corunning.controller;

import com.tjoeun.corunning.domain.BoardType;
import com.tjoeun.corunning.domain.CrewApplication;
import com.tjoeun.corunning.domain.CrewBoard;
import com.tjoeun.corunning.domain.CrewComment;
import com.tjoeun.corunning.dto.CrewBoardRequestDTO;
import com.tjoeun.corunning.dto.CrewCommentRequestDTO;
import com.tjoeun.corunning.service.CrewBoardService;
import com.tjoeun.corunning.service.CrewCommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crew-board")
@RequiredArgsConstructor
public class CrewBoardController {

    private final CrewBoardService crewBoardService;
    private final CrewCommentService crewCommentService;


    // 게시글 생성
    @PostMapping
    public CrewBoard createBoard(@RequestBody CrewBoardRequestDTO dto,
                                 HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        if (loginUserId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        return crewBoardService.createBoard(dto, loginUserId);
    }

    // 게시글 목록 (type이 없으면 전체, 있으면 해당 타입만 조회)
    @GetMapping
    public List<CrewBoard> getBoardList(@RequestParam(name = "type", required = false) BoardType type) {
        return crewBoardService.getBoardList(type);
        
    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public CrewBoard getBoard(@PathVariable("id") Long id) {
        return crewBoardService.getBoard(id);
    }

    // 게시글 수정 (작성자만 가능)
    @PutMapping("/{id}")
    public CrewBoard updateBoard(@PathVariable("id") Long id,
                                 @RequestBody CrewBoardRequestDTO dto,
                                 HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        if (loginUserId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        return crewBoardService.updateBoard(id, dto, loginUserId);
    }

    // 게시글 삭제 (작성자만 가능)
    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable("id") Long id,
                            HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        if (loginUserId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        crewBoardService.deleteBoard(id, loginUserId);
    }

    // 크루 모집 신청
    @PostMapping("/{id}/apply")
    public CrewApplication applyToBoard(@PathVariable("id") Long id,
                                        HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        if (loginUserId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        return crewBoardService.applyToBoard(id, loginUserId);
    }

    // 신청자 목록 조회 (작성자만 가능)
    @GetMapping("/{id}/applications")
    public List<CrewApplication> getApplications(@PathVariable("id") Long id,
                                                 HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        if (loginUserId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        return crewBoardService.getApplications(id, loginUserId);
    }
    // 신청 상태 조회
    @GetMapping("/check")
    public boolean checkApplication(HttpSession session, @RequestParam(name="boardId") Long boardId) {
    	String loginUserId = (String) session.getAttribute("loginUserId");
    	return crewBoardService.checkApplication(boardId, loginUserId);
    } 
    
 // 댓글 작성
    @PostMapping("/{boardId}/comments")
    public CrewComment createComment(@PathVariable("boardId") Long boardId,
                                     @RequestBody CrewCommentRequestDTO dto,
                                     HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        if (loginUserId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        return crewCommentService.createComment(boardId, dto.getContent(), loginUserId);
    }

    // 게시글 댓글 목록 조회
    @GetMapping("/{boardId}/comments")
    public List<CrewComment> getComments(@PathVariable("boardId") Long boardId) {
        return crewCommentService.getComments(boardId);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId,
                              HttpSession session) {
        String loginUserId = (String) session.getAttribute("loginUserId");
        if (loginUserId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        crewCommentService.deleteComment(commentId, loginUserId);
    }
    
    //

    
}

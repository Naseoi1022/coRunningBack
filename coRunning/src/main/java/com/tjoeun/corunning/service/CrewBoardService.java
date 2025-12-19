package com.tjoeun.corunning.service;

import com.tjoeun.corunning.domain.*;
import com.tjoeun.corunning.dto.CrewBoardRequestDTO;
import com.tjoeun.corunning.repository.CrewApplicationRepository;
import com.tjoeun.corunning.repository.CrewBoardRepository;
import com.tjoeun.corunning.repository.CrewCommentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrewBoardService {

    private final CrewBoardRepository crewBoardRepository;
    private final CrewApplicationRepository crewApplicationRepository;
    private final UserService userService; // 신청자 정보 조회용
	private final CrewCommentRepository crewCommentRepository;

    // 게시글 생성 (로그인한 사용자 ID를 writerId로 사용)
    public CrewBoard createBoard(CrewBoardRequestDTO dto, String writerId, String userName) {
        CrewBoard board = new CrewBoard();
        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        board.setRegion(dto.getRegion());
        board.setBoardType(dto.getBoardType());

        board.setRecruitCount(dto.getRecruitCount());
        board.setCurrentCount(0); // 처음엔 0명

        board.setDeadline(dto.getDeadline());
        board.setRoutePathJson(dto.getRoutePathJson());
        board.setWriterId(writerId);
        board.setWriter_name(userName);
        board.setCreatedAt(LocalDateTime.now());
        board.setRunAt(dto.getRunAt());

        return crewBoardRepository.save(board);
    }

    // 게시글 목록 (type이 null이면 전체, 아니면 해당 타입만)
    public List<CrewBoard> getBoardList(BoardType type) {
        if (type == null) {
            return crewBoardRepository.findAll();
        }
        return crewBoardRepository.findByBoardType(type);
    }

    // 게시글 상세 조회
    public CrewBoard getBoard(Long id) {
        return crewBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다. id=" + id));
    }

    // 게시글 수정 (작성자만 가능)
    public CrewBoard updateBoard(Long id, CrewBoardRequestDTO dto, String loginUserId) {
        CrewBoard board = getBoard(id);

        if (!board.getWriterId().equals(loginUserId)) {
            throw new RuntimeException("작성자만 수정할 수 있습니다.");
        }

        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());
        board.setRegion(dto.getRegion());
        board.setBoardType(dto.getBoardType());
        board.setRecruitCount(dto.getRecruitCount());
        board.setDeadline(dto.getDeadline());
        board.setRoutePathJson(dto.getRoutePathJson());
        board.setRunAt(dto.getRunAt());


        return crewBoardRepository.save(board);
    }

    
    // 게시글 삭제 (작성자만 가능)
    @Transactional
    public void deleteBoard(Long id, String loginUserId) {
        CrewBoard board = getBoard(id);
        if (!board.getWriterId().equals(loginUserId)) {
            throw new RuntimeException("작성자만 삭제할 수 있습니다.");
        }
        crewApplicationRepository.deleteByCrewBoard_Id(id);
        crewCommentRepository.deleteByBoard_Id(id);
        crewBoardRepository.delete(board);
    }

    // 크루 모집 신청
    public CrewApplication applyToBoard(Long boardId, String loginUserId) {
        CrewBoard board = getBoard(boardId);

        //마감일 체크
        LocalDate today = LocalDate.now();
        if (board.getDeadline() != null && board.getDeadline().isBefore(today)) {
            throw new RuntimeException("모집 기한이 지나 신청할 수 없습니다.");
        }

        //인원 마감 체크
        if (board.getCurrentCount() >= board.getRecruitCount()) {
            throw new RuntimeException("모집 인원이 가득 찼습니다.");
        }

        //중복 신청 체크
        if (crewApplicationRepository.existsByCrewBoardIdAndApplicantId(boardId, loginUserId)) {
            throw new RuntimeException("이미 신청한 모집글입니다.");
        }

        //신청자 정보 조회
        User user = userService.getUser(loginUserId);

        CrewApplication application = new CrewApplication();
        application.setCrewBoard(board);
        application.setApplicantId(user.getUserId());
        application.setApplicantName(user.getUserName());
        application.setBirthDate(user.getBirthDate());
        application.setHireDate(user.getHireDate());
        application.setPhone(user.getPhone());
        application.setUserAddress(user.getUserAddress());
        application.setAppliedAt(LocalDateTime.now());

        // 신청 저장
        CrewApplication saved = crewApplicationRepository.save(application);

        // 현재 인원 +1
        board.setCurrentCount(board.getCurrentCount() + 1);
        crewBoardRepository.save(board);

        return saved;
    }
    
    //신청 상태 조회
    public boolean checkApplication(Long boardId, String loginUserId) {
    	if (crewApplicationRepository.existsByCrewBoardIdAndApplicantId(boardId, loginUserId)) {
            return true;
        }else {
        	return false;
        }
    	
    }

    // 신청자 목록 조회 (작성자만 조회 가능)
    public List<CrewApplication> getApplications(Long boardId, String loginUserId) {
        CrewBoard board = getBoard(boardId);

        if (!board.getWriterId().equals(loginUserId)) {
            throw new RuntimeException("작성자만 신청자 목록을 조회할 수 있습니다.");
        }

        return crewApplicationRepository.findByCrewBoardId(boardId);
    }
    //id로 게시판 조회
    public List<CrewBoard> getBoardByUserId(String userId){
    	return crewBoardRepository.findByWriterId(userId);
    	
    	
    }

}

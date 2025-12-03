package com.tjoeun.corunning.repository;

import com.tjoeun.corunning.domain.BoardType;
import com.tjoeun.corunning.domain.CrewBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrewBoardRepository extends JpaRepository<CrewBoard, Long> {

    // 게시판 종류별 목록 조회 (NORMAL / FLASH / DRAWING)
    List<CrewBoard> findByBoardType(BoardType boardType);

	List<CrewBoard> findByWriterId(String userId);
}

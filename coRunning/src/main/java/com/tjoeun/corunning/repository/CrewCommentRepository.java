package com.tjoeun.corunning.repository;

import com.tjoeun.corunning.domain.CrewComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrewCommentRepository extends JpaRepository<CrewComment, Long> {

    // 특정 게시글의 댓글을 작성일 오름차순으로 조회
    List<CrewComment> findByBoardIdOrderByCreatedAtAsc(Long boardId);
}

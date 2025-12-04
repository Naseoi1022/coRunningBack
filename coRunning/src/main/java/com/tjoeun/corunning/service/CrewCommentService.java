package com.tjoeun.corunning.service;

import com.tjoeun.corunning.domain.CrewBoard;
import com.tjoeun.corunning.domain.CrewComment;
import com.tjoeun.corunning.repository.CrewBoardRepository;
import com.tjoeun.corunning.repository.CrewCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrewCommentService {

    private final CrewCommentRepository commentRepository;
    private final CrewBoardRepository crewBoardRepository;

    // 댓글 등록
    public CrewComment createComment(Long boardId, String content, String writerId, String userName) {
        CrewBoard board = crewBoardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다. id=" + boardId));

        CrewComment comment = new CrewComment();
        comment.setBoard(board);
        comment.setWriterId(writerId);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setWriter_name(userName);

        return commentRepository.save(comment);
    }

    // 게시글의 댓글 목록 조회
    public List<CrewComment> getComments(Long boardId) {
        return commentRepository.findByBoardIdOrderByCreatedAtAsc(boardId);
    }

    // 댓글 삭제 (작성자만 가능)
    public void deleteComment(Long commentId, String loginUserId) {
        CrewComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글이 존재하지 않습니다. id=" + commentId));

        if (!comment.getWriterId().equals(loginUserId)) {
            throw new RuntimeException("본인 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }
}

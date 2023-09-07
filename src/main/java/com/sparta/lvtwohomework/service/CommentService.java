package com.sparta.lvtwohomework.service;

import com.sparta.lvtwohomework.dto.CommentRequestDto;
import com.sparta.lvtwohomework.dto.CommentResponseDto;
import com.sparta.lvtwohomework.entity.Board;
import com.sparta.lvtwohomework.entity.Comment;
import com.sparta.lvtwohomework.entity.User;
import com.sparta.lvtwohomework.repository.BoardRepository;
import com.sparta.lvtwohomework.repository.CommentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    public CommentResponseDto createComment(CommentRequestDto requestDto, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        Board saveBoard = boardRepository.findById(requestDto.getBoardId()).orElseThrow(()-> new IllegalArgumentException("선택하신 게시글은 없습니다."));

        Comment comment = new Comment(requestDto, saveBoard);
        comment.setUsername(user.getUsername());

        Comment saveComment = commentRepository.save(comment);
        return new CommentResponseDto(saveComment);
    }

    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        if(comment.getUsername().equals(user.getUsername()) || user.getRole().equals("ADMIN")){
            comment.update(requestDto);
        } else {
            throw new IllegalArgumentException("댓글수정권한이 없습니다.");
        }

        return new CommentResponseDto(comment);
    }

    public String deleteComment(Long id, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        if(comment.getUsername().equals(user.getUsername()) || user.getRole().equals("ADMIN")){
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("댓글삭제권한이 없습니다.");
        }

        return id + "번 댓글 삭제에 성공했습니다.";
    }
}

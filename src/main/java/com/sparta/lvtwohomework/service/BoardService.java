package com.sparta.lvtwohomework.service;

import com.sparta.lvtwohomework.dto.BoardCommentResponseDto;
import com.sparta.lvtwohomework.dto.BoardRequestDto;
import com.sparta.lvtwohomework.dto.BoardResponseDto;
import com.sparta.lvtwohomework.dto.CommentResponseDto;
import com.sparta.lvtwohomework.entity.Board;
import com.sparta.lvtwohomework.entity.User;
import com.sparta.lvtwohomework.jwt.JwtUtil;
import com.sparta.lvtwohomework.repository.BoardRepository;
import com.sparta.lvtwohomework.repository.CommentRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public BoardResponseDto createBoard(BoardRequestDto requestDto, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        Board board = new Board(requestDto);
        board.setUsername(user.getUsername());

        Board saveBoard = boardRepository.save(board);
        return new BoardResponseDto(saveBoard);
    }

    public List<BoardCommentResponseDto> getBoard() {
        List<Board> boardList = boardRepository.findAllByOrderBySaveDateDesc();
        List<BoardCommentResponseDto> boardCommentResponseDtoList = new ArrayList<>();

        for (Board board : boardList) {
            boardCommentResponseDtoList.add(new BoardCommentResponseDto(board, commentRepository.findAllByBoardIdOrderBySaveDateDesc(board.getId()).stream().map(CommentResponseDto::new).toList()));
        }

        return boardCommentResponseDtoList;
        // return boardRepository.findAllByOrderBySaveDateDesc().stream().map(BoardResponseDto::new).toList();
    }

    public BoardCommentResponseDto selectGetBoard(Long id) {
        Board board = boardRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다"));

        return new BoardCommentResponseDto(board, commentRepository.findAllByBoardIdOrderBySaveDateDesc(board.getId()).stream().map(CommentResponseDto::new).toList());
    }

    public BoardResponseDto updateBoard(Long id,
        BoardRequestDto requestDto,
        HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        Board board = boardRepository.findByUsernameAndId(user.getUsername(), id)
            .orElseThrow(()-> new IllegalArgumentException("게시물이 존재하지 않습니다."));
        if(board.getUsername().equals(user.getUsername())||user.getRole().equals("ADMIN")){
            board.update(requestDto);
        } else {
            throw new IllegalArgumentException("게시글수정 권한이 없습니다.");
        }
        return new BoardResponseDto(board);
    }

    public String deleteBoard(Long id, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        Board board = boardRepository.findByUsernameAndId(user.getUsername(), id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 비밀 번호 입니다."));
        if(board.getUsername().equals(user.getUsername())||user.getRole().equals("ADMIN")){
            boardRepository.delete(board);
        } else {
            throw new IllegalArgumentException("게시글삭제 권한이 없습니다.");
        }

        return id + "번 게시물 삭제에 성공했습니다.";
    }
}
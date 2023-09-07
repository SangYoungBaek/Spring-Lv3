package com.sparta.lvtwohomework.dto;

import com.sparta.lvtwohomework.entity.Board;
import com.sparta.lvtwohomework.entity.Comment;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class BoardCommentResponseDto {

    private Long id;
    private String title;
    private String username;
    private String contents;
    private LocalDate saveDate;
    private LocalDate modifiedAt;
    private List<CommentResponseDto> commentResponseDtoList;


    public BoardCommentResponseDto(Board board, List<CommentResponseDto> commentResponseDto) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.contents = board.getContents();
        this.saveDate = board.getSaveDate();
        this.modifiedAt = board.getModifiedAt();
        this.commentResponseDtoList = commentResponseDto;
    }
}

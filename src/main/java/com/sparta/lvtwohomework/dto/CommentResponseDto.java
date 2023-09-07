package com.sparta.lvtwohomework.dto;

import com.sparta.lvtwohomework.entity.Comment;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String contents;
    private String username;
    private LocalDate saveDate;
    private LocalDate modifiedAt;

    public CommentResponseDto(Comment saveComment) {
        this.id = saveComment.getId();
        this.contents = saveComment.getContents();
        this.username = saveComment.getUsername();
        this.saveDate = saveComment.getSaveDate();
        this.modifiedAt = saveComment.getModifiedAt();
    }
}

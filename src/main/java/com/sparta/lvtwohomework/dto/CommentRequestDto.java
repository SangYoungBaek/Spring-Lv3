package com.sparta.lvtwohomework.dto;

import com.sparta.lvtwohomework.entity.Board;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long boardId;
    private String contents;
}

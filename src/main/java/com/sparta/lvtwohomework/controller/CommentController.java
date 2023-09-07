package com.sparta.lvtwohomework.controller;

import com.sparta.lvtwohomework.dto.CommentRequestDto;
import com.sparta.lvtwohomework.dto.CommentResponseDto;
<<<<<<< HEAD
import com.sparta.lvtwohomework.dto.StatusResponseDto;
import com.sparta.lvtwohomework.entity.Board;
=======
>>>>>>> 39739499c49c339fe0e79ab0b87e246e6328ded4
import com.sparta.lvtwohomework.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto, HttpServletRequest req) {
        return commentService.createComment(requestDto, req);
    }

    @PutMapping("/comment/{id}")
    public StatusResponseDto updateComment(@PathVariable Long id,
                                           @RequestBody CommentRequestDto requestDto,
                                           HttpServletRequest req) {
        return commentService.updateComment(id, requestDto, req);
    }

    @DeleteMapping("/comment/{id}")
    public StatusResponseDto deleteComment(@PathVariable Long id, HttpServletRequest req) {
        return commentService.deleteComment(id, req);
    }

}

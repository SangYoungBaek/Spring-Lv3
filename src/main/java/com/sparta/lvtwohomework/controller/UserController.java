package com.sparta.lvtwohomework.controller;

import com.sparta.lvtwohomework.dto.StatusResponseDto;
import com.sparta.lvtwohomework.dto.UserRequestDto;
import com.sparta.lvtwohomework.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/signup")
    public StatusResponseDto signup(@RequestBody @Valid UserRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return new StatusResponseDto(String.valueOf(HttpStatus.BAD_REQUEST), "회원가입에 실패하였습니다.");
        }
        return userService.signup(requestDto);
    }

    @PostMapping("/user/login")
    public StatusResponseDto login(@RequestBody UserRequestDto requestDto, HttpServletResponse res) {
        try {
            return userService.login(requestDto, res);
        } catch (Exception e) {
            return new StatusResponseDto(String.valueOf(HttpStatus.BAD_REQUEST), "로그인에 실패하였습니다.");
        }
    }
}

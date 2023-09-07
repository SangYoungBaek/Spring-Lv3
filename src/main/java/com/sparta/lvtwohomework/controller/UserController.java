package com.sparta.lvtwohomework.controller;

import com.sparta.lvtwohomework.dto.StatusResponseDto;
import com.sparta.lvtwohomework.dto.UserRequestDto;
import com.sparta.lvtwohomework.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/signup")
    public ResponseEntity<StatusResponseDto> signup(@RequestBody @Valid UserRequestDto requestDto, BindingResult bindingResult) {
        StatusResponseDto result = new StatusResponseDto();
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            result.setStatus("400");
            result.setMsg("회원가입에 실패하였습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        result = userService.signup(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/user/login")
    public ResponseEntity<StatusResponseDto> login(@RequestBody UserRequestDto requestDto, HttpServletResponse res) {
        StatusResponseDto result = new StatusResponseDto();

        try {
             result = userService.login(requestDto, res);
        } catch (Exception e){
            result.setStatus("400");
            result.setMsg("로그인에 실패하였습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

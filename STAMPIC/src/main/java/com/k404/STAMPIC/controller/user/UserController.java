package com.k404.STAMPIC.controller.user;

import com.k404.STAMPIC.response.CustomResponseEntity;
import com.k404.STAMPIC.service.user.UserService;

import com.k404.STAMPIC.request.controller.user.UserRequest;
import com.k404.STAMPIC.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 이메일 중복 검사
    @GetMapping("auth/check")
    public CustomResponseEntity<Boolean> checkMatchEmail(
            @RequestParam final String email
    ) {
        return CustomResponseEntity.success(userService.emailCheckMatch(email));
    }
    
    // 회원가입
    @PostMapping("auth")
    public CustomResponseEntity<UserResponse.Login> createUserAccount(
            @RequestBody @Valid final UserRequest.CreateUser request
    ) {
        return CustomResponseEntity.success(userService.createUserAccount(request.toServiceRequest()));
    }
    
    // 로그인
    @PostMapping("auth/login")
    public CustomResponseEntity<UserResponse.Login> login(
            @RequestBody final UserRequest.Login request
    ) {
        return CustomResponseEntity.success(userService.login(request.toServiceRequest()));
    }
    
    // 마이페이지 정보조회
    @GetMapping("auth/my/info")
    public CustomResponseEntity<UserResponse.Detail> findMyListUser(
            @AuthenticationPrincipal Long userId
    ) {
        return CustomResponseEntity.success(userService.findMyListUser(userId));
    }
}

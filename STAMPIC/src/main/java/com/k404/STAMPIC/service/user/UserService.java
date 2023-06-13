package com.k404.STAMPIC.service.user;

import static com.k404.STAMPIC.enumeration.Result.NOT_FOUND_USER;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.swing.text.AbstractDocument.Content;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.k404.STAMPIC.domain.stamp.Stamp;
import com.k404.STAMPIC.domain.user.Authority;
import com.k404.STAMPIC.domain.user.User;
import com.k404.STAMPIC.enumeration.Result;
import com.k404.STAMPIC.exception.CustomException;
import com.k404.STAMPIC.repository.user.UserRepository;
import com.k404.STAMPIC.request.service.user.UserServiceRequest;

import com.k404.STAMPIC.config.jwt.TokenProvider;
import com.k404.STAMPIC.response.user.UserResponse;
import com.k404.STAMPIC.service.redis.RedisService;
import com.k404.STAMPIC.service.stamp.StampService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;    
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisService redisService;
    private final StampService stampService; // StampService 주입

    
    // 가입된 이메일 체크
    @Transactional
    public Boolean emailCheckMatch(String email) {
        return userRepository.existsByEmail(email);
    }
    
    // 회원가입
    @Transactional
    public UserResponse.Login createUserAccount(UserServiceRequest.CreateUser request) {
        validateRegister(request);
        
        User user = userRepository.save(
                createEntityUserFromDto(request)
        );

        // 토큰 발급
        String accessToken = tokenProvider.createToken(
                user.getId(), getAuthentication(request.getEmail(), request.getPassword()));
        String refreshToken = tokenProvider.createRefreshToken(request.getEmail());

        return UserResponse.Login.response(user, accessToken, refreshToken);
    }   
    
    private Authentication getAuthentication(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
   
    // 로그인
    @Transactional
    public UserResponse.Login login(UserServiceRequest.Login request) {
        validateLogin(request);
        System.out.println("로그인 요청시 requset에는 무엇이 들어있나요?"+request.getEmail());
        User user = userRepository.findOneWithAuthoritiesByEmail(request.getEmail())
                .orElseThrow(
                        () -> new CustomException(NOT_FOUND_USER)
        		);

        // 토큰 발급
        String accessToken = tokenProvider.createToken(
                user.getId(), getAuthentication(request.getEmail(), request.getPassword()));
        String refreshToken = tokenProvider.createRefreshToken(request.getEmail());        
        System.out.println("로그인 시 응답? " + user.getEmail().toString());
        return UserResponse.Login.response(user, accessToken, refreshToken);
    }
    
    // 마이페이지 정보조회
    @Transactional(readOnly = true)
    public UserResponse.Detail findMyListUser(Long userId) {
    	System.out.println("findMyListUser 실행: "+userId.toString());
        User user = getUser(userId);
        List<Stamp> AttrList = stampService.getStampfindAttrIdByUserId(userId);         
        List<Long> stampAttrList = new ArrayList<Long>();
        for (Stamp stamp : AttrList) {
        	stampAttrList.add(stamp.getAttraction().getId());
        	 System.out.println("stampAttrId: "+stamp.getAttraction().getId());
        }       
        long stampCount = stampService.getStampCountByUserId(userId); // 스탬프 카운트 조회
        return UserResponse.Detail.response(user, String.valueOf(stampCount), stampAttrList);
    }    
 
    // method
    public User getUser(Long userId) {
    	System.out.println("겟유저 실행 userId: "+ userId);
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)
        );
    }    

    // Validate
    private void validateRegister(UserServiceRequest.CreateUser request) {
        Boolean existsByEmail = userRepository.existsByEmail(request.getEmail());
        Boolean existsByNickName = userRepository.existsByNickName(request.getNickName());
        if (existsByEmail) {
            throw new CustomException(Result.DUPLICATION_USER);
        }
        if (existsByNickName) {
            throw new CustomException(Result.DUPLICATION_NICKNAME);
        }
    }

    private void validateLogin(
            UserServiceRequest.Login request
    ) {
        if (!userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(Result.NOT_FOUND_USER);
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                userRepository.findOneWithAuthoritiesByEmail(request.getEmail())
                        .orElseThrow(
                                () -> new CustomException(Result.NOT_MATCHED_ID_OR_PASSWORD)
                        ).getPassword())
        ) {
            throw new CustomException(Result.NOT_MATCHED_ID_OR_PASSWORD);
        }
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private User createEntityUserFromDto(UserServiceRequest.CreateUser request) {
        return User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .province(request.getProvince())
                .nickName(request.getNickName())
                .birth(request.getBirth())
//                .profileImageUrl(
//                        // 사용자 기본 프로필 추가
//                        setDefaultProfileImage()
//                )
                .authorities(
                        getAuthorities()
                )
                .build();
    }

    private static Set<Authority> getAuthorities() {
        return Collections.singleton(Authority.builder()
                .authorityName("ROLE_USER")
                .build());
    }
    
}
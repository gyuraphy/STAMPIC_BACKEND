package com.k404.STAMPIC.service.stamp;

import static com.k404.STAMPIC.enumeration.Result.NOT_FOUND_USER;

import java.util.List;

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
import com.k404.STAMPIC.repository.stamp.StampRepository;
import com.k404.STAMPIC.repository.user.UserRepository;
import com.k404.STAMPIC.request.service.user.UserServiceRequest;

import com.k404.STAMPIC.config.jwt.TokenProvider;
import com.k404.STAMPIC.response.user.UserResponse;
import com.k404.STAMPIC.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StampService {
	
	private final StampRepository stampRepository;

    public long getStampCountByUserId(Long userId) {
        return stampRepository.countByUserId(userId);
    }
    
    public List<Stamp> getStampfindAttrIdByUserId(Long userId) {
    	System.out.println("getStampfindAttrIdbyUserId 실행?");
    	System.out.println("getStampfindAttrIdbyUserId: "+stampRepository.findAttrIdByUserId(userId));    	
        return stampRepository.findAttrIdByUserId(userId);
    }
    
}
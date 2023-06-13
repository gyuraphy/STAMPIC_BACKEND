package com.k404.STAMPIC.controller.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k404.STAMPIC.response.CustomResponseEntity;
import com.k404.STAMPIC.response.home.HomeListResponse;
import com.k404.STAMPIC.service.home.HomeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HomeController {
	
	private final HomeService homeService;
	
	// 홈 명소 리스트 조회
	@GetMapping("/home")
	public CustomResponseEntity<HomeListResponse> getHomeList() {
		System.out.println("HomeController 실행");
		return CustomResponseEntity.success(homeService.getHomeList());
	}
}

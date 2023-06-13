package com.k404.STAMPIC.service.home;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.k404.STAMPIC.domain.home.Attraction;
import com.k404.STAMPIC.response.home.HomeListResponse;
import com.k404.STAMPIC.repository.home.AttractionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class HomeService {
	
	private final AttractionRepository attractionRepository;
	
	public HomeListResponse getHomeList() {
		
		System.out.println("HomeService 실행");
		
		List<Attraction> attrList = attractionRepository.findAll();
		//System.out.println("attrList: " + attrList);
		
		List<HomeListResponse.AttrInfo> attrInfoList = new ArrayList<>();
		
		for (Attraction attr : attrList) {		
			HomeListResponse.AttrInfo attrInfo = HomeListResponse.AttrInfo.builder()
					.Id(attr.getId())
			        .place(attr.getPlace())
			        .title(attr.getTitle())
			        .contents(attr.getContents())
			        .image(attr.getImage())
			        .thumb(attr.getThumb())
			        .addr(attr.getAddr())
			        .gugunNm(attr.getGugunNm())
			        .lat(attr.getLat())
			        .lng(attr.getLng())
			        .build(); 
			
		    	attrInfoList.add(attrInfo);
			}
		
		attrInfoList.sort(Comparator.comparing(HomeListResponse.AttrInfo::getId));
		
		HomeListResponse response = HomeListResponse.builder()
	            .attrInfoList(attrInfoList)
	            .build();
		System.out.println("reponse 값 확인"+response);
	return response;
	}
}

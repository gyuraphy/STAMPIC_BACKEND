package com.k404.STAMPIC.response.home;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeListResponse {
	
	private List<AttrInfo> attrInfoList = new ArrayList<>();
	
	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AttrInfo {
		private Long Id;   
	    private String place;  
	    private String title;
	    private String contents; 
	    private String image;
	    private String thumb;  
	    private String addr;
	    private String gugunNm;
	    private Float lat;
	    private Float lng;
	}
}

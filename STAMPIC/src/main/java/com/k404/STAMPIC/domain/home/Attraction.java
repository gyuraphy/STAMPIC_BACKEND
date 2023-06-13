package com.k404.STAMPIC.domain.home;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.k404.STAMPIC.domain.BaseEntity;
import com.sun.istack.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "attractions")
@SequenceGenerator(
        name="ATTR_SEQ_GEN", //시퀀스 제너레이터 이름
        sequenceName="ATTR_SEQ", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
        )   
public class Attraction extends BaseEntity {

	 @Id
	 @GeneratedValue(strategy=GenerationType.SEQUENCE, //사용할 전략을 시퀀스로  선택
	            generator="ATTR_SEQ_GEN")
	 @NotNull
	 private Long id;
	 
	 private int unseq;
	 
	 private String place;
	 
	 private String title;
	 
	 private String contents;
	 
	 private String image;

	 private String thumb;
	 
	 private String addr;
	 
	 @Column(name = "gugun_nm")
	 private String gugunNm;

	 private Float lat;
	 
	 private Float lng;	 
	 
	 @Builder
	    private Attraction(int unseq, String place, String title, String contents, String image, String thumb, String addr, String gugunNm, Float lat, Float lng) {
	        this.unseq = unseq;
	        this.place = place;
	        this.title = title;
	        this.contents = contents;
	        this.image = image;
	        this.thumb = thumb;
	        this.addr = addr;
	        this.gugunNm = gugunNm;
	        this.lat = lat;
	        this.lng = lng;
	    }
	 
	 public static Attraction toEntity(int unseq, String place, String title, String contents, String image, String thumb, String addr, String gugunNm, Float lat, Float lng) {
	        return Attraction.builder()
	                .unseq(unseq)
	                .place(place)
	                .title(title)
	                .contents(contents)
	                .image(image)
	                .thumb(thumb)
	                .addr(addr)
	                .gugunNm(gugunNm)
	                .lat(lat)
	                .lng(lng)
	                .build();
	    }
}

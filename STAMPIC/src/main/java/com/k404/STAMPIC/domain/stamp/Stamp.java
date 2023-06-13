package com.k404.STAMPIC.domain.stamp;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.k404.STAMPIC.domain.BaseEntity;
import com.k404.STAMPIC.domain.home.Attraction;
import com.k404.STAMPIC.domain.user.User;
import com.sun.istack.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "stamp")
@SequenceGenerator(
        name="STAMP_SEQ_GEN", //시퀀스 제너레이터 이름
        sequenceName="STAMP_SEQ", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
        )   
public class Stamp extends BaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, //사용할 전략을 시퀀스로  선택
            generator="STAMP_SEQ_GEN")
    @Column(name = "stamp_id")
    private Long stampId;
	 
    @ManyToOne
    @JoinColumn(name = "user_id_fk")
    private User user;
	 
    @ManyToOne
    @JoinColumn(name = "attr_id_fk")
    private Attraction attraction;
	 
    @Column(name = "visit_date")
    private LocalDateTime visitDate;
	 
    @Builder
    public Stamp(User user, Attraction attraction, LocalDateTime visitDate) {
        this.user = user;
        this.attraction = attraction;
        this.visitDate = visitDate;
    }
	 
	 public static Stamp toEntity(User user, Attraction attraction, LocalDateTime visitDate) {
	        return Stamp.builder()
	                .user(user)
	                .attraction(attraction)
	                .visitDate(visitDate)
	                .build();
	    }
}

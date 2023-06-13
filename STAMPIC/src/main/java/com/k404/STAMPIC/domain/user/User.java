package com.k404.STAMPIC.domain.user;

import com.sun.istack.NotNull;

import com.k404.STAMPIC.domain.user.Authority;

import com.k404.STAMPIC.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Getter
@Entity
@Table(name = "users")
@Builder
@DynamicUpdate
@SequenceGenerator(
        name="USER_SEQ_GEN", //시퀀스 제너레이터 이름
        sequenceName="USER_SEQ", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
        )     
public class User extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy=GenerationType.SEQUENCE, //사용할 전략을 시퀀스로  선택
            generator="USER_SEQ_GEN" //식별자 생성기를 설정해놓은  USER_SEQ_GEN으로 설정        
            )
    @Column(name = "user_id")
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String password;
    
    private String name;

    @NotNull
    private String nickName;

    private String birth;

    private String profileImageUrl;        
    
    private String province;
    
    private boolean isNewNotification = Boolean.FALSE;   // 새로운 알림 생성 여부
    
    // 유저 권한
    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;
    
    protected User() {}

    @Builder
    private User(Long id, String email, String password, String name, String nickName, String phoneNumber, String profileImageUrl,  String birth, String province, boolean isNewNotification, Set<Authority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;       
        this.profileImageUrl = profileImageUrl;
        this.birth = birth;
        this.province = province;
        this.isNewNotification = isNewNotification;
        this.authorities = authorities;
    }

    public void updateUserProfile(String nickName, String profileImageUrl){
        this.nickName = nickName;
        this.profileImageUrl = profileImageUrl;
    }

    public void updateNewNotification() {
        this.isNewNotification = true;
    }
    public void updateReadNewNotification() {
        this.isNewNotification = false;
    }
}
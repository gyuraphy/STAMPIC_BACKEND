package com.k404.STAMPIC.response.user;

import com.k404.STAMPIC.domain.stamp.Stamp;
import com.k404.STAMPIC.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserResponse {
	
	 // 로그인 Login
    @NoArgsConstructor
    @Getter
    public static class Login {
        private Long id;
        private String email;
        private String name;        
        private String nickName;
        private String province;
        private String birth;
        private String profileImageUrl;
        private String accessToken;
        private String refreshToken;        
       
        @Builder
        private Login(Long id, String email, String nickName, String province, String birth, String profileImageUrl, String accessToken, String refreshToken) {
            this.id = id;
            this.email = email;            
            this.nickName = nickName;
            this.province = province;
            this.birth = birth;
            this.profileImageUrl = profileImageUrl;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public static UserResponse.Login response(User user, String atk, String rtk) {
            return UserResponse.Login.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .province(user.getProvince())
                    .nickName(user.getNickName())
                    .birth(user.getBirth())
                    .accessToken(atk)
                    .refreshToken(rtk)
                    .build();
        }
    }
    
    // 마이페이지 정보조회 Detail
    @NoArgsConstructor
    @Getter
    public static class Detail {
        private Long id;
        private String email;
        private String name;
        private String nickName;        
        private String profileImageUrl;
        private String stampCount;
        private List<Long> stampAttrList = new ArrayList<>();       
        
        @Builder
        private Detail(Long id, String email, String nickName, String stampCount,  List<Long> stampAttrList) {
            this.id = id;
            this.email = email;
            this.nickName = nickName;      
            this.stampCount = stampCount; 
            this.stampAttrList = stampAttrList;
        }

        public static UserResponse.Detail response(User user, String stampCount, List<Long> stampAttrList){
            return UserResponse.Detail.builder()
                    .id(user.getId())
                    .email(user.getEmail())                   
                    .nickName(user.getNickName())                    
                    .stampCount(stampCount)
                    .stampAttrList(stampAttrList)
                    .build();
        }
    }

    @NoArgsConstructor
    @Getter
    public static class Update {
        private String nickName;
        private String profileImageUrl;

        @Builder
        private Update(String nickName, String profileImageUrl) {
            this.nickName = nickName;
            this.profileImageUrl = profileImageUrl;
        }

        public static UserResponse.Update response(User user) {
            return UserResponse.Update.builder()
                    .nickName(user.getNickName())
                    .profileImageUrl(user.getProfileImageUrl())
                    .build();
        }
    }    
   
}

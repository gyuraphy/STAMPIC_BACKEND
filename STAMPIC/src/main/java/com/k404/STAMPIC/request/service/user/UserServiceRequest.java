package com.k404.STAMPIC.request.service.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class UserServiceRequest {

    @NoArgsConstructor
    @Getter
    public static class CreateUser {
        private String email;
        private String password;
        private String province;
        private String nickName;
        private String birth;

        @Builder
        private CreateUser(String email, String password, String province, String nickName, String birth) {
            this.email = email;
            this.password = password;
            this.province = province;
            this.nickName = nickName; 
            this.birth = birth;
        }
    }

    @NoArgsConstructor
    @Getter
    public static class Login {
        private String email;
        private String password;

        @Builder
        private Login(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}

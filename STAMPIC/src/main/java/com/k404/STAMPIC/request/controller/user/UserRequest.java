package com.k404.STAMPIC.request.controller.user;

import com.k404.STAMPIC.request.service.user.UserServiceRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class UserRequest {

    @NoArgsConstructor
    @Getter
    public static class CreateUser {

        @NotBlank(message = "이메일은 필수입니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수입니다.")
        private String password;

        @NotBlank(message = "지역은 필수입니다.")
        private String province;

        @NotBlank(message = "닉네임은 필수입니다.")
        private String nickName;
        
        @NotBlank(message = "생년월일은 필수입니다.")
        private String birth;         

        public UserServiceRequest.CreateUser toServiceRequest() {
            return UserServiceRequest.CreateUser.builder()
                    .email(email)
                    .password(password)
                    .province(province)
                    .nickName(nickName)        
                    .birth(birth)
                    .build();
        }

        // 테스트 생성자
        public CreateUser(String email, String password, String province, String nickName, String birth) {
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
        @NotBlank(message = "이메일은 필수입니다.")
        private String email;
        @NotBlank(message = "비밀번호는 필수입니다.")
        private String password;

        // 테스트 생성자
        public Login(String email, String password) {
            this.email = email;
            this.password = password;
        }

        public UserServiceRequest.Login toServiceRequest() {
            return UserServiceRequest.Login.builder()
                    .email(email)
                    .password(password)
                    .build();
        }
    }
}

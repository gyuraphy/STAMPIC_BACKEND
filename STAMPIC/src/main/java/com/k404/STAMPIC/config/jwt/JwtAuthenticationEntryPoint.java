package com.k404.STAMPIC.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.k404.STAMPIC.enumeration.Result;
import com.k404.STAMPIC.exception.CustomException;
import com.k404.STAMPIC.exception.CustomExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        //Content-type : application/json;charset=utf-8
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(objectMapper.writeValueAsString(
                CustomExceptionResponse.entryPointResponse.response(
                        new CustomException(Result.NOT_USES_TOKEN)
                )
        ));
    }
}

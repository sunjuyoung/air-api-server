package com.example.airbnbApi.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
//토큰에 대한 유효성 검
    private final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException ex) throws IOException {
        //ObjectMapper objectMapper = new ObjectMapper();
        JSONObject responseJson = new JSONObject();
        LOGGER.info("[commence] 인증 실패로 response.sendError 발생");

//        EntryPointErrorResponse entryPointErrorResponse = new EntryPointErrorResponse();
//        entryPointErrorResponse.setMsg("인증이 실패하였습니다.");


        try {

            if(request.getAttribute("exception") == HttpStatus.UNAUTHORIZED){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                responseJson.put("code", 401);
            }else{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                responseJson.put("code", 404);
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");

            responseJson.put("message", request.getAttribute("msg"));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        //response.getWriter().write(objectMapper.writeValueAsString(entryPointErrorResponse));
        response.getWriter().print(responseJson);
    }
}
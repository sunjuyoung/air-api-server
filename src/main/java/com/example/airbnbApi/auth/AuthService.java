package com.example.airbnbApi.auth;

import com.example.airbnbApi.config.JwtService;
import com.example.airbnbApi.event.RegistrationCompleteEvent;
import com.example.airbnbApi.event.RegistrationCompleteEventListener;
import com.example.airbnbApi.mail.EmailMessage;
import com.example.airbnbApi.mail.EmailService;
import com.example.airbnbApi.user.Account;
import com.example.airbnbApi.user.Role;
import com.example.airbnbApi.user.User;
import com.example.airbnbApi.user.UserRepository;

import com.example.airbnbApi.user.mapper.UserMapper;
import com.example.airbnbApi.user.vo.UserResponseVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService   {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final TemplateEngine templateEngine;


    public Account register(RegisterRequest request,boolean social) {
        var user = new Account(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                social);
        user.generateToken();
        Account account = userRepository.save(user);
        return account;
    }

    public void sendCheckEmail(Account account){
        applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(account));
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Account account = userRepository.findByEmail(request.getEmail())
                .orElseThrow();


        var jwtToken = jwtService.generateToken(new User(account));
        Map<String, Object> currentUser = new HashMap<>();
        currentUser.put("id",account.getId().toString());
        currentUser.put("name",account.getName());
        currentUser.put("email",account.getEmail());
        currentUser.put("emailVerify",account.isEmailVerified());
        currentUser.put("favorites",account.getFavorites());


        log.info(jwtToken);
        return AuthResponse.builder()
                .token(jwtToken)
                .user(currentUser)
                .build();
    }



    public String checkEmailConfirm(String token, String email) {
        Account account = userRepository.findByEmail(email).orElseThrow();
        account.checkEmailToken(token);
         return sendSignUpConfirmEmail(account);
    }
    public String sendSignUpConfirmEmail(Account account) {
        Context context = new Context();
        String link = "http://localhost:3001/";
        context.setVariable("link",link);
        context.setVariable("nickname",account.getName());
        context.setVariable("linkName","홈페이지 이동");
        if(account.isEmailVerified()){
            context.setVariable("message","인증완료");
        }else {
            context.setVariable("message", "인증실패");
        }
        String message = templateEngine.process("mail/check-email-after",context);
        return message;

    }

    public void resendCheckEmailConfirm(Integer account_id) {
        Account account = userRepository.findOnlyId(account_id);
        if(account.isEmailVerified()){
            throw new IllegalArgumentException("인증 완료된 회원입니다.");
        }else{
            account.generateToken();
            sendCheckEmail(account);
        }

    }

//    private void saveUserToken(User user, String jwtToken) {
//        var token = Token.builder()
//                .user(user)
//                .token(jwtToken)
//                .tokenType(TokenType.BEARER)
//                .expired(false)
//                .revoked(false)
//                .build();
//        tokenRepository.save(token);
//    }
//
//    private void revokeAllUserTokens(User user) {
//        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
//        if (validUserTokens.isEmpty())
//            return;
//        validUserTokens.forEach(token -> {
//            token.setExpired(true);
//            token.setRevoked(true);
//        });
//        tokenRepository.saveAll(validUserTokens);
//    }
//
//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response) throws IOException {
//
//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String refreshToken;
//        final String userEmail;
//        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
//            return;
//        }
//        refreshToken = authHeader.substring(7);
//        userEmail = jwtService.extractUsername(refreshToken);
//        if (userEmail != null) {
//            var user = this.userRepository.findByEmail(userEmail)
//                    .orElseThrow();
//            if (jwtService.isTokenValid(refreshToken, user)) {
//                var accessToken = jwtService.generateToken(user);
//                revokeAllUserTokens(user);
//                saveUserToken(user, accessToken);
//                var authResponse = AuthenticationResponse.builder()
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .build();
//                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
//            }
//        }
//    }


}

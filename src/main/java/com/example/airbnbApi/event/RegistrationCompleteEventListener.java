package com.example.airbnbApi.event;


import com.example.airbnbApi.mail.EmailMessage;
import com.example.airbnbApi.mail.EmailService;
import com.example.airbnbApi.user.Account;
import com.example.airbnbApi.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Log4j2
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class RegistrationCompleteEventListener {

    private final UserRepository userRepository;

    private final TemplateEngine templateEngine;

    private final EmailService emailService;


    @EventListener
    public void handleRegistrationUserEmailEvent(RegistrationCompleteEvent event){
        Account account = userRepository.findByEmail(event.getAccount().getEmail()).orElseThrow();

        Context context = new Context();
        String link = "http://localhost:8081/api/v1/auth/check-email/"+account.getEmailCheckToken()+"/"+account.getEmail();
        context.setVariable("link",link);
        context.setVariable("nickname",account.getName());
        context.setVariable("linkName","이메일 인증하기");
        context.setVariable("message","이메일 인증을 완료하려면 링크를 클릭하세요");

        String message = templateEngine.process("mail/email-confirm",context);
        EmailMessage emailMessage = EmailMessage.builder()
                .to(account.getEmail())
                .subject("회원 가입 인증")
                .message(message)
                .build();
        emailService.sendEmail(emailMessage);
    }
}

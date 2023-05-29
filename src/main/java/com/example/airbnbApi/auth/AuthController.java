package com.example.airbnbApi.auth;



import com.example.airbnbApi.user.Account;
import com.example.airbnbApi.valid.RegisterValidation;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;

import java.io.IOException;

@Log4j2
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final RegisterValidation registerValidation;

    @InitBinder("registerRequest")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(registerValidation);
    }



    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest, Errors errors){
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        Account newAccount = service.register(registerRequest, false);
        service.sendCheckEmail(newAccount);

        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }


    @GetMapping("/check-email/{token}/{email}")
    public ResponseEntity<?> checkEmailConfirm(@PathVariable("token") String token,
                                                          @PathVariable("email") String email){
        String confirm = service.checkEmailConfirm(token, email);
        return ResponseEntity.ok().body(confirm);
    }


//    @PostMapping("/refresh-token")
//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response) throws IOException {
//        service.refreshToken(request, response);
//    }
}

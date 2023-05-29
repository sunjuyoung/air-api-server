package com.example.airbnbApi.valid;

import com.example.airbnbApi.auth.RegisterRequest;
import com.example.airbnbApi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Log4j2
@Component
@RequiredArgsConstructor
public class RegisterValidation implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        RegisterRequest request = (RegisterRequest) object;
        if(userRepository.existsByName(request.getName())){
            errors.rejectValue("name","invalid.name",new Object[]{request.getName()},"이미 사용중인 닉네임 또는 이메일 입니다.");
        }
        if(userRepository.existsByEmail(request.getEmail())){
            errors.rejectValue("email","invalid.email",new Object[]{request.getEmail()},"이미 사용중인 이메일 입니다.");
        }
    }
}

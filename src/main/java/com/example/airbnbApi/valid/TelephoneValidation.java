package com.example.airbnbApi.valid;


import com.example.airbnbApi.common.Telephone;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelephoneValidation implements ConstraintValidator<Telephone,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return false;
        }
        return value.matches("^010-?([0-9]{3,4})-?([0-9]{4})$");

    }
}

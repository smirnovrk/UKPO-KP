package com.yellow.photoshare.validation;

import com.yellow.photoshare.entity.UserEntity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserEntity user = (UserEntity) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }

}

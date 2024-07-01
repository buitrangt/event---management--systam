package org.aibles.eventmanagementsystem.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = ValidatePassword.PasswordValidation.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
public @interface ValidatePassword {

    String message() default "Invalid password format!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class PasswordValidation implements ConstraintValidator<ValidatePassword, String> {

        @Override
        public void initialize(ValidatePassword constraintAnnotation) {}

        @Override
        public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
            String regexPassword = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$";
            return password != null && password.matches(regexPassword);
        }
    }
}

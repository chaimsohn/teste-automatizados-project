package br.com.daniloc.testeautomatizadosproject.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<Date, LocalDate> {


    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value == null || LocalDate.now().isBefore(value);
    }
}

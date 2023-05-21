package br.com.daniloc.testeautomatizadosproject.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MinPriceValidator implements ConstraintValidator<MinPrice, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value == null || value >= 20.0;
    }
}
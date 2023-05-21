package br.com.daniloc.testeautomatizadosproject.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = { MinPriceValidator.class })
@Target(FIELD)
@Retention(RUNTIME)
public @interface MinPrice {

    String message() default "O valor deve ser maior que R$20,00";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
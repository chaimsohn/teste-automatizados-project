package br.com.daniloc.testeautomatizadosproject.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = { DateValidator.class })
@Target(FIELD)
@Retention(RUNTIME)
public @interface Date {

    String message() default "A data deve ser posterior a atual";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}

package br.com.daniloc.testeautomatizadosproject.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends StandardError{

    @Serial
    private static final long serialVersionUID =1L;

    private final List<FieldError> errors = new ArrayList<>();
    public ValidationError(LocalDateTime timestamps, String path, Integer status, String error, String message) {
        super(timestamps, path, status, error, message);
    }

    public void addError(String fieldName, String message){
        this.errors.add(new FieldError(fieldName, message));
    }

    @Getter
    @AllArgsConstructor
    private static final class FieldError{
        private String fieldName;
        private String message;
    }
}

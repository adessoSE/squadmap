package de.adesso.squadmap.adapter.web.webentities.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
@AllArgsConstructor
public class Violation implements SubError{

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    private static Violation of(FieldError fieldError){
        return new Violation(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    private static Violation of(ConstraintViolation<?> constraintViolation) {
        return new Violation(
                constraintViolation.getLeafBean().getClass().getName(),
                StreamSupport.stream(constraintViolation.getPropertyPath().spliterator(), false)
                        .reduce((f,s) -> s).orElseThrow().toString(),
                constraintViolation.getInvalidValue(),
                constraintViolation.getMessage());
    }

    public static List<SubError> getViolations(Set<ConstraintViolation<?>> constraintViolations) {
        return constraintViolations.stream()
                .map(Violation::of)
                .collect(Collectors.toList());
    }

    public static List<SubError> getViolations(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(Violation::of)
                .collect(Collectors.toList());
    }
}



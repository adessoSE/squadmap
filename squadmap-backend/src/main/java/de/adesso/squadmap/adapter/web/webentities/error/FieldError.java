package de.adesso.squadmap.adapter.web.webentities.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FieldError implements SubError{

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    private static FieldError of(ConstraintViolation<?> constraintViolation) {
        return new FieldError(
                constraintViolation.getLeafBean().getClass().getName(),
                StreamSupport.stream(constraintViolation.getPropertyPath().spliterator(), false)
                        .reduce((f,s) -> s).orElseThrow().toString(),
                constraintViolation.getInvalidValue(),
                constraintViolation.getMessage());
    }

    public static List<SubError> getErrors(Set<ConstraintViolation<?>> constraintViolations) {
        return constraintViolations.stream()
                .map(FieldError::of)
                .collect(Collectors.toList());
    }
}



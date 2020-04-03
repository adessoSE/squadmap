package de.adesso.squadmap.adapter.web;

import de.adesso.squadmap.adapter.web.webentities.error.ApiError;
import de.adesso.squadmap.adapter.web.webentities.error.FieldError;
import de.adesso.squadmap.application.domain.exceptions.AlreadyExistsException;
import de.adesso.squadmap.application.domain.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerAdvise extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    ApiError handleConstraintViolationException(ConstraintViolationException ex) {
        ex.getConstraintViolations().forEach(v-> System.out.println(v.getRootBeanClass()));
        ApiError error = new ApiError(ex.getMessage(), ex, HttpStatus.BAD_REQUEST);
        error.setSubErrors(FieldError.getErrors(ex.getConstraintViolations()));
        return error;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    ApiError handleNotFoundException(NotFoundException ex) {
        return new ApiError(ex.getMessage(), ex, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyExistsException.class)
    ApiError handleAlreadyExistsException(AlreadyExistsException ex) {
        return new ApiError(ex.getMessage(), ex, HttpStatus.CONFLICT);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ApiError handleAll(Exception ex) {
        return new ApiError(ex.getMessage(), ex,  HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

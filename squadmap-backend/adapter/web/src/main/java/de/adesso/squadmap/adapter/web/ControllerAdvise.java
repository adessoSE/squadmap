package de.adesso.squadmap.adapter.web;

import de.adesso.squadmap.adapter.web.webentities.error.ApiError;
import de.adesso.squadmap.adapter.web.webentities.error.Violation;
import de.adesso.squadmap.domain.exceptions.AlreadyExistsException;
import de.adesso.squadmap.domain.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class ControllerAdvise {

    Logger logger = Logger.getAnonymousLogger();

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logger.log(Level.SEVERE, "handled MethodArgumentNotValidException", ex);
        ApiError error = new ApiError(ex.getMessage(), ex, HttpStatus.BAD_REQUEST.value());
        error.setErrors(Violation.getViolations(ex.getBindingResult().getFieldErrors()));
        return error;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    ApiError handleConstraintViolationException(ConstraintViolationException ex) {
        logger.log(Level.SEVERE, "handled ConstraintViolationException", ex);
        ApiError error = new ApiError(ex.getMessage(), ex, HttpStatus.BAD_REQUEST.value());
        error.setErrors(Violation.getViolations(ex.getConstraintViolations()));
        return error;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    ApiError handleNotFoundException(NotFoundException ex) {
        logger.log(Level.SEVERE, "handled NotFoundException", ex);
        return new ApiError(ex.getMessage(), ex, HttpStatus.NOT_FOUND.value());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyExistsException.class)
    ApiError handleAlreadyExistsException(AlreadyExistsException ex) {
        logger.log(Level.SEVERE, "handled AlreadyExistsException", ex);
        return new ApiError(ex.getMessage(), ex, HttpStatus.CONFLICT.value());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ApiError handleAll(Exception ex) {
        logger.log(Level.SEVERE, "unknown Exception", ex);
        return new ApiError(ex, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}

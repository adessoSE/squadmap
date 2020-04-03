package de.adesso.squadmap.adapter.web.webentities.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ApiError {

    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
    private String debugMessage;
    private List<SubError> subErrors = new ArrayList<>();


    private ApiError() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(Throwable ex, HttpStatus status) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(String message, Throwable ex, HttpStatus status) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    public void addSubError(SubError subError) {
        this.subErrors.add(subError);
    }
}

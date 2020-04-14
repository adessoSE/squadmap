package de.adesso.squadmap.adapter.web.webentities.error;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError {

    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String debugMessage;
    private List<SubError> errors;


    private ApiError() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(Throwable ex, int status) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(String message, Throwable ex, int status) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.toString();
    }
}

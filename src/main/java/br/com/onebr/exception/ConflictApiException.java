package br.com.onebr.exception;

import org.springframework.http.HttpStatus;

public class ConflictApiException extends ApiException {

    public ConflictApiException(String message, String... args) {
        super(message, HttpStatus.CONFLICT, args);
    }
}

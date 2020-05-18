package br.com.onebr.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenApiException extends ApiException {

    public ForbiddenApiException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}

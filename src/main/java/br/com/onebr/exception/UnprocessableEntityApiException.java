package br.com.onebr.exception;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityApiException extends ApiException {

    public UnprocessableEntityApiException(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}

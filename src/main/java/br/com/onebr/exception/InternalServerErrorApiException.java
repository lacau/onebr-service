package br.com.onebr.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorApiException extends ApiException {

    public InternalServerErrorApiException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

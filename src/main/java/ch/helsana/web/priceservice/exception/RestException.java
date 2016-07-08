package ch.helsana.web.priceservice.exception;

import org.springframework.http.HttpStatus;

/**
 * Includes HTTP response status
 * Created by EX3C2 on 25.01.2016.
 */

public class RestException extends RuntimeException {

    private final HttpStatus httpStatus;

    public RestException(String message, HttpStatus status){
         super(message);
         this.httpStatus = status;

    };

    public RestException(String message ){
        super(message);
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    };

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
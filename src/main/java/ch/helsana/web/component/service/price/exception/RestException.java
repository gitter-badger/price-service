package ch.helsana.web.component.service.price.exception;

import org.springframework.http.HttpStatus;

/**
 * Includes HTTP response status
 * Created by EX3C2 on 25.01.2016.
 */
public class RestException extends FSLException {

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
package ch.helsana.web.priceservice.exception;

import org.springframework.http.HttpStatus;

/**
 * Service internal exception.
 * Created by marcelwidmer
 */
public class BusinessException extends RestException {

     public BusinessException(String message){
         super(message);
     }

    public BusinessException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
